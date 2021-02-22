package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Pawn extends Chesspiece {
	private boolean isUntouched;
	private boolean isEnPassant; // Can be beaten by en-passant
	private boolean isTransforming;
	private boolean isUsingEnPassant; // Has beaten opponent Pawn by en-passant move
	Pawn enemyPawn; // Stores the enemy Pawn for en-passant

	public Pawn(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
		isUntouched = true;
		isEnPassant = false;
		isTransforming = false;
		isUsingEnPassant = false;
		enemyPawn = null;
	}
	
	public boolean isEnPassant() {
		return isEnPassant;
	}
	
	public boolean isTransforming() {
		return isTransforming;
	}
	
	public boolean isUntouched() {
		return isUntouched;
	}
	
	public boolean isUsingEnPassant() {
		return isUsingEnPassant;
	}
	
	public Pawn getEnemyPawn() {
		return enemyPawn;
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// Store IDs of starting and ending field
		int startingFieldID = getField().getID();
		int endingFieldID = endingField.getID();
		// Get starting column for exception handling on column 0 and column 7
		int startingColumn = getField().getColumn();
		// Get starting row for en-passant detection
		int startingRow = getField().getRow();
		// Get ending row for transformation detection
		int endingRow = endingField.getRow();
		// Create a list to store found valid movement positions
		List<Integer> validMovement = new ArrayList<Integer>();
		// Check for empty ending field
		if(!endingField.isOccupied()) {
			// Check if pawn was moved already
			if(isUntouched()) {
				// Check playing color to choose right direction (white pawns move from bottom to top and black pawns from top to bottom)
				if(!isBlack()) {
					// Add possible movement of white to ArrayList
					validMovement.add(-16);
					validMovement.add(-8);
				} else {
					// Add possible movement of black to ArrayList
					validMovement.add(16);
					validMovement.add(8);
				}
			} else {
				// Was moved already (Not untouched)
				// Check playing color to choose right direction
				if(!isBlack()) {
					// Add possible movement of white to ArrayList
					// Add possible En-Passant movement first
					isBeatingWithEnPassant(startingFieldID,startingRow,startingColumn,false,endingField,validMovement);
					// Add normal movement
					validMovement.add(-8);
				} else {
					// Add possible movement of black to ArrayList
					// Add possible En-Passant movement first
					isBeatingWithEnPassant(startingFieldID,startingRow,startingColumn,true,endingField,validMovement);
					// Add normal movement
					validMovement.add(8);
				}
			}
		} else {
			// Field was occupied
			// Check for opponent chesspiece on ending field
			if(endingField.getChesspiece().isBlack()!=isBlack()) {
				// Check playing color to choose right direction (white pawns move from bottom to top and black pawns from top to bottom)
				if(!isBlack()) {
					// White pawn is attacking
					// Check for column 0 for exception handling
					if(startingColumn==0) {
						// Add valid movement position to ArrayList
						validMovement.add(-7);
					} else if(startingColumn==7) {
						// Add valid movement position to ArrayList
						validMovement.add(-9);
					} else {
						// Add valid movement position to ArrayList
						validMovement.add(-9);
						validMovement.add(-7);
					}
				} else {
					// Black pawn is attacking
					// Check for column 0 for exception handling
					if(startingColumn==0) {
						// Add valid movement position to ArrayList
						validMovement.add(9);
					} else if(startingColumn==7) {
						// Add valid movement position to ArrayList
						validMovement.add(7);
					} else {
						// Add valid movement positions to ArrayList
						validMovement.add(7);
						validMovement.add(9);
					}
				}
			}
		}
		// Check for a match
		for(int i=0; i<validMovement.size(); i++) {
			if(startingFieldID+validMovement.get(i)==endingFieldID) {
				// Check if Pawn moved one or two fields (necessary for en-passant)
				if(validMovement.get(i)==16 || validMovement.get(i)==-16) {
					isEnPassant = true;
				}
				// Check for transformation
				if((!isBlack() && endingRow==0)) {
					isTransforming = true;
				} else if(isBlack() && endingRow==7) {
					isTransforming = true;
				}
				// Pawn will be moved now, switch to untouched = false if it was true before
				if(isUntouched = true) {
					isUntouched = false;
				}
				// Return valid movement
				return true;
			}
		}
		return false;
	}
	
	public void isBeatingWithEnPassant(int startingFieldID, int startingRow, int startingColumn, boolean isBlack, Field endingField, List<Integer> validMovement) {
		// Check for en-passant attack
		// List with chesspieces to check later
		List<Chesspiece> chesspieceList = new ArrayList<Chesspiece>();
		int possibility = 0; // Is used to store the case which happened. 0 = no en-passant case, 1 = white try to use en passant on row 3 column 0
		// Correct row for white to use en-passant is 3
		// Check column 0 for exception handling
		if(!isBlack && startingRow==3 && startingColumn==0) {
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID+1).getChesspiece());
			possibility = 1;
		// Check column 7 for exception handling
		} else if(!isBlack && startingRow==3 && startingColumn==7) {
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID-1).getChesspiece());
			possibility = 2;
		} else if((!isBlack && startingRow==3 && startingColumn!=7) && (!isBlack && startingRow==3 && startingColumn!=0)) {
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID-1).getChesspiece());
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID+1).getChesspiece());
			if(chesspieceList.get(0)!=null && chesspieceList.get(1)==null) {
				possibility = 31;
			} else if(chesspieceList.get(0)==null && chesspieceList.get(1)!=null) {
				possibility = 32;
			} else if(chesspieceList.get(0)!=null && chesspieceList.get(1)!=null) {
				possibility = 33;
			}
		}
		// Correct row for black to use en-passant is 4
		// Check column 0 for exception handling
		if(isBlack && startingRow==4 && startingColumn==0) {
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID+1).getChesspiece());
			possibility = 4;
		// Check column 7 for exception handling
		} else if(isBlack && startingRow==4 && startingColumn==7) {
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID-1).getChesspiece());
			possibility = 5;
		} else if((isBlack && startingRow==4 && startingColumn!=7) && (isBlack && startingRow==4 && startingColumn!=0)) {
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID-1).getChesspiece());
			chesspieceList.add(endingField.getBoard().getFieldByID(startingFieldID+1).getChesspiece());
			if(chesspieceList.get(0)!=null && chesspieceList.get(1)==null) {
				possibility = 61;
			} else if(chesspieceList.get(0)==null && chesspieceList.get(1)!=null) {
				possibility = 62;
			} else if(chesspieceList.get(0)!=null && chesspieceList.get(1)!=null) {
				possibility = 63;
			}
		}
		for(int i=0; i<chesspieceList.size(); i++) {
			// Check if chesspiece isn't null (Field was empty)
			if(chesspieceList.get(i)!=null) {
				// Check if chesspiece is a Pawn
				if(chesspieceList.get(i).getType().equals("Pawn")) {
					// We are looking for an enemy Pawn
					if(chesspieceList.get(i).isBlack()==!isBlack) {
						// Store chesspiece as Pawn
						enemyPawn = (Pawn) chesspieceList.get(i);
						// Check if en-passant move is possible against the enemy Pawn
						if(enemyPawn.isEnPassant()) {
							// Check which case of en-passant is happening
							switch(possibility) {
							case 1: validMovement.add(-7); break;
							case 2: validMovement.add(-9); break;
							case 31: validMovement.add(-9); break;
							case 32: validMovement.add(-7); break;
							case 33: validMovement.add(-7); validMovement.add(-9); break;
							case 4: validMovement.add(9); break;
							case 5: validMovement.add(7); break;
							case 61: validMovement.add(7); break;
							case 62: validMovement.add(9); break;
							case 63: validMovement.add(7); validMovement.add(9); break;
							}
							isUsingEnPassant = true;
						}
					}
				}
			}	
		}
	}
}
