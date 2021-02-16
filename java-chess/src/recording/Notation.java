package recording;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import board.Board;

public class Notation extends JPanel {
	private Board board;
	private List<Entry> entryList;
	
	public Notation(Board board) {
		this.board = board;
		entryList = new ArrayList<Entry>();
		// TODO Auto-generated method stub
	}
	
	public void addEntry(Entry entry) {
		// TODO Auto-generated method stub
	}
}
