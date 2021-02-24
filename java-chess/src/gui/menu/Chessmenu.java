package gui.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import gui.Mainframe;

public class Chessmenu extends JPanel {
	private Mainframe mainframe;
	private MenuButton btnResign;
	private MenuButton btnDraw;
	private MenuButton btnBreak;
	private MenuButton btnSaveNote;
	
	public Chessmenu(Mainframe mainframe) {
		// Initialize mainframe
		this.mainframe = mainframe;
		// Styling chessmenu
		setBackground(Color.decode("#3D454F"));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		// Initialize buttons
		btnResign = new MenuButton("Resign");
		btnDraw = new MenuButton("Draw");
		btnBreak = new MenuButton("Break");
		btnSaveNote = new MenuButton ("Save Notation");
		// Adding chessmenu listeners
		btnResign.addActionListener(new ChessmenuButtonListener());
		btnDraw.addActionListener(new ChessmenuButtonListener());
		btnBreak.addActionListener(new ChessmenuButtonListener());
		btnSaveNote.addActionListener(new ChessmenuButtonListener());
		// Adding buttons
		add(btnResign);
		add(btnDraw);
		add(btnBreak);
		add(btnSaveNote);
	}
	
	public void resign() {
		if(mainframe.getBoard().getPlayerByColor(false).isPlaying()) {
			new Checkmate(false,mainframe.getBoard());
		} else {
			new Checkmate(true,mainframe.getBoard());
		}
	}
	
	public void askForDraw() {
		// Pause the game, will be resumed in DrawDialog
		mainframe.getBoard().pause();
		new DrawDialog(mainframe);
	}
	
	public void askForBreak() {
		// TODO Auto-generated method stub
	}
	
	public void saveNotation() {
		// TODO Auto-generated method stub
	}
	
	private class ChessmenuButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnResign)) {
				resign();
			} else if(event.getSource().equals(btnDraw)) {
				askForDraw();
			} else if(event.getSource().equals(btnBreak)) {
				askForBreak();
			} else if(event.getSource().equals(btnSaveNote)) {
				saveNotation();
			}
		}
	}
}
