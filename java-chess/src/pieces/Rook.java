package pieces;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Rook extends Chesspiece {
	private boolean isUntouched;
	
	public Rook(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
		isUntouched = true;
	}
	
	public boolean isUntouched() {
		return isUntouched;
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// TODO Auto-generated method stub
		return true;
	}

}
