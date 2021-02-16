package pieces;

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
		// TODO Auto-generated method stub
	}
	
	public boolean isEnPassant() {
		return isEnPassant;
	}
	
	public boolean isTransforming() {
		return isTransforming;
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// TODO Auto-generated method stub
		return true;
	}

}
