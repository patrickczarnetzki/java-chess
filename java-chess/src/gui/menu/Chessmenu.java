package gui.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import gui.Mainframe;
import recording.Entry;

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
		// Pause the game
		mainframe.getBoard().pause();
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
	
	public void saveNotation() throws IOException {
		// Pause the game
		mainframe.getBoard().pause();
		// Get entry list
		List<Entry> entryList = mainframe.getBoard().getNotation().getEntryList();
		// Using Gson to create a Json file
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		JsonArray entryArray = new JsonArray();
		// Go through entryList and store Entries in JsonArray
		for(int i=0; i<entryList.size(); i++) {
			entryArray.add(gson.toJson(entryList.get(i)));
		}
		// Open save dialog
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save Notation to...");
		// Check which option user choosed
		int userSelection = fileChooser.showSaveDialog(mainframe);
		if(userSelection==JFileChooser.APPROVE_OPTION) {
			// User choosed a place and clicked on save
			Writer writer = new FileWriter(fileChooser.getSelectedFile());
			writer.write(entryArray.toString());
			writer.close();
		}
		// Resume the game
		mainframe.getBoard().resume();
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
				try {
					saveNotation();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}