package pieces;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public class Bishop extends Chesspiece {

	public Bishop(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
