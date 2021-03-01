package player;

import com.google.gson.annotations.Expose;

import board.Board;
import recording.Clock;

public class Player {
	@Expose
	private boolean isPlaying;
	private boolean isBlack;
	private Board board;
	@Expose
	private Clock clock;
	
	public Player(boolean isPlaying, boolean isBlack, Board board, int clockMinutes, int clockSeconds) {
		this.isPlaying = isPlaying;
		this.isBlack = isBlack;
		this.board = board;
		this.clock = new Clock(this,clockMinutes,clockSeconds);
	}
	
	public boolean isPlaying() {
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
	
	public void setIsPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
}