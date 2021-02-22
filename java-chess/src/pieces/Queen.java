package pieces;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Queen extends Chesspiece {

	public Queen(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
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
		// Check for vertical movement
		if(startingColumn==endingColumn) {
			// Up or down?
			if(startingRow>endingRow) {
				// Going up
				// Check for a free route to ending field
				for(int i=startingFieldID-8; i>=endingFieldID; i=i-8) {
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
			} else if(startingRow<endingRow) {
				// Going down
				// Check for a free route to the ending field
				for(int i=startingFieldID+8; i<=endingFieldID; i=i+8) {
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
			} else {
				// No valid movement
			}
		// Check for horizontal movement
		} else if(startingRow==endingRow) {
			// Going left or right?
			if(startingColumn>endingColumn) {
				// Going left
				// Check for a free route to the ending field
				for(int i=startingFieldID-1; i>=endingFieldID; i--) {
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
			} else if(startingColumn<endingColumn) {
				// Going right
				// Check for a free route to the ending field
				for(int i=startingFieldID+1; i<=endingFieldID; i++) {
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
			} else {
				// No valid movement
			}
		} else {
			// No vertical nor horizontal movement (Invalid movement)
		}
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
		// Check for a match (Valid movement)
		for(int i=0; i<validMovement.size(); i++) {
			if(validMovement.get(i)==endingFieldID) {
				return true;
			}
		}
		return false;
	}
}