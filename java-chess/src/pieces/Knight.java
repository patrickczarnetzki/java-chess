package pieces;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import board.Field;
import player.Player;

public class Knight extends Chesspiece {

	public Knight(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// Store ID of starting and ending field
		int startingFieldID = getField().getID();
		int endingFieldID = endingField.getID();
		// Store starting column for exception handling on column 0 and 7
		int startingColumn = getField().getColumn();
		// Generate a list to store possible valid moves
		List<Integer> validMovement = new ArrayList<Integer>();
		// Movement and attacking is following the same logic
		// Check if ending field is occupied
		if(endingField.isOccupied()) {
			// Check for own color on ending field (Invalid movement)
			if(endingField.getChesspiece().isBlack()==isBlack()) {
				return false;
			}
		}
		// Check for exception on column 0
		if(startingColumn==0) {
			// Add valid movement
			validMovement.add(-15);
			validMovement.add(-6);
			validMovement.add(10);
			validMovement.add(17);
		// Check for exception on column 7
		} else if(startingColumn==7) {
			validMovement.add(-17);
			validMovement.add(-10);
			validMovement.add(6);
			validMovement.add(15);
		// Normal movement and attacking without exception handling
		} else {
			validMovement.add(-17);
			validMovement.add(-15);
			validMovement.add(-10);
			validMovement.add(-6);
			validMovement.add(6);
			validMovement.add(10);
			validMovement.add(15);
			validMovement.add(17);
		}
		// Check for a match
		for(int i=0; i<validMovement.size(); i++) {
			if((startingFieldID+validMovement.get(i))==endingFieldID) {
				// Important to check if beating sound should played
				if(endingField.isOccupied()) {
					setHasBeatenEnemy(true);
				} else {
					setHasBeatenEnemy(false);
				}
				return true;
			}
		}
		// If no match was found return false
		return false;
	}
}
