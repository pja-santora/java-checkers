package player;

public enum Status {
	DONE {
		@Override
		public boolean isDone() {
			return true;
		}
	},
	ILLEGAL {
		@Override
		public boolean isDone() {
			return false;
		}
	};
	
	public abstract boolean isDone();
}
