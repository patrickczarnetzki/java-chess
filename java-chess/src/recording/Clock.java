package recording;

import javax.swing.JPanel;
import javax.swing.JTextField;

import player.Player;

public class Clock extends JPanel {
	private Player player;
	private int minutes;
	private int seconds;
	private JTextField time;
	private ClockThread clockThread;
	
	public Clock(Player player, int minutes, int seconds) {
		this.player = player;
		this.minutes = minutes;
		this.seconds = seconds;
		// TODO Auto-generated method stub
	}
	
	private class ClockThread extends Thread {
		public void run() {
			// TODO Auto-generated method stub
		}
	}

}
