package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board.Board;
import board.Move;
import board.Utility;

public class King extends Checker {
	
	// theoretically possible moves, offset from current position
	private static final int[] CANDADATE_MOVES = { -18, -14, -7, -9, 7, 9, 14, 18 };

	public King(PieceColor type, int position) {
		super(type, PieceRank.KING, position);
	}

	@Override
	public Collection<Move> PossibleMoves(Board board) {
		
		// list of moves the player can make
		final List<Move> possibleMoves = new ArrayList<>();
		
		for (int move : CANDADATE_MOVES) {
			
			// index of tile destination
			final int destination = this.c_position + move;
			
			// if tile is a valid tile on board
			if (Utility.validTilePosition(destination)) {
				
				// if tile is unoccupied
				if (!board.getTile(destination).isOccupied()) {
					
					 /* ------------------------------------------------------------------ */
					/* non-attacking, one-space moves ----------------------------------- */
					if ((move == -7 || move == 9) && !Utility.COLUMN_8[this.c_position]) {
						/* doesn't work on right-most column */
						
						possibleMoves.add(new Move.RegularMove(board, this, destination));
						
					} else if ((move == -9 || move == 7) && !Utility.COLUMN_1[this.c_position]) {
						/* doesn't work on left-most column */
						
						possibleMoves.add(new Move.RegularMove(board, this, destination));
						
					   /* ---------------------------------------------------------------- */
					} /* attacking, jump moves ------------------------------------------ */
					else if ((move == -14 || move == 18) && 
							!(Utility.COLUMN_7[this.c_position] || Utility.COLUMN_8[this.c_position])) {
						
						// if space to-be jumped over is occupied
						final int betweenCheckerAndDestination = this.c_position + (move/2);
						if (board.getTile(betweenCheckerAndDestination).isOccupied()) {
							
							// if checker in that space is opposite color
							final Checker checkerBetween = board.getTile(betweenCheckerAndDestination).getChecker();
							if (this.getPieceColor() != checkerBetween.getPieceColor()) {
								
								possibleMoves.add(new Move.JumpMove(board, this, destination, checkerBetween));
							}
						}
					} else if ((move == -18 || move == 14) && 
							!(Utility.COLUMN_1[this.c_position] || Utility.COLUMN_2[this.c_position])) {
						
						// if space to-be jumped over is occupied
						final int betweenCheckerAndDestination = this.c_position + (move/2);
						if (board.getTile(betweenCheckerAndDestination).isOccupied()) {
							
							// if checker in that space is opposite color
							final Checker checkerBetween = board.getTile(betweenCheckerAndDestination).getChecker();
							if (this.getPieceColor() != checkerBetween.getPieceColor()) {
								
								possibleMoves.add(new Move.JumpMove(board, this, destination, checkerBetween));
							}
						}
					}
				}
			}
		} /* end for loop */
		
		return Collections.unmodifiableList(possibleMoves);
	}
	
	@Override
	public String toString() {
		return PieceRank.KING.toString();
	}

	@Override
	public King moveChecker(Move move) {
		return new King(move.getMovedChecker().getPieceColor(), move.getDestination());
	}
}





















