package pieces;

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
		// TODO Auto-generated method stub
		return true;
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
