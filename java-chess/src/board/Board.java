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

import gui.menu.Transformation;
import pieces.Bishop;
import pieces.Chesspiece;
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
		// Initialize variables
		fields = new Field[10][10];
		fieldListener = new FieldListener();
		players = new Player[2];
		players[0] = new Player(true,false,this);
		players[1] = new Player(false,true,this);
		notation = new Notation(this);
		isGameStarted = false;
		isGamePaused = false;
		isStartingMovement = true;
		turn = 0;
		round = 0;
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
		Field foundField = null;
		for(int r=0; r<fields.length; r++) {
			for(int c=0; c<fields.length; c++) {
				if(fields[r][c].getID()==ID) {
					foundField = fields[r][c];
				}
			}
		}
		return foundField;
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
				fields[7][c].setChesspiece(new Pawn(fields[7][c],false,players[0],new ImageIcon("src/gfx/PawnWhite.png"),"Pawn"));
			}
		}
	}
	
	public King searchAndGetKing(Boolean isBlack) {
		King foundKing = null;
		// Go through all fields of the board
		for(int r=0; r<fields.length; r++) {
			for(int c=0; c<fields[r].length; c++) {
				// Check if field is occupied by a chesspiece
				if(fields[r][c].getChesspiece()!=null) {
					// Check if that chesspiece is a King
					if(fields[r][c].getChesspiece().getType().equals("King")) {
						// Check if that King is of the right color
						if(fields[r][c].getChesspiece().isBlack()==isBlack) {
							// Right King found
							foundKing = (King) fields[r][c].getChesspiece();
						}
					}
				}
			}
		}
		return foundKing;
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
		isGamePaused = true;
	}
	
	public void resume() {
		isGamePaused = false;
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
		// Used to store the chesspiece on the clicked field if there is a chesspiece
		private Chesspiece chesspiece = null;
		// Used to store the clicked field
		private Field clickedField = null;
		// Used to store position of first clicked field (starting field)
		private int startingFieldID = 0;
		// Used to store position of second clicked field (ending field)
		private int endingFieldID = 0;
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// Get clicked field and store it in clickedField
			for(int r=0; r<fields.length; r++) {
				for(int c=0; c<fields[r].length; c++) {
					if(event.getSource().equals(fields[r][c])) {
						clickedField = fields[r][c];
						break;
					}
				}
			}
			// Check if game is not paused
			if(!isGamePaused()) {
				// Check for starting movement
				if(isStartingMovement()) {
					// Store ID of clicked field in startingFieldID (position where movement starts)
					startingFieldID = clickedField.getID();
					// Store chesspiece of clicked field in chesspiece
					chesspiece = clickedField.getChesspiece();
					// Check if clicked field wasn't empty
					if(chesspiece!=null) {
						// Check for first move by black (Invalid movement, white has to start the game)
						if(chesspiece.isBlack() && !isGameStarted()) {
							System.out.println("White has to start the match");
						// Check for first move of the match by white (valid move)
						} else if(!chesspiece.isBlack() && players[0].isPlaying() && !isGameStarted()) {
							// Delete chesspiece on starting position
							clickedField.deleteChesspiece();
							// Switch to second move (ending movement)
							isStartingMovement = !isStartingMovement;
						// Check for black movement
						} else if(chesspiece.isBlack() && players[1].isPlaying() && isGameStarted()) {
							// Delete chesspiece on starting position
							clickedField.deleteChesspiece();
							// Switch to second move (ending movement)
							isStartingMovement = !isStartingMovement;
						// Check for white movement
						} else if(!chesspiece.isBlack() && players[0].isPlaying() && isGameStarted()) {
							// Delete chesspiece on starting position
							clickedField.deleteChesspiece();
							// Switch to second move (ending movement)
							isStartingMovement = !isStartingMovement;
						// Wrong color is moving (Invalid movement)
						} else {
							if(players[0].isPlaying()) {
								System.out.println("White has to move next");
							} else if(players[1].isPlaying()) {
								System.out.println("Black has to move next");
							}
						}
					}
				} else {
					// Ending movement
					// Store ID of clicked field in endingFieldID
					endingFieldID = clickedField.getID();
					// Check for valid movement
					if(chesspiece.isValidMovement(clickedField) ) {
						// Check if king would not be checkmate after movement
						/// Store chesspiece on clickedField for restoring
						Chesspiece chesspieceClickedField = clickedField.getChesspiece();
						/// Set chesspiece temporary to ending field
						clickedField.setChesspiece(chesspiece);
						/// Do the check test
						King tmpKing = searchAndGetKing(chesspiece.isBlack());
						if(!tmpKing.isInCheck()) {
							// Delete temporary settings
							if(chesspieceClickedField!=null) {
								clickedField.setChesspiece(chesspieceClickedField);
							}
							// Do the move and set chesspiece to new position
							clickedField.setChesspiece(chesspiece);
							// Check for Rochade
							if(chesspiece.getType().equals("King")) {
								King tmpRochadeKing = (King) chesspiece;
								// Check for long rochade
								if(tmpRochadeKing.isUsingLongRochade()) {
									// Move left Rook
									Rook tmpRook = (Rook) tmpRochadeKing.getField().getBoard().getFieldByID(tmpRochadeKing.getField().getID()-2).getChesspiece();
									tmpRochadeKing.getField().getBoard().getFieldByID(tmpRochadeKing.getField().getID()+1).setChesspiece(tmpRook);
									tmpRochadeKing.getField().getBoard().getFieldByID(tmpRochadeKing.getField().getID()-2).deleteChesspiece();
									// After using it can't be reused
									tmpRochadeKing.setIsUsingLongRochade(false);
									// Also short rochade can't be used anymore
									tmpRochadeKing.setIsUsingShortRochade(false);
								}
								// Check for short rochade
								if(tmpRochadeKing.isUsingShortRochade()) {
									// Move right Rook
									Rook tmpRook = (Rook) tmpRochadeKing.getField().getBoard().getFieldByID(tmpRochadeKing.getField().getID()+1).getChesspiece();
									tmpRochadeKing.getField().getBoard().getFieldByID(tmpRochadeKing.getField().getID()-1).setChesspiece(tmpRook);
									tmpRochadeKing.getField().getBoard().getFieldByID(tmpRochadeKing.getField().getID()+1).deleteChesspiece();
									// After using it can't be reused
									tmpRochadeKing.setIsUsingShortRochade(false);
									// Also long rochade can't be used anymore
									tmpRochadeKing.setIsUsingLongRochade(false);
								}
							}
							// Check for Pawn transformation and en-passant
							if(chesspiece.getType().equals("Pawn")) {
								Pawn tmpPawn = (Pawn) chesspiece;
								// Check for en-passant
								if(tmpPawn.isUsingEnPassant()) {
									// Delete enemy Pawn
									tmpPawn.getEnemyPawn().getField().deleteChesspiece();
								}
								// Check for transformation
								if(tmpPawn.isTransforming()) {
									// Open transformation menu and pause the game until transformation is done
									pause();
									// Game will be resumed by choosing a chesspiece in transformation menu
									Transformation transformation = new Transformation(clickedField);
								}
							}							
							// Switch to starting move
							isStartingMovement = !isStartingMovement;
							// Check if it was the first move of the game to start the black clock for the first time
							if(players[0].isPlaying() && !isGameStarted()) {
								// Set game to started
								isGameStarted = true;
								// Start black clock
							}
							// Switch players and clocks
							if(players[0].isPlaying()) {
								// White was playing, switch to black
								players[0].setIsPlaying(false);
								players[1].setIsPlaying(true);
							} else {
								// Black was playing, switch to white
								players[0].setIsPlaying(true);
								players[1].setIsPlaying(false);
							}
							// Add movement to notation component
							
							// One turn is over (Turn = One move of white or black player)
							turn++;
							// Check if round is over (Round = Two moves, one move of each player (black and white)
							if(turn%2==0) {
								round++;
							}
							// Check if opponent is checkmate now to stop the game
							if(!chesspiece.isBlack()) {
								// Player is playing white, start checkmate test for black
								if(searchAndGetKing(true).isCheckmate()) {
									System.out.println("Black is checkmate, White wins!");
								}
							} else {
								// Player is playing black, start checkmate test for white
								if(searchAndGetKing(false).isCheckmate()) {
									System.out.println("White is checkmate, Black wins!");
								}
							}
						} else {
							// King would be checkmate
							// Restore old settings
							isStartingMovement = !isStartingMovement;
							// Set chesspiece back to starting position
							getFieldByID(startingFieldID).setChesspiece(chesspiece);
							// Check if ending field was occupied an restore it
							if(chesspieceClickedField!=null) {
								getFieldByID(endingFieldID).setChesspiece(chesspieceClickedField);
							} else {
								// Delete old chesspiece
								getFieldByID(endingFieldID).deleteChesspiece();
							}
							// Restore rochade settings
							if(tmpKing.isUsingLongRochade()) {
								tmpKing.setIsUntouched(true);
								tmpKing.setIsUsingLongRochade(false);
							} else if(tmpKing.isUsingShortRochade()) {
								tmpKing.setIsUntouched(true);
								tmpKing.setIsUsingShortRochade(false);
							}
						}
					} else {
						// Invalid movement
						System.out.println("Invalid Move");
						// Restore old settings
						isStartingMovement = !isStartingMovement;
						getFieldByID(startingFieldID).setChesspiece(chesspiece);
					}
				}
			}
		}
	}
}
