package board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pieces.Checker;

public abstract class Tile {
	
	// tile on board, numbered 0-63
	protected final int tileNumber;
	
	private static final Map<Integer, EmptyTile> EMPTY_BOARD = createAllEmptyTiles();
	
	private Tile(int tileNumber) {
		this.tileNumber = tileNumber;
	}
	
	public int getTileNumber() {
		return this.tileNumber;
	}
	
	private static Map<Integer, EmptyTile> createAllEmptyTiles() {
		final Map<Integer, EmptyTile> emptyBoard = new HashMap<>();
		
		for (int i = 0; i < Utility.TILES; ++i) {
			emptyBoard.put(i, new EmptyTile(i));
		}
		return Collections.unmodifiableMap(emptyBoard);
	}
	
	// create a new tile outside of class
	public static Tile createTile(int tileNumber, Checker checker) {
		return checker != null ? new OccupiedTile(tileNumber, checker) : EMPTY_BOARD.get(tileNumber);
	}

	public abstract boolean isOccupied();
	public abstract Checker getChecker();
	
	// empty tile class ------------------------------
	public static final class EmptyTile extends Tile {
		
		private EmptyTile(int number) {
			super(number);
		}

		@Override
		public boolean isOccupied() {
			return false;
		}

		@Override
		public Checker getChecker() {
			return null;
		}
		
		// for testing ---------------------------\-\-\
		@Override
		public String toString() {
			return "-";
		}
		// ---------------------------------------/-/-/
		
	} // ---------------------------------------------
	
	// occupied tile class ----------------------------------
	public static final class OccupiedTile extends Tile {
		
		// type of checker on tile, {BN, BK, WN, BK}
		private final Checker checkerOnTile;

		private OccupiedTile(int tileNumber, Checker checkerOnTile) {
			super(tileNumber);
			this.checkerOnTile = checkerOnTile;
		}

		@Override
		public boolean isOccupied() {
			return true;
		}

		@Override
		public Checker getChecker() {
			return this.checkerOnTile;
		}
		
		// for testing ---------------------------\-\-\
		@Override
		public String toString() {
			return getChecker().getPieceColor().isRed() ? getChecker().toString().toLowerCase() : getChecker().toString();
		}
		// ---------------------------------------/-/-/
		
	} // ----------------------------------------------------
}























