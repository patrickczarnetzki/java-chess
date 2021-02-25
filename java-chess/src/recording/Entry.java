package recording;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;

import pieces.Chesspiece;

public class Entry extends JLabel {
	private int id;
	private String entry;
	
	public Entry(int id, String entry) {
		this.id = id;
		this.entry = entry;
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setForeground(Color.WHITE);
		setFont(new Font("SansSerif",Font.BOLD,14));
		setOpaque(true);
		if(id%2==0) {
			setBackground(Color.decode("#657180"));
		} else {
			setBackground(Color.decode("#89929D"));
		}
		setText(entry);
	}
	
	public int getID() {
		return id;
	}
	
	public String getEntry() {
		return entry;
	}
	
	public void setEntry(String entry) {
		this.entry = entry;
		setText(entry);
	}
}