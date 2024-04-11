package player;

import java.util.Collection;

import board.Board;
import board.Move;
import pieces.Checker;
import pieces.PieceColor;

public class BlackPlayer extends Player {

	public BlackPlayer(Board board, final Collection<Move> possibleRedMoves, final Collection<Move> possibleBlackMoves) {
		super(board, possibleBlackMoves, possibleRedMoves);
	} 

	@Override
	public Collection<Checker> getActivePieces() {
		return this.board.getBlackPieces();
	}

	@Override
	public PieceColor getPieceColor() {
		return PieceColor.BLACK;
	}

	@Override
	public Player getOpponent() {
		return this.board.redPlayer();
	}  
}
