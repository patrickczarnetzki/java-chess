package gui.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import board.Board;

public class Checkmate extends JFrame {
	
	private boolean isWinnerWhite;
	private MenuButton btnNewGame;
	private MenuButton btnLoadGame;
	private MenuButton btnSaveNotation;
	private MenuButton btnClose;
	private CheckmateButtonListener btnListener;
	private Board board;
	
	public Checkmate(boolean isWinnerWhite, Board board) {
		// Initialize variables
		this.board = board;
		this.isWinnerWhite = isWinnerWhite;
		this.btnNewGame = new MenuButton("New Game");
		this.btnLoadGame = new MenuButton("Load Game");
		this.btnSaveNotation = new MenuButton("Save Notation");
		this.btnClose = new MenuButton("Close Game");
		this.btnListener = new CheckmateButtonListener();
		// Frame Size, Styling, etc.
		setTitle("Game Over");
		setSize(350,250);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLayout(new BorderLayout());
		setBackground(Color.decode("#3D454F"));
		// Create JPanel to store buttons
		JPanel btnPanel = new JPanel(new GridLayout(2,2));
		btnPanel.setBackground(Color.decode("#3D454F"));
		// Add listener to buttons
		btnNewGame.addActionListener(btnListener);
		btnLoadGame.addActionListener(btnListener);
		btnSaveNotation.addActionListener(btnListener);
		btnClose.addActionListener(btnListener);
		// Create a headline
		JLabel lblCheckmate;
		// Create JPanel to store headline
		JPanel lblPanel = new JPanel(new GridLayout(1,1));
		if(isWinnerWhite) {
			lblCheckmate = new JLabel("THE WINNER IS WHITE");
			lblPanel.setBackground(Color.WHITE);
			lblCheckmate.setForeground(Color.BLACK);
		} else {
			lblCheckmate = new JLabel("THE WINNER IS BLACK");
			lblPanel.setBackground(Color.BLACK);
			lblCheckmate.setForeground(Color.WHITE);
		}
		lblCheckmate.setFont(new Font("SansSerif",Font.BOLD,20));
		lblCheckmate.setHorizontalAlignment(JLabel.CENTER);
		lblPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		// Add buttons to btnPanel
		btnPanel.add(btnNewGame);
		btnPanel.add(btnLoadGame);
		btnPanel.add(btnSaveNotation);
		btnPanel.add(btnClose);
		// Add headline to lblPanel
		lblPanel.add(lblCheckmate);
		// Add Panels to JFrame
		add(lblPanel,BorderLayout.NORTH);
		add(btnPanel,BorderLayout.CENTER);
		// Set Frame visible
		setVisible(true);
	}
	
	private class CheckmateButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnNewGame)) {
				board.newGame();
				dispose();
			} else if(event.getSource().equals(btnLoadGame)) {
				System.out.println("Load Game clicked");
				dispose();
			} else if(event.getSource().equals(btnSaveNotation)) {
				System.out.println("Save Notation clicked");
				dispose();
			} else if(event.getSource().equals(btnClose)) {
				System.out.println("Close Game clicked");
				dispose();
			}
		}
		
	}
	
}
