package board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
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
		// Initialize field array
		fields = new Field[10][10];
		// Initialize field listener
		fieldListener = new FieldListener();
		// Initialize players with clocks
		players = new Player[2];
		players[0] = new Player(false,false,this);
		players[1] = new Player(false,true,this);
		// Initialize notation
		notation = new Notation(this);
		// Set background to grey-blue color, add border to get some space between the end of this panel
		setBackground(Color.decode("#4B5869"));
		setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		// Create fields
		createFields(fields);
		// Setup chesspieces
		setupChesspieces(fields);
	}
	
	public void createFields(Field[][] fields) {
		// Add GridLayout
		setLayout(new GridLayout(10,10));
		// Create iterators for ids and color switching
		int iteratorID = 0;
		int iteratorColor = 0;
		// r=row (horizontal) c=column (vertical)
		for(int r=0; r<fields.length; r++) {
			for(int c=0; c<fields[r].length; c++) {
				// Find fields which represent top and bottom ending of the board
				if(r==0 || r==9) {
					// Initialize top and bottom border fields
					fields[r][c] = new Field(this,true,null,r-1,c-1,100,true);
					fields[r][c].setBackground(Color.decode("#3D454F"));
					fields[r][c].setForeground(Color.WHITE);
					// Set chess coordinates
					switch(c) {
					case 1: fields[r][c].setText("a"); break;
					case 2: fields[r][c].setText("b"); break;
					case 3: fields[r][c].setText("c"); break;
					case 4: fields[r][c].setText("d"); break;
					case 5: fields[r][c].setText("e"); break;
					case 6: fields[r][c].setText("f"); break;
					case 7: fields[r][c].setText("g"); break;
					case 8: fields[r][c].setText("h"); break;
					}
					// Add to GridLayout
					add(fields[r][c]);
					// Find fields which represent left and right ending of the board
				} else if(c==0 || c==9) {
					// Initialize left and right border fields
					fields[r][c] = new Field(this,true,null,r-1,c-1,100,true);
					fields[r][c].setBackground(Color.decode("#3D454F"));
					fields[r][c].setForeground(Color.WHITE);
					// Set chess coordinates
					switch(r) {
					case 1: fields[r][c].setText("8"); break;
					case 2: fields[r][c].setText("7"); break;
					case 3: fields[r][c].setText("6"); break;
					case 4: fields[r][c].setText("5"); break;
					case 5: fields[r][c].setText("4"); break;
					case 6: fields[r][c].setText("3"); break;
					case 7: fields[r][c].setText("2"); break;
					case 8: fields[r][c].setText("1"); break;
					}
					// Add to GridLayout
					add(fields[r][c]);
				} else {
					// Initialize playing fields (8x8 of two different colors)
					if(iteratorColor%2==0) {
						fields[r][c] = new Field(this,true,null,r-1,c-1,iteratorID,false);
						fields[r][c].setBackground(Color.decode("#DDE2C6"));
						// Add ActionListener
						fields[r][c].addActionListener(fieldListener);
						// Add to GridLayout
						add(fields[r][c]);
						// Increment iterators to get new ids and switch color
						iteratorColor++;
						iteratorID++;
					} else {
						fields[r][c] = new Field(this,false,null,r-1,c-1,iteratorID,false);
						fields[r][c].setBackground(Color.decode("#93827F"));
						// Add ActionListener
						fields[r][c].addActionListener(fieldListener);
						// Add to GridLayout
						add(fields[r][c]);
						// Increment iterators to get new ids and switch color
						iteratorColor++;
						iteratorID++;
					}
				}
			}
			// Increment color iterator to start the next row with opposide color
			iteratorColor++;
		}		
	}
	
	public Field getFieldByID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setupChesspieces(Field[][] fields) {
		for(int r=0; r<fields.length; r++) {
			for(int c=0; c<fields[r].length; c++) {
				// Check if that is not a border field (ID = 100)
				if(fields[r][c].getID()!=100) {
					// Setup black pieces (no Pawns)
					if(fields[r][c].getRow()==0 && fields[r][c].getColumn()==0) {
						fields[r][c].setChesspiece(new Rook(fields[r][c],true,players[1],new ImageIcon("src/gfx/RookBlack.png"),"Rook"));
					} else if(fields[r][c].getRow()==0 && fields[r][c].getColumn()==1) {
						fields[r][c].setChesspiece(new Knight(fields[r][c],true,players[1],new ImageIcon("src/gfx/KnightBlack.png"),"Knight"));
					} else if(fields[r][c].getRow()==0 & fields[r][c].getColumn()==2) {
						fields[r][c].setChesspiece(new Bishop(fields[r][c],true,players[1],new ImageIcon("src/gfx/BishopBlack.png"),"Bishop"));
					} else if(fields[r][c].getRow()==0 & fields[r][c].getColumn()==3) {
						fields[r][c].setChesspiece(new Queen(fields[r][c],true,players[1],new ImageIcon("src/gfx/QueenBlack.png"),"Queen"));
					} else if(fields[r][c].getRow()==0 & fields[r][c].getColumn()==4) {
						fields[r][c].setChesspiece(new King(fields[r][c],true,players[1],new ImageIcon("src/gfx/KingBlack.png"),"King"));
					} else if(fields[r][c].getRow()==0 & fields[r][c].getColumn()==5) {
						fields[r][c].setChesspiece(new Bishop(fields[r][c],true,players[1],new ImageIcon("src/gfx/BishopBlack.png"),"Bishop"));
					} else if(fields[r][c].getRow()==0 & fields[r][c].getColumn()==6) {
						fields[r][c].setChesspiece(new Knight(fields[r][c],true,players[1],new ImageIcon("src/gfx/KnightBlack.png"),"Knight"));
					} else if(fields[r][c].getRow()==0 & fields[r][c].getColumn()==7) {
						fields[r][c].setChesspiece(new Rook(fields[r][c],true,players[1],new ImageIcon("src/gfx/RookBlack.png"),"Rook"));
					}
					// Setup white chesspieces (no pawns)
					if(fields[r][c].getRow()==7 && fields[r][c].getColumn()==0) {
						fields[r][c].setChesspiece(new Rook(fields[r][c],false,players[0],new ImageIcon("src/gfx/RookWhite.png"),"Rook"));
					} else if(fields[r][c].getRow()==7 && fields[r][c].getColumn()==1) {
						fields[r][c].setChesspiece(new Knight(fields[r][c],false,players[0],new ImageIcon("src/gfx/KnightWhite.png"),"Knight"));
					} else if(fields[r][c].getRow()==7 & fields[r][c].getColumn()==2) {
						fields[r][c].setChesspiece(new Bishop(fields[r][c],false,players[0],new ImageIcon("src/gfx/BishopWhite.png"),"Bishop"));
					} else if(fields[r][c].getRow()==7 & fields[r][c].getColumn()==3) {
						fields[r][c].setChesspiece(new Queen(fields[r][c],false,players[0],new ImageIcon("src/gfx/QueenWhite.png"),"Queen"));
					} else if(fields[r][c].getRow()==7 & fields[r][c].getColumn()==4) {
						fields[r][c].setChesspiece(new King(fields[r][c],false,players[0],new ImageIcon("src/gfx/KingWhite.png"),"King"));
					} else if(fields[r][c].getRow()==7 & fields[r][c].getColumn()==5) {
						fields[r][c].setChesspiece(new Bishop(fields[r][c],false,players[0],new ImageIcon("src/gfx/BishopWhite.png"),"Bishop"));
					} else if(fields[r][c].getRow()==7 & fields[r][c].getColumn()==6) {
						fields[r][c].setChesspiece(new Knight(fields[r][c],false,players[0],new ImageIcon("src/gfx/KnightWhite.png"),"Knight"));
					} else if(fields[r][c].getRow()==7 & fields[r][c].getColumn()==7) {
						fields[r][c].setChesspiece(new Rook(fields[r][c],false,players[0],new ImageIcon("src/gfx/RookWhite.png"),"Rook"));
					}
				}
			}
		}
		// Setup Black Pawns
		for(int c=0; c<fields[2].length; c++) {
			if(fields[2][c].getID()!=100) {
				fields[2][c].setChesspiece(new Pawn(fields[2][c],true,players[1],new ImageIcon("src/gfx/PawnBlack.png"),"Pawn"));
			}
		}
		// Setup White Pawns
		for(int c=0; c<fields[7].length; c++) {
			if(fields[7][c].getID()!=100) {
				fields[7][c].setChesspiece(new Pawn(fields[7][c],true,players[0],new ImageIcon("src/gfx/PawnWhite.png"),"Pawn"));
			}
		}
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
	
	public Notation getNotation() {
		return notation;
	}
	
	public Player getPlayerByColor(boolean isBlack) {
		if(!isBlack) {
			return players[0];
		} else {
			return players[1];
		}
	}
	
	private class FieldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Field tmpField = (Field) event.getSource();
			System.out.println("You clicked on field id " + tmpField.getID() + " which is on row " + tmpField.getRow() + " and on column " + tmpField.getColumn());
		}
	}
}
