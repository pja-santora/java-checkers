package player;

import java.util.Collection;

import board.Board;
import board.Move;
import pieces.Checker;
import pieces.PieceColor;

public class RedPlayer extends Player {

	public RedPlayer(final Board board, final Collection<Move> possibleRedMoves, final Collection<Move> possibleBlackMoves) {
		super(board, possibleRedMoves, possibleBlackMoves);
	}

	@Override
	public Collection<Checker> getActivePieces() {
		return this.board.getRedPieces();
	}
	
	@Override
	public PieceColor getPieceColor() {
		return PieceColor.RED;
	}

	@Override
	public Player getOpponent() {
		return this.board.blackPlayer();
	}
}
