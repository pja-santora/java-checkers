package board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pieces.Checker;
import pieces.Normal;
import pieces.PieceColor;
import player.BlackPlayer;
import player.Player;
import player.RedPlayer;

public class Board {
	
	private List<Tile> checkerBoard;
	private Collection<Checker> redPieces;
	private Collection<Checker> blackPieces;
	
	private RedPlayer red_player;
	private BlackPlayer black_player;
	private Player current_player;
	
	private Board(final Build build) {
		this.checkerBoard = createBoard(build);
		this.redPieces = setActiveCheckers(this.checkerBoard, PieceColor.RED);
		this.blackPieces = setActiveCheckers(this.checkerBoard, PieceColor.BLACK);
		
		Collection<Move> possibleRedMoves = setMoves(this.redPieces);
		Collection<Move> possibleBlackMoves = setMoves(this.blackPieces);
		
		this.red_player = new RedPlayer(this, possibleRedMoves, possibleBlackMoves);
		this.black_player = new BlackPlayer(this, possibleRedMoves, possibleBlackMoves);
		
		this.current_player = build.playerWithNextMove.choosePlayer(this.red_player, this.black_player);
	}
	
	public Player redPlayer() {
		return this.red_player;
	}
	
	public Player blackPlayer() {
		return this.black_player;
	}
	
	public Player currentPlayer() {
		return this.current_player;
	}
	
	public Collection<Checker> getBlackPieces() {
		return this.blackPieces;
	}
	
	public Collection<Checker> getRedPieces() {
		return this.redPieces;
	}
	
	// for testing ---------------------------\-\-\
	// print current board in console
	@Override
	public String toString() {
		
		StringBuilder s_builder = new StringBuilder();
		for (int i = 0; i < Utility.TILES; ++i) {
			final String tileText = this.checkerBoard.get(i).toString();
			s_builder.append(String.format(" %s", tileText));
			if ((i + 1) % 8 == 0)
				s_builder.append("\n");
		}
		return s_builder.toString();
	}
	// ---------------------------------------/-/-/
	
	private Collection<Move> setMoves(Collection<Checker> checkers) {
		
		List<Move> possibleMoves = new ArrayList<>();
		for (final Checker checker : checkers) {
			possibleMoves.addAll(checker.PossibleMoves(this));
		}
		return Collections.unmodifiableList(possibleMoves);
	}

	private static Collection<Checker> setActiveCheckers(List<Tile> board, PieceColor type) {
		
		List<Checker> activeCheckers = new ArrayList<>();
		for (final Tile tile : board) {
			
			if (tile.isOccupied()) {
				final Checker checker = tile.getChecker();
				if (checker.getPieceColor() == type)
					activeCheckers.add(checker);
			}
		}
		return Collections.unmodifiableList(activeCheckers);
	}

	public Tile getTile(int tileNumber) {
		return checkerBoard.get(tileNumber);
	}
	
	private static List<Tile> createBoard(Build build) {
		
		final Tile[] tiles = new Tile[Utility.TILES];
		
		for (int i = 0; i < Utility.TILES; ++i) {
			tiles[i] = Tile.createTile(i, build.checkerBoardSetup.get(i));
		}
		
		return Collections.unmodifiableList(Arrays.asList(tiles));
	}
	
	public Iterable<Move> getAllPossibleMoves() {
		List<Move> allPossibleMoves = new ArrayList<>();
		allPossibleMoves.addAll(this.red_player.getPossibleMoves());
		allPossibleMoves.addAll(this.black_player.getPossibleMoves());
		return Collections.unmodifiableList(allPossibleMoves);
	}
	
	// create initial board state
	public static Board setInitialBoard() {
		Build builder = new Build();
		
		// RED-Player setup
		builder.setChecker(new Normal(PieceColor.RED, 1));
		builder.setChecker(new Normal(PieceColor.RED, 3));
		builder.setChecker(new Normal(PieceColor.RED, 5));
		builder.setChecker(new Normal(PieceColor.RED, 7));
		builder.setChecker(new Normal(PieceColor.RED, 8));
		builder.setChecker(new Normal(PieceColor.RED, 10));
		builder.setChecker(new Normal(PieceColor.RED, 12));
		builder.setChecker(new Normal(PieceColor.RED, 14));
		builder.setChecker(new Normal(PieceColor.RED, 17));
		builder.setChecker(new Normal(PieceColor.RED, 19));
		builder.setChecker(new Normal(PieceColor.RED, 21));
		builder.setChecker(new Normal(PieceColor.RED, 23));
		
		// BLACK-Player setup
		builder.setChecker(new Normal(PieceColor.BLACK, 40));
		builder.setChecker(new Normal(PieceColor.BLACK, 42));
		builder.setChecker(new Normal(PieceColor.BLACK, 44));
		builder.setChecker(new Normal(PieceColor.BLACK, 46));
		builder.setChecker(new Normal(PieceColor.BLACK, 49));
		builder.setChecker(new Normal(PieceColor.BLACK, 51));
		builder.setChecker(new Normal(PieceColor.BLACK, 53));
		builder.setChecker(new Normal(PieceColor.BLACK, 55));
		builder.setChecker(new Normal(PieceColor.BLACK, 56));
		builder.setChecker(new Normal(PieceColor.BLACK, 58));
		builder.setChecker(new Normal(PieceColor.BLACK, 60));
		builder.setChecker(new Normal(PieceColor.BLACK, 62));
		
		return builder.build();
	}
	
	// helps build checker board
	public static class Build {
		
		Map<Integer, Checker> checkerBoardSetup;
		PieceColor playerWithNextMove;
		
		public Build() {
			this.checkerBoardSetup = new HashMap<>();
			
			// black goes first
			this.playerWithNextMove = PieceColor.BLACK;
		}
		
		public Build setChecker(Checker checker) {
			this.checkerBoardSetup.put(checker.getPosition(), checker);
			return this;
		}
		
		public Build nextMove(final PieceColor playerWithNextMove) {
			this.playerWithNextMove = playerWithNextMove;
			return this;
		}
		
		public Board build() {
			return new Board(this);
		}
	}
}















