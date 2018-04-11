package ai;

import java.util.LinkedList;
import java.util.List;

public final class Util {

	public static void debugMap(Object[][] map) {
		List<StringBuilder> rowsToPrint = new LinkedList<>();
		
		int x = 0;
		int y = 0;
		
		while (x < map.length) {
			Object[] col = map[x];
			
			if (rowsToPrint.size() <= y) {
				rowsToPrint.add(y, new StringBuilder());
			}
			rowsToPrint.get(y).append(map[x][y].toString() + " | ");
			
			y++;
			if (y >= col.length) {
				y = 0;
				x++;
			}
			
		}
		
		for (int i = 0; i < rowsToPrint.get(0).length(); i++) {
			System.out.print("=");
		}
		System.out.print("\n");
		
		for (StringBuilder sb : rowsToPrint) {
			System.out.println(sb.toString());
		}
		
	}
	
}
