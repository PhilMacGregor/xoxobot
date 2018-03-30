package ai;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AiPlayer {

	private MapResolver mapResolver;
	
	private double[][] dangers;
	private double[][] opportunities;
	
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

	public void turn() {
		if (firstTurn) {
			init();
		}
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
		
		dangers = new double[maxX + 1][maxY + 1];
		opportunities = new double[maxX + 1][maxY + 1];
		
		for (Point point : allPoints) {
			countDanger(point);
			countOpportunity(point);
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
	
	private void countOpportunity(Point point) {
		double opportunity = 0;
		
		for (Point v : vectors) {
			Map<Integer, Player> players = lookupDirection(point, v);
		}
		
		opportunities[point.x][point.y] = opportunity;
	}

	private void countDanger(Point point) {
		double danger = 0;
		
		for (Point v : vectors) {
			Map<Integer, Player> players = lookupDirection(point, v);
		}
		
		dangers[point.x][point.y] = danger;
	}
	
	private Map<Integer, Player> lookupDirection(Point origin, Point vector) {
		
		Map<Integer, Player> points = new TreeMap<>();
		
		points.put(0, mapResolver.getFieldAt(origin.x, origin.y));
		int direction = 1;
		int index = 1;
		
		while (direction != 0) {
			Point cursor = addPoint(origin, multiplyPoint(vector, direction * index));
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
	
	private Point multiplyPoint(Point pt, int multiplier) {
		return new Point(pt.x * multiplier, pt.y * multiplier);
	}
	
	private Point addPoint(Point pt, Point add) {
		return new Point(pt.x + add.x, pt.y + add.y);
	}
	
}
