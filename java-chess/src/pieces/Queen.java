package pieces;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Queen extends Chesspiece {

	public Queen(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
		// TODO Auto-generated method stub
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// TODO Auto-generated method stub
		return true;
	}

}
