package board;

public class Utility {
	
	// total number of tile on board
	public static final int TILES = 64;
	
	public static final boolean[] COLUMN_1 = setColumn(0); // left-most column
	public static final boolean[] COLUMN_2 = setColumn(1);
	public static final boolean[] COLUMN_3 = setColumn(2);
	public static final boolean[] COLUMN_4 = setColumn(3);
	public static final boolean[] COLUMN_5 = setColumn(4);
	public static final boolean[] COLUMN_6 = setColumn(5);
	public static final boolean[] COLUMN_7 = setColumn(6);
	public static final boolean[] COLUMN_8 = setColumn(7); // right-most column
	
	public static final boolean[] ROW_1 = setRow(0); // top row
	public static final boolean[] ROW_8 = setRow(56); // bottom row
	
	private Utility() { throw new RuntimeException("NOPE!"); }
	
	// semi-generic column initialization method
	private static boolean[] setColumn(int columnNumber) {
		
		boolean[] column = new boolean[TILES];
		
		do {
			column[columnNumber] = true;
			columnNumber += 8;
		} while (columnNumber < TILES);
		
		return column;
	}
	
	// semi-generic row initialization method
	private static boolean[] setRow(int rowNumber) {
		
		boolean[] row = new boolean[TILES];
		
		do {
			row[rowNumber] = true;
			rowNumber += 1;
		} while (rowNumber % 8 != 0);
		
		return row;
	}
	
	public static boolean validTilePosition(int position) {
		return position >= 0 && position < TILES;
	}
	
	
	
}
