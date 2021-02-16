package board;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import pieces.King;
import player.Player;
import recording.Notation;

public class Board extends JPanel {
	private Player[] players;
	private Notation notation;
	private Field[][] fields;
	private FieldListener fieldListener;
	private boolean isGameStarted;
	private boolean isGamePaused;
	private boolean isStartingMovement;
	private int turn;
	private int round;
	
	public Board() {
		createFields(fields);
		setupChesspieces(fields);
		// TODO Auto-generated method stub
	}
	
	public void createFields(Field[][] fields) {
		// TODO Auto-generated method stub
	}
	
	public Field getFieldByID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setupChesspieces(Field[][] fields) {
		// TODO Auto-generated method stub
	}
	
	public King searchAndGetKing(Boolean isBlack) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isGameStarted() {
		return isGameStarted;
	}
	
	public boolean isGamePaused() {
		return isGamePaused;
	}
	
	public boolean isStartingMovement() {
		return isStartingMovement;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int getRound() {
		return round;
	}
	
	public void pause() {
		// TODO Auto-generated method stub
	}
	
	public void resume() {
		// TODO Auto-generated method stub
	}
	
	private class FieldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
