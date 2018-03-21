package ai;

import java.awt.Dimension;

public interface MapResolver {

	public Player getFieldAt(int x, int y);
	
	public Dimension getMapSize();
	
}
