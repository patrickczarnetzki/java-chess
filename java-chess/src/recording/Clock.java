package recording;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import player.Player;

public class Clock extends JPanel {
	private Player player;
	private int minutes;
	private int seconds;
	private JLabel time;
	private ClockThread clockThread;
	
	public Clock(Player player, int minutes, int seconds) {
		// Initialize variables
		this.player = player;
		this.minutes = minutes;
		this.seconds = seconds;
		time = new JLabel("");
		// Styling this panel
		setLayout(new BorderLayout());
		// Styling time text field
		time.setBackground(Color.decode("#3D454F"));
		time.setForeground(Color.WHITE);
		time.setBorder(BorderFactory.createEmptyBorder(10, 45, 10, 45));
		time.setHorizontalAlignment(JLabel.CENTER);
		time.setOpaque(true);
		time.setFont(new Font("Serif",Font.BOLD,16));
		// Set time for textfield
		time.setText(minutes + " : " + seconds);
		// Create small line as symbol for white or black clock
		JLabel lblColor = new JLabel("");
		// General styling for fieldColor
		lblColor.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		lblColor.setOpaque(true);
		lblColor.setHorizontalAlignment(JLabel.CENTER);
		// Set right background color to fieldColor
		if(!player.isBlack()) {
			lblColor.setBackground(Color.WHITE);
			lblColor.setForeground(Color.BLACK);
		} else {
			lblColor.setBackground(Color.BLACK);
			lblColor.setForeground(Color.WHITE);
		}
		// Add textfields to this panel
		add(lblColor,BorderLayout.NORTH);
		add(time,BorderLayout.CENTER);
	}
	
	private class ClockThread extends Thread {
		public void run() {
			// TODO Auto-generated method stub
		}
	}

}