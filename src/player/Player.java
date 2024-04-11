package player;

import java.util.Collection;

import board.Board;
import board.Move;
import pieces.Checker;
import pieces.PieceColor;

public abstract class Player {

	protected Board board;
	protected Collection<Move> possibleMoves;
	
	Player(Board board, final Collection<Move> possibleMoves, final Collection<Move> opponentsMoves) {
		this.board = board;
		this.possibleMoves = possibleMoves;
	}
	
	public Collection<Move> getPossibleMoves() {
		return this.possibleMoves;
	}
	
	public boolean isPossibleMove(final Move move) {
		return this.possibleMoves.contains(move);
	}
	
	public MoveShift makeMove(final Move move) {
		
		if (!isPossibleMove(move))
			return new MoveShift(this.board, move, Status.ILLEGAL);
		
		// if legal move
		Board shiftedBoard = move.make();
		return new MoveShift(shiftedBoard, move, Status.DONE);
	}
	
	public abstract Collection<Checker> getActivePieces();
	public abstract PieceColor getPieceColor();
	public abstract Player getOpponent();
}

































