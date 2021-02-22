package pieces;

import javax.swing.ImageIcon;

import board.Field;
import player.Player;

public abstract class Chesspiece {
	private Field field;
	private boolean isBlack;
	private Player player;
	private ImageIcon icon;
	private String type;
	
	public Chesspiece(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		this.field = field;
		this.isBlack = isBlack;
		this.player = player;
		this.icon = icon;
		this.type = type;
	}
	
	public Field getField() {
		return field;
	}
	
	public void setField(Field field) {
		this.field = field;
	}
	
	public boolean isBlack() {
		return isBlack;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public ImageIcon getIcon() {
		return icon;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean isValidMovement(Field endingField) {
		return true;
	}
}
