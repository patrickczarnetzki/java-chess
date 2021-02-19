package pieces;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Knight extends Chesspiece {

	public Knight(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// TODO Auto-generated method stub
		return true;
	}

}
