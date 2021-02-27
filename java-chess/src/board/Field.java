package board;

import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
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
		// Creating Icons for chesspieces
		Icon icRookWhite = new ImageIcon("src/gfx/RookWhite.png");
		Icon icRookBlack = new ImageIcon("src/gfx/RookBlack.png");
		Icon icKnightWhite = new ImageIcon("src/gfx/KnightWhite.png");
		Icon icKnightBlack = new ImageIcon("src/gfx/KnightBlack.png");
		Icon icBishopWhite = new ImageIcon("src/gfx/BishopWhite.png");
		Icon icBishopBlack = new ImageIcon("src/gfx/BishopBlack.png");
		Icon icQueenWhite = new ImageIcon("src/gfx/QueenWhite.png");
		Icon icQueenBlack = new ImageIcon("src/gfx/QueenBlack.png");
		Icon icKingWhite = new ImageIcon("src/gfx/KingWhite.png");
		Icon icKingBlack = new ImageIcon("src/gfx/KingBlack.png");
		Icon icPawnWhite = new ImageIcon("src/gfx/PawnWhite.png");
		Icon icPawnBlack = new ImageIcon("src/gfx/PawnBlack.png");
		// Which color is moving?
		if(!chesspiece.isBlack()) {
			switch(chesspiece.getType()) {
			case "Rook": setIcon(icRookWhite); break;
			case "Knight": setIcon(icKnightWhite); break;
			case "Bishop": setIcon(icBishopWhite); break;
			case "Queen": setIcon(icQueenWhite); break;
			case "King": setIcon(icKingWhite); break;
			case "Pawn": setIcon(icPawnWhite); break;
			}
		} else {
			switch(chesspiece.getType()) {
			case "Rook": setIcon(icRookBlack); break;
			case "Knight": setIcon(icKnightBlack); break;
			case "Bishop": setIcon(icBishopBlack); break;
			case "Queen": setIcon(icQueenBlack); break;
			case "King": setIcon(icKingBlack); break;
			case "Pawn": setIcon(icPawnBlack); break;
			}
		}
		// Field is occupied by this chesspiece now
		isOccupied = true;
	}
	
	public void deleteChesspiece() {
		this.chesspiece = null;
		this.isOccupied = false;
		setIcon(null);
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
	
	public boolean isBlack() {
		return isBlack;
	}
}
