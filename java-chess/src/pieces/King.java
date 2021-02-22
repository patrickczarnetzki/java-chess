package pieces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class King extends Chesspiece {
	private boolean isUntouched;
	private boolean isUsingShortRochade;
	private boolean isUsingLongRochade;
	private boolean isInCheck;
	
	public King(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
		isUntouched = true;
		isUsingShortRochade = false;
		isUsingLongRochade = false;
		isInCheck = false;
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// Store IDs of starting and ending field
		int startingFieldID = getField().getID();
		int endingFieldID = endingField.getID();
		// Store starting and ending column for exception handling on column 0 and 7 and movement calculation
		int startingColumn = getField().getColumn();
		int endingColumn = endingField.getColumn();
		// Store starting and ending row for movement calculation
		int startingRow = getField().getRow();
		int endingRow = endingField.getRow();
		// Create a list which stores valid field id's
		List<Integer> validMovement = new ArrayList<Integer>();
		// Attacking and Movement is the same
		// Check for exception on column 0
		if(startingColumn==0) {
			validMovement.add(-8);
			validMovement.add(-7);
			validMovement.add(1);
			validMovement.add(8);
			validMovement.add(9);
		// Check for exception on column 7
		} else if(startingColumn==7) {
			validMovement.add(-9);
			validMovement.add(-8);
			validMovement.add(-1);
			validMovement.add(7);
			validMovement.add(8);
		// Has to be normal movement
		} else {
			// Check for long and short rochade
			// Check if king wasn't moved yet
			if(isUntouched() ) {
				// Check for long rochade
				if(endingFieldID==startingFieldID-2) {
					// Check if Rook is on starting field
					Chesspiece tmpChesspiece = endingField.getBoard().getFieldByID(startingFieldID-4).getChesspiece();
					if(tmpChesspiece!=null) {
						if(tmpChesspiece.isBlack()==isBlack()) {
							if(tmpChesspiece.getType().equals("Rook")) {
								Rook tmpRook = (Rook) tmpChesspiece;
								// Check if Rook wasn't moved before
								if(tmpRook.isUntouched()) {
									// Check for a free route between King and Rook
									for(int i=startingFieldID-1; i>startingFieldID-4; i--) {
										if(endingField.getBoard().getFieldByID(i).isOccupied()) {
											isUsingLongRochade = false;
											break;
										} else {
											isUsingLongRochade = true;
											validMovement.add(-2);
										}
									}
								}
							}
						}
					}
				} else if(endingFieldID==startingFieldID+2) {
					// Check if Rook is on starting field
					Chesspiece tmpChesspiece = endingField.getBoard().getFieldByID(startingFieldID+3).getChesspiece();
					if(tmpChesspiece!=null) {
						if(tmpChesspiece.isBlack()==isBlack()) {
							if(tmpChesspiece.getType().equals("Rook")) {
								Rook tmpRook = (Rook) tmpChesspiece;
								// Check if Rook wasn't moved before
								if(tmpRook.isUntouched()) {
									// Check for a free route between King and Rook
									for(int i=startingFieldID+1; i<startingFieldID+3; i++) {
										if(endingField.getBoard().getFieldByID(i).isOccupied()) {
											isUsingShortRochade = false;
											break;
										} else {
											isUsingShortRochade = true;
											validMovement.add(2);
										}
									}
								}
							}
						}
					}
				}
			}
			// Normal movement
			validMovement.add(-9);
			validMovement.add(-8);
			validMovement.add(-7);
			validMovement.add(-1);
			validMovement.add(1);
			validMovement.add(7);
			validMovement.add(8);
			validMovement.add(9);
		}
		// Check for a match
		for(int i=0; i<validMovement.size(); i++) {
			// Check for own color (Invalid movement)
			if(endingField.isOccupied()) {
				if(endingField.getChesspiece().isBlack()==isBlack()) {
					return false;
				} else {
					// Field is occupied by opponent color
					if((validMovement.get(i)+startingFieldID)==endingFieldID) {
						isUntouched = false;
						return true;
					}
				}
			} else {
				// Field is not occupied
				if((validMovement.get(i)+startingFieldID)==endingFieldID) {
					isUntouched = false;
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isUntouched() {
		return isUntouched;
	}
	
	public boolean isUsingShortRochade() {
		return isUsingShortRochade;
	}
	
	public void setIsUsingShortRochade(boolean isUsingShortRochade) {
		this.isUsingShortRochade = isUsingShortRochade;
	}
	
	public boolean isUsingLongRochade() {
		return isUsingLongRochade;
	}
	
	public void setIsUsingLongRochade(boolean isUsingLongRochade) {
		this.isUsingLongRochade = isUsingLongRochade;
	}
	
	public boolean isInCheck() {
		return isInCheck;
	}
	
	public boolean isCheckmate() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
