package gui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import board.Board;

public class Options extends JFrame {
	
	private Board board;
	private JButton btnAccept;
	private JButton btnDenie;
	private JTextField fldMinutes;
	private JTextField fldSeconds;
	private JTextField fldFirstColor;
	private JTextField fldSecondColor;
	private OptionsButtonListener btnListener;

	public Options(Board board) {
		// Pause the game, will be resumed by clicking accept or denie button
		board.pause();
		// Initialize Components
		this.board = board;
		btnListener = new OptionsButtonListener();
		//Style, Behaviour, etc.
		setTitle("Options");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(400,400);
		setResizable(false);
		setLayout(new BorderLayout());
		// Create options panel to store components
		JPanel optionsPanel = new JPanel(new GridLayout(4,1));
		optionsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		// Create clock options section
		JPanel clockPanel = new JPanel(new GridLayout(3,1));
		JLabel lblClockPanel = new JLabel("Clock Options");
		// Create first label / field combination for time settings (minutes)
		JPanel minutePanel = new JPanel(new GridLayout(1,1));
		JLabel lblMinutes = new JLabel("Minutes");
		fldMinutes = new JTextField();
		fldMinutes.setText(Integer.toString(board.getMinutes()));
		minutePanel.add(lblMinutes);
		minutePanel.add(fldMinutes);
		// Create seconds label / field combination for time settings (seconds)
		JPanel secondPanel = new JPanel(new GridLayout(1,1));
		JLabel lblSeconds = new JLabel("Seconds");
		fldSeconds = new JTextField();
		fldSeconds.setText(Integer.toString(board.getSeconds()));
		secondPanel.add(lblSeconds);
		secondPanel.add(fldSeconds);
		// Add clock label, minutes and seconds panel to clock panel
		clockPanel.add(lblClockPanel);
		clockPanel.add(minutePanel);
		clockPanel.add(secondPanel);
		// Create board options section
		JPanel boardPanel = new JPanel(new GridLayout(3,1));
		JLabel lblBoardPanel = new JLabel("Board Options");
		// Create first label / field combination for field color
		JPanel fieldFirstColorPanel = new JPanel(new GridLayout(1,1));
		JLabel lblFirstColor = new JLabel("Field Color One (1):");
		fldFirstColor = new JTextField();
		fldFirstColor.setText(board.getFieldColorOne());
		fieldFirstColorPanel.add(lblFirstColor);
		fieldFirstColorPanel.add(fldFirstColor);
		// Create second label / field combination for field color two
		JPanel fieldSecondColorPanel = new JPanel(new GridLayout(1,1));
		JLabel lblSecondColor = new JLabel("Field Color Two (2):");
		fldSecondColor = new JTextField();
		fldSecondColor.setText(board.getFieldColorTwo());
		fieldSecondColorPanel.add(lblSecondColor);
		fieldSecondColorPanel.add(fldSecondColor);
		// Add board label, first and second color panel to board panel
		boardPanel.add(lblBoardPanel);
		boardPanel.add(fieldFirstColorPanel);
		boardPanel.add(fieldSecondColorPanel);
		// Create button section
		JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnAccept = new JButton("Accept");
		btnDenie = new JButton("Denie");
		btnPanel.add(btnAccept);
		btnPanel.add(btnDenie);
		// Add listener to buttons
		btnAccept.addActionListener(btnListener);
		btnDenie.addActionListener(btnListener);
		// Add clock, board and button panel to options panel
		optionsPanel.add(boardPanel);
		optionsPanel.add(clockPanel);
		optionsPanel.add(btnPanel);
		// Add options panel to jframe
		add(optionsPanel,BorderLayout.CENTER);
		// Set visible
		setVisible(true);
	}
	
	private class OptionsButtonListener implements ActionListener {
		boolean isColorChanged = false;
		boolean isTimeChanged = false;
		@Override
		public void actionPerformed(ActionEvent e) {
			// Accept clicked
			if(e.getSource().equals(btnAccept)) {
				// Check if minutes changed
				try {
					if(!fldMinutes.getText().equals(Integer.toString(board.getMinutes()))) {
						// Minutes changed
						board.setMinutes(Integer.parseInt(fldMinutes.getText()));
						isTimeChanged = true;
					}
				} catch(NumberFormatException event) {
					System.out.println("Only numeric values allowed");
				}
				if(!fldSeconds.getText().equals(Integer.toString(board.getSeconds()))) {
					// Seconds changed
					// Check if new value is in the right range
					try {
						if(Integer.parseInt(fldSeconds.getText())>=0 && Integer.parseInt(fldSeconds.getText())<=59) {
							board.setSeconds(Integer.parseInt(fldSeconds.getText()));
							isTimeChanged = true;
						} else {
							System.out.println("No valid range");
						}
						// If users dont type a numeric value
					} catch(NumberFormatException event) {
						System.out.println("Only numeric values allowed");
					}
				}
				// Check if first color changed
				if(!fldFirstColor.getText().equals(board.getFieldColorOne())) {
					// Check for valid color code
					if(isValidColorCode(fldFirstColor.getText())) {
						// First color changed
						board.setFirstColor(fldFirstColor.getText());
						System.out.println(board.getFieldColorOne());
						isColorChanged = true;
					}
				}
				if(!fldSecondColor.getText().equals(board.getFieldColorTwo())) {
					// Check for valid color code
					if(isValidColorCode(fldSecondColor.getText())) {
						// Second color changed
						board.setSecondColor(fldSecondColor.getText());
						isColorChanged = true;
					}
				}
				if(isColorChanged) {
					board.setFieldColor(board.getFieldColorOne(), board.getFieldColorTwo());
					isColorChanged = false;
				}
				if(isTimeChanged) {
					board.setTime(Integer.parseInt(fldMinutes.getText()), Integer.parseInt(fldSeconds.getText()));
					isTimeChanged = false;
				}
				board.resume();
				dispose();
			} else if(e.getSource().equals(btnDenie)) {
				board.resume();
				dispose();
			}
		}
		
	}
	
	public JTextField getFldFirstColor() {
		return fldFirstColor;
	}
	
	public JTextField getFldSecondColor() {
		return fldSecondColor;
	}
	
	public JTextField getFldMinutes() {
		return fldMinutes;
	}
	
	public JTextField getFldSeconds() {
		return fldSeconds;
	}
	
	public boolean isValidColorCode(String colorCode) {
		boolean isValidCharacter = false;
		// Check length (Min-size = 2 (#X), Max-size = 7 (#XXXXXX)
		if(colorCode.length()>=2 && colorCode.length()<=7) {
			// Right length
			// Check if string starts with #
			if(colorCode.substring(0, 1).equals("#")) {
				// String starts with #
				// Go through the rest of the string and check for valid characters (0-9, A-Z, a-z)
				// Set colorCode to lower case so we don't need to check A-Z separately
				colorCode = colorCode.toLowerCase();
				// Delete first # Character
				colorCode = colorCode.substring(1,colorCode.length()-1);
				// Store string into array without #
				char[] stringArray = colorCode.toCharArray();
				// Create valid letters and numbers
				char[] validCharacters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','0','1','2','3','4','5','6','7','8','9'};
				for(int i=0; i<stringArray.length; i++) {
					isValidCharacter = false;
					for(int j=0; j<validCharacters.length; j++) {
						if(stringArray[i]==validCharacters[j]) {
							isValidCharacter = true;
							break;
						}
					}
					// Check if we found a valid character, otherwise it is not a valid color code
					if(!isValidCharacter) {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
}
