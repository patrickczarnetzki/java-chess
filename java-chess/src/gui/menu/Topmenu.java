package gui.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import gui.Mainframe;

public class Topmenu extends JPanel {
	private Mainframe mainframe;
	private MenuButton btnNewGame;
	private MenuButton btnSaveGame;
	private MenuButton btnLoadGame;
	private MenuButton btnOptions;
	
	public Topmenu(Mainframe mainframe) {
		// Initialize Mainframe
		this.mainframe = mainframe;
		// Styling
		setBackground(Color.decode("#3D454F"));
		// Buttons will be stored in flow layout
		setLayout(new FlowLayout(FlowLayout.LEFT));
		// Initialize buttons
		btnNewGame = new MenuButton("New Game");
		btnSaveGame = new MenuButton("Save Game");
		btnLoadGame = new MenuButton("Load Game");
		btnOptions = new MenuButton("Options");
		// Add action listener to buttons
		btnNewGame.addActionListener(new TopmenuButtonListener());
		btnSaveGame.addActionListener(new TopmenuButtonListener());
		btnLoadGame.addActionListener(new TopmenuButtonListener());
		btnOptions.addActionListener(new TopmenuButtonListener());
		// Add buttons to flow layout
		add(btnNewGame);
		add(btnSaveGame);
		add(btnLoadGame);
		add(btnOptions);
	}
	
	public void startNewGame() {
		mainframe.getBoard().newGame();
	}
	
	public void saveGame() {
		// TODO Auto-generated method stub
	}
	
	public void loadGame() {
		// TODO Auto-generated method stub
	}
	
	public void openOptions() {
		new Options(mainframe.getBoard());
	}
	
	private class TopmenuButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnNewGame)) {
				if(!mainframe.getBoard().isGameInBreak()) {
					startNewGame();
				}
			} else if(event.getSource().equals(btnSaveGame)) {
				if(!mainframe.getBoard().isGameInBreak()) {
					saveGame();
				}
			} else if(event.getSource().equals(btnLoadGame)) {
				// Check for running break
				if(!mainframe.getBoard().isGameInBreak()) {
					loadGame();
				}
			} else if(event.getSource().equals(btnOptions)) {
				// Options can only changed for not started games
				// You have to start a new game to change settings
				if(!mainframe.getBoard().isGameStarted() && !mainframe.getBoard().isGameInBreak()) {
					openOptions();
				} else {
					System.out.println("Options cannot changed while game is started");
				}
			}
		}
	}
}
