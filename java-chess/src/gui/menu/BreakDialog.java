package gui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import gui.Mainframe;

public class BreakDialog extends JFrame {
	
	private Mainframe mainframe;
	private JTextField fldTime;
	private JTextField fldMinutes;
	private JTextField fldSeconds;
	private int minutes;
	private int seconds;
	private boolean isBreakRunning;
	private boolean isBreakOver;
	private JButton btnSetMinutes;
	private JButton btnSetSeconds;
	private MenuButton btnAccept;
	private MenuButton btnDenie;
	private ClockThread clockThread;

	public BreakDialog(Mainframe mainframe) {
		// Pause the game
		mainframe.getBoard().pause();
		// Initialize variables
		this.mainframe = mainframe;
		minutes = 30;
		seconds = 0;
		isBreakRunning = false;
		isBreakOver = false;
		clockThread = new ClockThread();
		// Style, Behaviour, etc.
		setTitle("Break");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(350,300);
		setResizable(false);
		setLayout(new GridLayout(3,1));
		// Create clock panel to store the clock
		// Clock is at beginning also the entry field for time settings
		JPanel clockPanel = new JPanel(new GridLayout(1,1));
		clockPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		clockPanel.setBackground(Color.decode("#4B5869"));
		fldTime = new JTextField(minutes + " : " + seconds);
		fldTime.setFont(new Font("SansSerif",Font.BOLD,14));
		fldTime.setBorder(null);
		fldTime.setBackground(Color.decode("#3D454F"));
		fldTime.setForeground(Color.WHITE);
		fldTime.setHorizontalAlignment(JTextField.CENTER);
		clockPanel.add(fldTime);
		add(clockPanel);
		// Create menu panel to change minutes and seconds
		JPanel menuPanel = new JPanel(new GridLayout(2,1));
		menuPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		menuPanel.setBackground(Color.decode("#3D454F"));
		// Create first label / field combination (minutes)
		JPanel minutesPanel = new JPanel(new GridLayout(1,3));
		minutesPanel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		minutesPanel.setBackground(Color.decode("#3D454F"));
		JLabel lblMinutes = new JLabel("Minutes");
		lblMinutes.setForeground(Color.WHITE);
		fldMinutes = new JTextField("30");
		fldMinutes.setBorder(null);
		fldMinutes.setHorizontalAlignment(JTextField.CENTER);
		btnSetMinutes = new JButton("Set");
		btnSetMinutes.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		btnSetMinutes.addActionListener(new BreakButtonListener());
		minutesPanel.add(lblMinutes);
		minutesPanel.add(fldMinutes);
		minutesPanel.add(btnSetMinutes);
		// Create second label / field combination (seconds)
		JPanel secondsPanel = new JPanel(new GridLayout(1,3));
		secondsPanel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
		secondsPanel.setBackground(Color.decode("#3D454F"));
		JLabel lblSeconds = new JLabel("Seconds");
		lblSeconds.setForeground(Color.WHITE);
		fldSeconds = new JTextField("0");
		fldSeconds.setHorizontalAlignment(JTextField.CENTER);
		fldSeconds.setBorder(null);
		btnSetSeconds = new JButton("Set");
		btnSetSeconds.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
		btnSetSeconds.addActionListener(new BreakButtonListener());
		secondsPanel.add(lblSeconds);
		secondsPanel.add(fldSeconds);
		secondsPanel.add(btnSetSeconds);
		// Adding label / field combinations to menuPanel
		menuPanel.add(minutesPanel);
		menuPanel.add(secondsPanel);
		// Adding menuPanel to frame
		add(menuPanel);
		// Create button panel to store the buttons
		JPanel btnPanel = new JPanel(new GridLayout(2,1));
		btnAccept = new MenuButton("Accept");
		btnAccept.addActionListener(new BreakButtonListener());
		btnDenie = new MenuButton("Denie");
		btnDenie.addActionListener(new BreakButtonListener());
		btnPanel.add(btnAccept);
		btnPanel.add(btnDenie);
		add(btnPanel);
		// Start clock thread
		clockThread.start();
		// Set visible
		setVisible(true);
	}
	
	public boolean isBreakRunning() {
		return isBreakRunning;
	}
	
	public boolean isBreakOver() {
		return isBreakOver;
	}
	
	public void setIsBreakOver(boolean isBreakOver) {
		this.isBreakOver = isBreakOver;
	}
	
	public JTextField getFldMinutes() {
		return fldMinutes;
	}

	public JTextField getFldSeconds() {
		return fldSeconds;
	}
	
	public JTextField getFldTime() {
		return fldTime;
	}

	private class BreakButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnSetMinutes)) {
				if(!isBreakRunning()) {
					try {
						minutes = Integer.parseInt(getFldMinutes().getText());
						fldTime.setText(minutes + " : " + seconds);
					} catch(NumberFormatException exception) {
						System.out.println("Only numeric values allowed");
					}
				}
			} else if(event.getSource().equals(btnSetSeconds)) {
				if(!isBreakRunning()) {
					try {
						if(Integer.parseInt(getFldSeconds().getText())>=0 && Integer.parseInt(getFldSeconds().getText())<=59) {
							seconds = Integer.parseInt(getFldSeconds().getText());
							fldTime.setText(minutes + " : " + seconds);
						}
					} catch(NumberFormatException exception) {
						System.out.println("Only numeric values allowed");
					}
				}
			} else if(event.getSource().equals(btnAccept)) {
				// Start clock
				isBreakRunning = true;
				isBreakOver = false;
				btnDenie.setText("STOP THE COUNT!");
				mainframe.getBoard().setIsGameInBreak(true);
			} else if(event.getSource().equals(btnDenie)) {
				mainframe.getBoard().resume();
				isBreakRunning = false;
				isBreakOver = true;
				btnDenie.setText("Denie");
				dispose();
			}
		}
	}
	
	private class ClockThread extends Thread {
		public void clockLoop() throws InterruptedException {
			System.out.println("Clock Loop Started");
			while(isBreakRunning()) {
				// Check for timeover
				if(minutes==0 && seconds==0) {
					// Break Over
					// Resume Game
					mainframe.getBoard().resume();
					// Pause Thread
					isBreakRunning = false;
					mainframe.getBoard().setIsGameInBreak(false);
					isBreakOver = true;
					dispose();
				// Only seconds left
				} else if(minutes==0 && seconds>0) {
					seconds--;
					fldTime.setText(minutes + " : " + seconds);
					sleep(1000);
				// Minutes and seconds left
				} else if(minutes!=0 && seconds>0) {
					seconds--;
					fldTime.setText(minutes + " : " + seconds);
					sleep(1000);
				// One minute has passed
				} else if(minutes!=0 && seconds==0) {
					minutes--;
					seconds = 59;
					fldTime.setText(minutes + " : " + seconds);
					sleep(1000);
				}
			}
			// Check if break isn't completly over
			if(!isBreakOver()) {
				// Recursive call after 1 second to run the loop in a reasonable speed
				sleep(1000);
				clockLoop();
			}
		}
		
		public void run() {
			try {
				clockLoop();
				System.out.println("Leaving Thread");
				mainframe.getBoard().setIsGameInBreak(false);
			} catch(InterruptedException exception) {
				exception.printStackTrace();
			}
		}
	}
}