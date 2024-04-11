package board;

import board.Board.Build;
import pieces.Checker;
import pieces.Normal;

public abstract class Move {
	
	// track objects
	protected Board board;
	protected Checker moved_checker;
	protected int checker_destination;
	
	public static final Move NULL_MOVE = new NullMove();
	
	private Move(Board board, Checker moved_checker, int checker_destination) {
		this.board = board;
		this.moved_checker = moved_checker;
		this.checker_destination = checker_destination;
	}
	
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
					
		if (!(other instanceof Move))
			return false;
		
		Move otherMove = (Move) other;
		return getDestination() == otherMove.getDestination() && getMovedChecker().equals(otherMove.getMovedChecker());
	}
	
	@Override
	public int hashCode() {
		int result = 1;
		result = 31 * result + this.checker_destination;
		result = 31 * result + this.moved_checker.hashCode();
		return result;
	}
	
	public int getPosition() {
		return this.moved_checker.getPosition();
	}
	
	public int getDestination() {
		return this.checker_destination;
	}
	
	public Checker getMovedChecker() {
		return this.moved_checker;
	}
	
	public boolean isAttack() {
		return false;
	}
	
	public Checker getAttackedChecker() {
		return null;
	}
	
	public Board getBoard() {
		return this.board;
	}
	
	public Board make() {
		
		Build builder = new Build();
		for (Checker checker : this.board.currentPlayer().getActivePieces()) {
			
			if (!this.moved_checker.equals(checker))
				builder.setChecker(checker);
		}
		
		for (Checker checker : this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setChecker(checker);
		}
		
		// move piece that's being moved
		builder.setChecker(this.moved_checker.moveChecker(this));
		builder.nextMove(this.board.currentPlayer().getOpponent().getPieceColor());
		
		return builder.build();
	}

	// -------------------------------------------------------------------------------------\>\>\
	// SUB-CLASSES --------------------------------------------------------------------------\<\<\
	public static final class RegularMove extends Move {
		
		public RegularMove(Board board, Checker moved_checker, int checker_destination) {
			super(board, moved_checker, checker_destination);
		}
	}
	
	public static class JumpMove extends Move {
		
		final Checker attacked_piece;

		public JumpMove(Board board, Checker moved_checker, int checker_destination, Checker attacked_piece) {
			super(board, moved_checker, checker_destination);
			this.attacked_piece = attacked_piece;
		}
		
		@Override
		public boolean equals(Object other) {
			if (this == other)
				return true;
						
			if (!(other instanceof JumpMove))
				return false;
			
			JumpMove otherMove = (JumpMove) other;
			return super.equals(otherMove) && getAttackedChecker().equals(otherMove.getAttackedChecker());
		}
		
		@Override
		public int hashCode() {
			return this.attacked_piece.hashCode() + super.hashCode();
		}
		
		@Override
		public Board make() {
			
			Build builder = new Build();
			for (Checker checker : this.board.currentPlayer().getActivePieces()) {
				
				if (!this.moved_checker.equals(checker))
					builder.setChecker(checker);
			}
			
			// run through opponents pieces
			for (Checker checker : this.board.currentPlayer().getOpponent().getActivePieces()) {
				
				// only set pieces that aren't being attacked
				if (!checker.equals(this.getAttackedChecker()))
					builder.setChecker(checker);
			}
			
			// move piece that's being moved
			builder.setChecker(this.moved_checker.moveChecker(this));
			builder.nextMove(this.board.currentPlayer().getOpponent().getPieceColor());
			
			return builder.build();
		}
		
		@Override
		public boolean isAttack() {
			return true;
		}
		
		@Override
		public Checker getAttackedChecker() {
			return this.attacked_piece;
		}
	}
	
	public static class Promotion extends Move {
		
		Move decorated_move;
		Normal promoted_checker;
		
		public Promotion(Move decoratedMove) {
			super(decoratedMove.getBoard(), decoratedMove.getMovedChecker(), decoratedMove.getDestination());
			this.decorated_move = decoratedMove;
			this.promoted_checker = (Normal) decoratedMove.getMovedChecker();
		}
		
		@Override
		public Board make() {
			
			Board promotionBoard = this.decorated_move.make();
			Build builder = new Build();
			for (Checker checker : promotionBoard.currentPlayer().getActivePieces()) {
				
				if (!this.promoted_checker.equals(checker))
					builder.setChecker(checker);
			}
			
			// run through opponents pieces
			for (Checker checker : promotionBoard.currentPlayer().getOpponent().getActivePieces()) {
					builder.setChecker(checker);
			}
			
			// move piece that's being moved
			builder.setChecker(this.promoted_checker.getPromotedChecker().moveChecker(this));
			builder.nextMove(promotionBoard.currentPlayer().getPieceColor());
			
			return builder.build();
		}
		
		@Override
		public boolean isAttack() {
			return this.decorated_move.isAttack();
		}
		
		@Override
		public Checker getAttackedChecker() {
			return this.decorated_move.getAttackedChecker();
		}
	}
	
	public static final class NullMove extends Move {
		
		public NullMove() {
			super(null, null, -1);
		}
		
		@Override
		public Board make() {
			throw new RuntimeException("NOPE!");
		}
	} // ----------------------------------------------
	
	public static class FactoryMove {
		
		private FactoryMove() {}
		
		public static Move createMove(Board board, int position, int destination) {
			
			for (Move move : board.getAllPossibleMoves()) {
				if (move.getPosition() == position && move.getDestination() == destination)
					return move;
			}
			return NULL_MOVE;
		}
	}
	
}



























