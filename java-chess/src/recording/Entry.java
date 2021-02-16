package recording;

import javax.swing.JTextField;

import pieces.Chesspiece;

public class Entry extends JTextField {
	private int id;
	private Chesspiece chesspiece;
	private String entry;
	
	public Entry(int id, Chesspiece chesspiece) {
		this.id = id;
		this.chesspiece = chesspiece;
		// TODO Auto-generated method stub
	}
	
	public int getID() {
		return id;
	}
	
	public String getEntry() {
		return entry;
	}
}
