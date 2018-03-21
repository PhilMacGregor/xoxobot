package ai;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class AiPlayer {

	private MapResolver mapResolver;
	
	private double[][] dangers;
	private double[][] opportunities;
	
	private Collection<Point> occupiedPoints;
	
	private Point[] vectors;
	
	private boolean firstTurn;
	
	public AiPlayer() {
		this.firstTurn = true;
		this.occupiedPoints = new HashSet<>();
		
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

	private void init() {
		Collection<Point> allPoints = getAllMapPoints();
		
		for (Point point : allPoints) {
			countDanger(point);
			countOpportunity(point);
		}
		
		firstTurn = false;
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
		// TODO Auto-generated method stub
		
	}

	private void countDanger(Point point) {
		// TODO Auto-generated method stub
		
	}
	
	private List<Player> lookupDirection(Point origin, Point vector) {
		List<Player> points = new ArrayList<>();
		
		return points;
	}
	
}
