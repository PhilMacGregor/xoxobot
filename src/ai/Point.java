package ai;

public class Point {
	public int x, y;
	
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Point multiplyPoint(int multiplier) {
		return new Point(x * multiplier, y * multiplier);
	}
	
	public Point addPoint(Point add) {
		return new Point(x + add.x, y + add.y);
	}
	
	@Override
	public String toString() {
		return String.format("[%d; %d]", x, y);
	}
	
}
