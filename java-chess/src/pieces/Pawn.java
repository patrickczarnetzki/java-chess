package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Pawn extends Chesspiece {
	private boolean isUntouched;
	private boolean isEnPassant;
	private boolean isTransforming;

	public Pawn(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
		isUntouched = true;
		isEnPassant = false;
		isTransforming = false;
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
		// Check for empty ending field
		if(!endingField.isOccupied()) {
			// Check playing color to choose right direction (white pawns move from bottom to top and black pawns from top to bottom)
			if(!isBlack()) {
				// Check if this Pawn is moving for the first time
				if(isUntouched()) {
					// Create possible movement (-16,-8)
					int[] possibleMoves = {-16,-8};
					// Go through all possible moves and check if addition of possible movement to the starting position is resulting into the expected ending position
					for(int i=0; i<possibleMoves.length; i++) {
						if(startingFieldID+possibleMoves[i]==endingFieldID) {
							// Valid movement detected
							// Check if Pawn moved one or two fields (necessary for en-passant)
							if(possibleMoves[i]==-16) {
								isEnPassant = true;
							}
							// Pawn will move and will not be untouched anymore
							isUntouched = false;
							// Return valid movement
							return true;
						}
					}
				} else {
					// Was moved already (Not untouched)
					// Create possible movement (-8)
					int possibleMove = -8;
					// Check if addition of possibleMove (-8) to the starting position will result into the expected ending position
					if(startingFieldID+possibleMove==endingFieldID) {
						// Valid movement detected
						return true;
					}
				}
			} else {
				// Black is playing
				// Check if this Pawn is moving for the first time
				if(isUntouched()) {
					// Create possible movement (8,16)
					int[] possibleMoves = {8,16};
					// Go through all possible moves and check if addition of possible movement to the starting position will result into the expected ending position
					for(int i=0; i<possibleMoves.length; i++) {
						if(startingFieldID+possibleMoves[i]==endingFieldID) {
							// Valid movement detected
							// Check if Pawn moved one or two fields (necessary for en-passant)
							if(possibleMoves[i]==16) {
								isEnPassant = true;
							}
							// Pawn will move and will not be untouched anymore
							isUntouched = false;
							// Return valid movement
							return true;
						}
					}
				} else {
					// Was moved already (Not untouched)
					// Create possible movement (8)
					int possibleMove = 8;
					// Check if addition of possibleMove (8) to the starting position will result into the expected ending position 
					if(startingFieldID+possibleMove==endingFieldID) {
						// Valid movement detected
						return true;
					}
				}
			}
		} else {
			// Field was occupied
			// Create a list to store found valid movement positions
			List<Integer> validMovement = new ArrayList<Integer>();
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
				// Check for a match
				for(int i=0; i<validMovement.size(); i++) {
					if(startingFieldID+validMovement.get(i)==endingFieldID) {
						// Check for transformation
						if(endingRow==0) {
							isTransforming = true;
						}
						// Return valid movement
						return true;
					}
				}
			}
		}
		return false;
	}
}
