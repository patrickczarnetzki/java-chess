package player;

import board.Board;
import recording.Clock;

public class Player {
	private boolean isPlaying;
	private boolean isBlack;
	private Board board;
	private Clock clock;
	
	public Player(boolean isPlaying, boolean isBlack, Board board) {
		this.isPlaying = isPlaying;
		this.isBlack = isBlack;
		this.board = board;
		this.clock = null;
		// TODO Auto-generated method stub
	}
	
	public boolean isPlaing() {
		return isPlaying;
	}
	
	public boolean isBlack() {
		return isBlack;
	}
	
	public Clock getClock() {
		return clock;
	}
	
	public Board getBoard() {
		return board;
	}
}
