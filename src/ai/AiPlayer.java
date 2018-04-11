package ai;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AiPlayer {

	private MapResolver mapResolver;
	
	private Double[][] dangers;
	private Double[][] opportunities;
	
	private Collection<Point> occupiedPoints;
	private Collection<Point> freePoints;
	
	private Point[] vectors;
	
	private boolean firstTurn;
	
	public AiPlayer() {
		this.firstTurn = true;
		this.occupiedPoints = new HashSet<>();
		this.freePoints = new HashSet<>();
		
		Collection<Point> vectorsToSet = new HashSet<>();
		vectorsToSet.add(new Point(1, 0));
		vectorsToSet.add(new Point(1, 1));
		vectorsToSet.add(new Point(0, 1));
		vectorsToSet.add(new Point(-1, 1));
		
		this.vectors = vectorsToSet.toArray(new Point[vectorsToSet.size()]);
	}
	
	public void setMapResolver(MapResolver mapResolver) {
		this.mapResolver = mapResolver;
	}

	public Point turn() {
		if (firstTurn) {
			init();
		}

		for (Point point : freePoints) {
			if (mapResolver.getFieldAt(point.x, point.y) != Player.NONE) {
				occupyPoint(point.x, point.y);
				continue;
			}
			
			dangers[point.x][point.y] = countDanger(point);
			opportunities[point.x][point.y] = countOpportunity(point);
		}

		Util.debugMap(dangers);
		
		Map<Double, Point> dangerMap = new TreeMap<>();
		for (Point pt : freePoints) {
			dangerMap.put(dangers[pt.x][pt.y], pt);
		}
		
		Double maxDanger = dangerMap.keySet().toArray(new Double[dangerMap.keySet().size()])[dangerMap.keySet().size() - 1];
		return (dangerMap.get(maxDanger));
		
	}

	public void opponentPlayed(int x, int y) {
		if (firstTurn) {
			init();
		}
		occupyPoint(x, y);
	}

	private void init() {
		Collection<Point> allPoints = getAllMapPoints();
		freePoints.addAll(allPoints);
		
		int maxX = 0;
		int maxY = 0;
		
		for (Point pt : allPoints) {
			if (pt.x > maxX) {
				maxX = pt.x;
			}
			if (pt.y > maxY) {
				maxY = pt.y;
			}
		}
		
		dangers = new Double[maxX + 1][maxY + 1];
		opportunities = new Double[maxX + 1][maxY + 1];
		
		for (Point pt : allPoints) {
			dangers[pt.x][pt.y] = 0.0;
			opportunities[pt.x][pt.y] = 0.0;
		}
		
		firstTurn = false;
	}
	
	private void occupyPoint(int x, int y) {
		freePoints.remove(new Point(x, y));
		occupiedPoints.add(new Point(x, y));
	}

	private Collection<Point> getAllMapPoints() {
		Collection<Point> points = new HashSet<>();
		
		int x = 0;
		int y = 0;
		Dimension mapSize = mapResolver.getMapSize();
		while(x < mapSize.width && y < mapSize.height) {
			points.add(new Point(x, y));
			
			x++;
			if (x == mapSize.width) {
				x = 0;
				y++;
			}
		}
		
		return points;
		
	}
	
	private double countOpportunity(Point point) {
		double opportunity = 0;
		
		for (Point v : vectors) {
			Map<Integer, Player> players = lookupDirection(point, v);
		}
		
		return opportunity;
	}

	private double countDanger(Point point) {
		double danger = 0;
		
		for (Point v : vectors) {
			Map<Integer, Player> players = lookupDirection(point, v);
			
			List<String> pts = new ArrayList<>();
			List<String> plys = new ArrayList<>();
			
			for (Player p : players.values()) {
				if (p == Player.ENEMY) {
					danger++;
				}
			}
		}
		
		return danger;
	}
	
	private Map<Integer, Player> lookupDirection(Point origin, Point vector) {
		
		Map<Integer, Player> points = new TreeMap<>();
		
		points.put(0, mapResolver.getFieldAt(origin.x, origin.y));
		int direction = 1;
		int index = 1;
		
		while (direction != 0) {
			Point cursor = origin.addPoint(vector.multiplyPoint(direction * index));
			Player p = mapResolver.getFieldAt(cursor.x, cursor.y);
			
			if (p == Player.WALL) {
				index = 1;
				direction = direction == 1 ? -1 : 0;
			} else {
				points.put(direction * index, p);
				index++;
			}
			
		}
		
		return points;
	}
	
}
