package pieces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Bishop extends Chesspiece {

	public Bishop(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
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
		// Check movement direction
		if(startingColumn<endingColumn && startingRow>endingRow) {
			// Bishop goes to the upper right diagonal
			// Check for a free route to ending field
			for(int i=startingFieldID-7; i>=endingFieldID; i=i-7) {
				// Check if field is occupied
				if(endingField.getBoard().getFieldByID(i).isOccupied()) {
					// Check if field is occupied by own color (= no free route)
					if(endingField.getBoard().getFieldByID(i).getChesspiece().isBlack()==isBlack()) {
						break;
					// Field is occupied by opponent color and it is not the ending field
					} else if(i!=endingFieldID) {
						break;
					// Field is occupied by opponent color and it is the ending field
					} else {
						validMovement.add(i);
						setHasBeatenEnemy(true);
						break;
					}
				} else {
					// If that field is the ending field store it in ArrayList
					if(i==endingFieldID) {
						validMovement.add(i);
						break;
					}
				}
			}
		}
		if(startingColumn>endingColumn && startingRow>endingRow) {
			// Bishop goes to the upper left diagonal
			// Check for a free route to ending field
			for(int i=startingFieldID-9; i>=endingFieldID; i=i-9) {
				// Check if field is occupied
				if(endingField.getBoard().getFieldByID(i).isOccupied()) {
					// Check if field is occupied by own color (= no free route)
					if(endingField.getBoard().getFieldByID(i).getChesspiece().isBlack()==isBlack()) {
						break;
					// Field is occupied by opponent color and it is not the ending field
					} else if(i!=endingFieldID) {
						break;
					// Field is occupied by opponent color and it is the ending field
					} else {
						validMovement.add(i);
						setHasBeatenEnemy(true);
						break;
					}
				} else {
					// If that field is the ending field store it in ArrayList
					if(i==endingFieldID) {
						validMovement.add(i);
						break;
					}
				}
			}
		}
		if(startingColumn<endingColumn && startingRow<endingRow) {
			// Bishop goes to the lower right diagonal
			// Check for a free route to ending field
			for(int i=startingFieldID+9; i<=endingFieldID; i=i+9) {
				// Check if field is occupied
				if(endingField.getBoard().getFieldByID(i).isOccupied()) {
					// Check if field is occupied by own color (= no free route)
					if(endingField.getBoard().getFieldByID(i).getChesspiece().isBlack()==isBlack()) {
						break;
					// Field is occupied by opponent color and it is not the ending field
					} else if(i!=endingFieldID) {
						break;
					// Field is occupied by opponent and it is the ending field
					} else {
						validMovement.add(i);
						setHasBeatenEnemy(true);
						break;
					}
				} else {
					// If that field is the ending field store it in ArrayList
					if(i==endingFieldID) {
						validMovement.add(i);
						break;
					}
				}
			}
		}
		if(startingColumn>endingColumn && startingRow<endingRow) {
			// Bishop goes to the lower left diagonal
			// Check for a free route to ending field
			for(int i=startingFieldID+7; i<=endingFieldID; i=i+7) {
				// Check if field is occupied
				if(endingField.getBoard().getFieldByID(i).isOccupied()) {
					// Check if field is occupied by own color (= no free route)
					if(endingField.getBoard().getFieldByID(i).getChesspiece().isBlack()==isBlack()) {
						break;
					// Field is occupied by opponent color and it is not the ending field
					} else if(i!=endingFieldID) {
						break;
					// Field is occupied by opponent color and it is the ending field
					} else {
						validMovement.add(i);
						setHasBeatenEnemy(true);
						break;
					}
				} else {
					// If that field is the ending field store it in ArrayList
					if(i==endingFieldID) {
						validMovement.add(i);
						break;
					}
				}
			}
		}
		// Check for valid movement
		for(int i=0; i<validMovement.size(); i++) {
			if(validMovement.get(i)==endingFieldID) {
				return true;
			}
		}
		return false;
	}
}
