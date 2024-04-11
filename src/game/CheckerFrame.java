package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import board.Board;
import board.Move;
import board.Tile;
import board.Utility;
import pieces.Checker;
import player.MoveShift;

public class CheckerFrame {
	
	private JFrame FRAME;
	private CheckerBoard BOARD;
	private Board CURRENT_BOARD;
	private Tile START;
	private Tile DESTINATION;
	private Checker PIECE;
	
	private static final Dimension FRAME_DIMENSION = new Dimension(790, 780);
	private static final Dimension BOARD_DIMENSION = new Dimension(650, 650);
	private static final Dimension TILE_DIMENSION = new Dimension(80, 80);
	
	private static final Color RED_COLOR = new Color(200, 35, 20);
	private static final Color BLACK_COLOR = new Color(10, 10, 20);
	private static final Color WHITE_COLOR = new Color(210, 210, 210);
	private static final Color BROWN_COLOR = new Color(40, 25, 10);
	
	private static final String path_ = "pieces/";
	
	public CheckerFrame() {
		this.FRAME = new JFrame("JAVA - CHECKERS");
		this.FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.FRAME.setSize(FRAME_DIMENSION);
		this.FRAME.setLocationRelativeTo(null);
		this.FRAME.setMaximumSize(FRAME_DIMENSION);
		this.FRAME.setMinimumSize(FRAME_DIMENSION);
		this.FRAME.setLayout(new BorderLayout());
		
		this.CURRENT_BOARD = Board.setInitialBoard();
		this.BOARD = new CheckerBoard();
		this.FRAME.add(this.BOARD, BorderLayout.CENTER);
		
		this.FRAME.setVisible(true);
	}
	
	// lay checker tiles on board
	private class CheckerBoard extends JPanel {
		
		private static final long serialVersionUID = 1L;
		
		List<CheckerTile> boardTiles;
		
		CheckerBoard() {
			
			super(new GridLayout(8, 8));
			this.boardTiles = new ArrayList<>();
			
			for (int i = 0; i < Utility.TILES; ++i) {
				CheckerTile tile = new CheckerTile(this, i);
				this.boardTiles.add(tile);
				add(tile);
			}
			setPreferredSize(BOARD_DIMENSION);
			setBorder(BorderFactory.createLineBorder(BROWN_COLOR, 10));
			validate();
		}
		
		public void drawBoard(Board board) {
			removeAll();
			for (CheckerTile b_tile : boardTiles) {
				b_tile.drawTile(board);
				add(b_tile);
				validate();
				repaint();
			}
		}
	}
	
	// represents each individual tile on the board
	private class CheckerTile extends JPanel {

		private static final long serialVersionUID = 1L;
		private int tile_position;
		
		CheckerTile(CheckerBoard board, int position) {
			
			super(new GridBagLayout());
			this.tile_position = position;
			setPreferredSize(TILE_DIMENSION);
			setTileColor();
			addChecker(CURRENT_BOARD);
			
			// listen for clicks on each tile 
			addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					
					if (START == null) {
						START = CURRENT_BOARD.getTile(position);
						PIECE = START.getChecker();
						if (PIECE == null) {
							START = null;
						}
					} else {
						DESTINATION = CURRENT_BOARD.getTile(position);
						Move move = Move.FactoryMove.createMove(CURRENT_BOARD, START.getTileNumber(), 
								DESTINATION.getTileNumber());
						MoveShift shift = CURRENT_BOARD.currentPlayer().makeMove(move);
						if (shift.getStatus().isDone()) {
							CURRENT_BOARD = shift.getShiftedBoard();
						}
						START = null;
						DESTINATION = null;
						PIECE = null;
					}
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							board.drawBoard(CURRENT_BOARD);
						}
					});
					
					// if no more piece of one player end the game
					if (CURRENT_BOARD.getBlackPieces().isEmpty()) {
						FRAME.dispose();
						new GameOver("RED");
					}
					else if (CURRENT_BOARD.getRedPieces().isEmpty()) {
						FRAME.dispose();
						new GameOver("BLACK");
					}
				}
				
				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}
				
			});
			validate();
		}
		
		private void highlightTiles(Board board) {
			for (Move move : checkerMoves(board)) {
				if (move.getDestination() == this.tile_position) {
					try {
						BufferedImage highlight = ImageIO.read(CheckerFrame.class.getResource(path_ + "CH.png"));
						add(new JLabel(new ImageIcon(highlight)));
					} catch (Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
		private Collection<Move> checkerMoves(Board board) {
			if (PIECE != null && PIECE.getPieceColor() == board.currentPlayer().getPieceColor())
				return PIECE.PossibleMoves(board);
			
			return Collections.emptyList();
		}

		public void drawTile(final Board board) {
			setTileColor();
			addChecker(board);
			highlightTiles(board);
			validate();
			repaint();
		}
		
		private void addChecker(Board board) {
			this.removeAll();
			if (board.getTile(this.tile_position).isOccupied()) {
				
				try { // get file in folder with correct filename
					BufferedImage checkerIcon = ImageIO.read(CheckerFrame.class.getResource(path_ + 
							board.getTile(this.tile_position).getChecker().getPieceRank().toString() + 
							board.getTile(this.tile_position).getChecker().getPieceColor().toString() + ".png"));
					add(new JLabel(new ImageIcon(checkerIcon)));
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void setTileColor() {
			
			if (Utility.COLUMN_1[this.tile_position] || Utility.COLUMN_3[this.tile_position]||
					Utility.COLUMN_5[this.tile_position] || Utility.COLUMN_7[this.tile_position]) {
				setBackground((this.tile_position / 8) % 2 == 0 ? RED_COLOR : BLACK_COLOR);
			}
			else if (Utility.COLUMN_2[this.tile_position] || Utility.COLUMN_4[this.tile_position]||
					Utility.COLUMN_6[this.tile_position] || Utility.COLUMN_8[this.tile_position]) {
				setBackground((this.tile_position / 8) % 2 != 0 ? RED_COLOR : BLACK_COLOR);
			}	
			setBorder(BorderFactory.createLineBorder(WHITE_COLOR));
		}
	}
}






























