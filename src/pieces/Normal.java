package pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import board.Board;
import board.Move;
import board.Move.Promotion;
import board.Utility;

public class Normal extends Checker {
	
	// theoretically possible moves, offset from current position
	private static final int[] CANDADATE_MOVES = { 7, 9, 14, 18 };

	public Normal(PieceColor type, int position) {
		super(type, PieceRank.NORMAL, position);
	}

	@Override
	public Collection<Move> PossibleMoves(Board board) {
		
		// list of moves the player can make
		final List<Move> possibleMoves = new ArrayList<>();
		
		for (int move : CANDADATE_MOVES) {
			
			// apply direction (/\ or \/) based on player
			final int destination = this.c_position + move * this.getPieceColor().getDirection();
			
			// tile is not a valid tile on board
			if (!Utility.validTilePosition(destination)) {
				continue;
			}
			
			 /* ------------------------------------------------------------------ */
			/* non-attacking, one-space moves ----------------------------------- */
			if (move == 7 && !board.getTile(destination).isOccupied() && !(
			   (this.getPieceColor().isBlack() && Utility.COLUMN_8[this.c_position]) ||
			   (this.getPieceColor().isRed() && Utility.COLUMN_1[this.c_position]) ) ) {
				/* ^^^ tile must be empty and within checker-board bounds ^^^ */
				
				// check if piece can be promoted to king
				if (this.c_type.isPromotionTile(destination)) {
					possibleMoves.add(new Promotion(new Move.RegularMove(board, this, destination)));
				} else {
					possibleMoves.add(new Move.RegularMove(board, this, destination));
				}
			} 
			else if (move == 9 && !board.getTile(destination).isOccupied() && !(
					(this.getPieceColor().isBlack() && Utility.COLUMN_1[this.c_position]) || 
					(this.getPieceColor().isRed() && Utility.COLUMN_8[this.c_position])) ) {
				/* ^^^ tile must be empty and within checker-board bounds ^^^ */
				
				// check if piece can be promoted to king
				if (this.c_type.isPromotionTile(destination)) {
					possibleMoves.add(new Promotion(new Move.RegularMove(board, this, destination)));
				}
				else {
					possibleMoves.add(new Move.RegularMove(board, this, destination));
				}
				
			   /* ---------------------------------------------------------------- */
			} /* attacking, jump moves ------------------------------------------ */
			else if (move == 14 && !board.getTile(destination).isOccupied() && !(
					(this.getPieceColor().isBlack() && (Utility.COLUMN_7[this.c_position] || 
													   Utility.COLUMN_8[this.c_position])) ||
					(this.getPieceColor().isRed() && (Utility.COLUMN_1[this.c_position] || 
													 Utility.COLUMN_2[this.c_position]))) ) {
				/* ^^^ tile must be empty and within checker-board bounds ^^^ */
				
				final int betweenCheckerAndDestination = this.c_position + (this.getPieceColor().getDirection() * 7);
				if (board.getTile(betweenCheckerAndDestination).isOccupied()) {
					
					final Checker checkerBetween = board.getTile(betweenCheckerAndDestination).getChecker();
					if (this.getPieceColor() != checkerBetween.getPieceColor()) {
						
						// check if piece can be promoted to king
						if (this.c_type.isPromotionTile(destination)) {
							possibleMoves.add(new Promotion(new Move.JumpMove(board, this, destination, checkerBetween)));
						} else {
							possibleMoves.add(new Move.JumpMove(board, this, destination, checkerBetween));
						}
					}
				}
			}
			else if (move == 18 && !board.getTile(destination).isOccupied() && !(
					(this.getPieceColor().isBlack() && (Utility.COLUMN_1[this.c_position] || 
													   Utility.COLUMN_2[this.c_position])) ||
					(this.getPieceColor().isRed() && (Utility.COLUMN_7[this.c_position] || 
													 Utility.COLUMN_8[this.c_position]))) ) {
				/* ^^^ tile must be empty and within checker-board bounds ^^^ */
				
				final int betweenCheckerAndDestination = this.c_position + (this.getPieceColor().getDirection() * 9);
				if (board.getTile(betweenCheckerAndDestination).isOccupied()) {
					
					final Checker checkerBetween = board.getTile(betweenCheckerAndDestination).getChecker();
					if (this.getPieceColor() != checkerBetween.getPieceColor()) {
						
						// check if piece can be promoted to king
						if (this.c_type.isPromotionTile(destination)) {
							possibleMoves.add(new Promotion(new Move.JumpMove(board, this, destination, checkerBetween)));
						} else {
							possibleMoves.add(new Move.JumpMove(board, this, destination, checkerBetween));
						}
					}
				}
			}
		} /* end for loop */
		
		return Collections.unmodifiableList(possibleMoves);
	}
	
	@Override
	public String toString() {
		return PieceRank.NORMAL.toString();
	}

	@Override
	public Normal moveChecker(Move move) {
		return new Normal(move.getMovedChecker().getPieceColor(), move.getDestination());
	}
	
	public Checker getPromotedChecker() {
		return new King(this.c_type, this.c_position);
	}
	
}




















