package board;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;

import pieces.Chesspiece;

public class Field extends JButton {
	private Board board;
	private boolean isBlack;
	private Chesspiece chesspiece;
	private int row;
	private int column;
	private int id;
	private boolean isOccupied;
	
	public Field(Board board, boolean isBlack, Chesspiece chesspiece, int row, int column, int id, boolean isOccupied) {
		this.board = board;
		this.isBlack = isBlack;
		this.chesspiece = chesspiece;
		this.row = row;
		this.column = column;
		this.id = id;
		this.isOccupied = isOccupied;
		// Styling
		setBorder(null);
		setMargin(new Insets(0,0,0,0));
	}
	
	public void setChesspiece(Chesspiece chesspiece) {
		this.chesspiece = chesspiece;
		this.chesspiece.setField(this);
		// Set ImageIcon
		setIcon(chesspiece.getIcon());
		// Field is occupied by this chesspiece now
		isOccupied = true;
	}
	
	public void deleteChesspiece() {
		// TODO Auto-generated method stub
	}
	
	public Chesspiece getChesspiece() {
		return chesspiece;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public int getID() {
		return id;
	}
	
	public boolean isOccupied() {
		return isOccupied;
	}
	
	public Board getBoard() {
		return board;
	}
}
