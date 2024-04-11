package player;

import board.Board;
import board.Move;

// shift board state after play decides to make a move
public class MoveShift {

	private Board shiftedBoard;
	private Move move;
	private Status status;
	
	public MoveShift(Board shifted, Move move, Status status) {
		this.shiftedBoard = shifted;
		this.move = move;
		this.status = status;
	}
	
	public Status getStatus() {
		return this.status;
	}
	
	public Board getShiftedBoard() {
		return this.shiftedBoard;
	}
	
	public Move getShiftedMove() {
		return this.move;
	}
}
