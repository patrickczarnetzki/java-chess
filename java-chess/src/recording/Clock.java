package recording;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import gui.menu.Checkmate;
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
		// Initialize ClockThread
		clockThread = new ClockThread();
	}
	
	private class ClockThread extends Thread {
		public void clockLoop() throws InterruptedException {
			// While player is playing clock is running
			while(player.isPlaying() && !player.getBoard().isGamePaused() && player.getBoard().isGameStarted()) {
				// Check for timeover
				if(minutes==0 && seconds==0) {
					// Game Over
					// Pause Game
					player.getBoard().pause();
					// Pause Threads
					player.getBoard().getPlayerByColor(true).setIsPlaying(false);
					player.getBoard().getPlayerByColor(false).setIsPlaying(false);
					// Check which player won the game
					if(!player.isBlack()) {
						new Checkmate(false,player.getBoard());
					} else {
						new Checkmate(true,player.getBoard());
					}
				// Only seconds left
				} else if(minutes==0 && seconds>0) {
					seconds--;
					time.setText(minutes + " : " + seconds);
					sleep(1000);
				// Minutes and seconds left
				} else if(minutes!=0 && seconds>0) {
					seconds--;
					time.setText(minutes + " : " + seconds);
					sleep(1000);
				// One minute has passed
				} else if(minutes!=0 && seconds==0) {
					minutes--;
					seconds = 59;
					time.setText(minutes + " : " + seconds);
					sleep(1000);
				}
			}
			// Recursive call after 1 seconds to run the loop in a reasonable speed
			sleep(1000);
			clockLoop();
		}
		
		public void run() {
			try {
				clockLoop();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startClock() {
		clockThread.start();
	}
	
	public void setMinutes(int minutes) {
		this.minutes = minutes;
		time.setText(minutes + " : " + seconds);
	}
	
	public void setSeconds(int seconds) {
		this.seconds = seconds;
		time.setText(minutes + " : " + seconds);
	}
}