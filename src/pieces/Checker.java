package pieces;

import java.util.Collection;

import board.Board;
import board.Move;

public abstract class Checker {
	
	// checker size
	// private static final int C_SIZE = 60;
	
	// type of checker
	protected PieceColor c_type;
	protected PieceRank c_rank;
	
	// location of checker
	protected final int c_position;
	
	// for hash
	private int hash_code;
	
	public Checker(PieceColor type, PieceRank rank, int position) {
		this.c_type = type;
		this.c_rank = rank;
		this.c_position = position;
		this.hash_code = setHashCode(); 
	}
	
	private int setHashCode() {
		int result = c_rank.hashCode();
		result = 31 * result + c_type.hashCode();
		result = 31 * result + c_position;
		return result;
	}

	public PieceColor getPieceColor() {
		return c_type;
	}
	
	public PieceRank getPieceRank() {
		return c_rank;
	}
	
	public int getPosition() {
		return this.c_position;
	}
	
	@Override
	public boolean equals(final Object other) {
		
		if (this == other)
			return true;
		if (!(other instanceof Checker))
			return false;
		
		Checker otherChecker = (Checker) other;
		return c_type == otherChecker.getPieceColor() && c_rank == otherChecker.getPieceRank() && 
				c_position == otherChecker.getPosition();
	}
	
	@Override
	public int hashCode() {
		return this.hash_code;
	}
	
	public enum PieceRank {
		
		NORMAL("N") {
			
			@Override
			public boolean isKing() {
				return false;
			}
		},
		KING("K") {
			
			@Override
			public boolean isKing() {
				return true;
			}
		};
		
		private String pieceName;
		
		PieceRank(String pieceName) {
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString() {
			return this.pieceName;
		}
		
		public abstract boolean isKing();
	}
	
	// returns a list of moves each checker can take
	public abstract Collection<Move> PossibleMoves(final Board board);
	public abstract Checker moveChecker(Move move);
}


























