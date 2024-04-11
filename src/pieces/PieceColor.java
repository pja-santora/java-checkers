package pieces;

import board.Utility;
import player.Player;

public enum PieceColor {
	
	RED {
		@Override
		public int getDirection() {
			return 1;
		}

		@Override
		public boolean isRed() {
			return true;
		}

		@Override
		public boolean isBlack() {
			return false;
		}

		@Override
		public Player choosePlayer(Player red_player, Player black_player) {
			return red_player;
		}
		
		@Override
		public String toString() {
			return "R";
		}

		@Override
		public boolean isPromotionTile(int position) {
			return Utility.ROW_8[position];
		}
	},
	BLACK {
		@Override
		public int getDirection() {
			return -1;
		}

		@Override
		public boolean isRed() {
			return false;
		}

		@Override
		public boolean isBlack() {
			return true;
		}

		@Override
		public Player choosePlayer(Player red_player, Player black_player) {
			return black_player;
		}
		
		@Override
		public String toString() {
			return "B";
		}

		@Override
		public boolean isPromotionTile(int position) {
			return Utility.ROW_1[position];
		}
	};
	
	public abstract int getDirection();
	
	public abstract boolean isRed();
	public abstract boolean isBlack();
	public abstract boolean isPromotionTile(int position);
	
	public abstract Player choosePlayer(Player red_player, Player black_player);
}































