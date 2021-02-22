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
						return true;
					}
				}
			} else {
				// Field is not occupied
				if((validMovement.get(i)+startingFieldID)==endingFieldID) {
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
	
	public boolean isUsingLongRochade() {
		return isUsingLongRochade;
	}
	
	public boolean isInCheck() {
		return isInCheck;
	}
	
	public boolean isCheckmate() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
