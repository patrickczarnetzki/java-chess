package board;

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
		// TODO Auto-generated method stub
	}
	
	public void setChesspiece(Chesspiece chesspiece) {
		// TODO Auto-generated method stub
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
