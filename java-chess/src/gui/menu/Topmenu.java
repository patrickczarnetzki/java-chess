package gui.menu;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.Expose;

import board.Field;
import gui.Mainframe;
import pieces.Bishop;
import pieces.Chesspiece;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;
import player.Player;
import recording.Entry;
import recording.LostPieces;

public class Topmenu extends JPanel {
	private Mainframe mainframe;
	private MenuButton btnNewGame;
	private MenuButton btnSaveGame;
	private MenuButton btnLoadGame;
	private MenuButton btnOptions;
	@Expose
	private LostPieces lostPieces;
	
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
		// Initialize lost pieces component to store lost chesspieces
		lostPieces = new LostPieces();
		// Add lost pieces component to topmenu
		add(lostPieces);
	}
	
	public LostPieces getLostPieces() {
		return lostPieces;
	}
	
	public void startNewGame() {
		mainframe.getBoard().newGame();
	}
	
	public void saveGame() throws IOException {
		// Pause the game
		mainframe.getBoard().pause();
		// Gson serialization
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
		String gsonString = gson.toJson(mainframe.getBoard().getFields());
		JsonArray jsonArray = (JsonArray) JsonParser.parseString(gsonString);
		// Store general game settings in json object
		JsonObject jsonGeneralSettings = new JsonObject();
		// Add round and turn to object
		jsonGeneralSettings.addProperty("round", Integer.toString(mainframe.getBoard().getRound()));
		jsonGeneralSettings.addProperty("turn", mainframe.getBoard().getTurn());
		// Add minutes and seconds of clocks to object
		jsonGeneralSettings.addProperty("minutesWhite", mainframe.getBoard().getPlayerByColor(false).getClock().getMinutes());
		jsonGeneralSettings.addProperty("secondsWhite", mainframe.getBoard().getPlayerByColor(false).getClock().getSeconds());
		jsonGeneralSettings.addProperty("minutesBlack", mainframe.getBoard().getPlayerByColor(true).getClock().getMinutes());
		jsonGeneralSettings.addProperty("secondsBlack", mainframe.getBoard().getPlayerByColor(true).getClock().getSeconds());
		// Store this object into a new json array
		JsonArray jsonGeneralSettingsArray = new JsonArray();
		jsonGeneralSettingsArray.add(jsonGeneralSettings);
		// Add json general settings array to json array
		jsonArray.add(jsonGeneralSettingsArray);
		// Save notation
		JsonArray jsonNotationArray = new JsonArray();
		for(int i=0; i<mainframe.getBoard().getNotation().getEntryList().size(); i++) {
			JsonObject jsonEntry = new JsonObject();
			jsonEntry.addProperty("id", mainframe.getBoard().getNotation().getEntryList().get(i).getID());
			jsonEntry.addProperty("entry", mainframe.getBoard().getNotation().getEntryList().get(i).getEntry());
			jsonNotationArray.add(jsonEntry);
		}
		// Add json notation array to json array
		jsonArray.add(jsonNotationArray);
		// Save lost pieces component values
		JsonArray jsonLostPiecesArray = new JsonArray();
		JsonObject jsonLostPiecesObject = new JsonObject();
		jsonLostPiecesObject.addProperty("lostPawnsWhite", getLostPieces().getLostPawnsWhite());
		jsonLostPiecesObject.addProperty("lostRooksWhite", getLostPieces().getLostRooksWhite());
		jsonLostPiecesObject.addProperty("lostKnightsWhite", getLostPieces().getLostKnightsWhite());
		jsonLostPiecesObject.addProperty("lostBishopsWhite", getLostPieces().getLostBishopsWhite());
		jsonLostPiecesObject.addProperty("lostQueensWhite", getLostPieces().getLostQueensWhite());
		jsonLostPiecesObject.addProperty("lostPawnsBlack", getLostPieces().getLostPawnsBlack());
		jsonLostPiecesObject.addProperty("lostRooksBlack", getLostPieces().getLostRooksBlack());
		jsonLostPiecesObject.addProperty("lostKnightsBlack", getLostPieces().getLostKnightsBlack());
		jsonLostPiecesObject.addProperty("lostBishopsBlack", getLostPieces().getLostBishopsBlack());
		jsonLostPiecesObject.addProperty("lostQueensBlack", getLostPieces().getLostQueensBlack());
		// Add json object to json lost pieces array
		jsonLostPiecesArray.add(jsonLostPiecesObject);
		// Add json lost pieces array to json array
		jsonArray.add(jsonLostPiecesArray);
		// Open save dialog
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		fileChooser.setDialogTitle("Save Game to...");
		fileChooser.setSelectedFile(new File("java-chess.json"));
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return null;
			}
			@Override
			public boolean accept(File file) {
				if(file.getName().endsWith(".json")) {
					return true;
				} else {
					return false;
				}
			}
		});
		// Check which option user choosed
		int userSelection = fileChooser.showSaveDialog(mainframe);
		if(userSelection==JFileChooser.APPROVE_OPTION) {
			// User choosed a place and clicked on save
			// Check for right file format (json)
			String filepath = fileChooser.getSelectedFile().toString();
			if(!filepath.endsWith(".json")) {
				// Add .json if file don't end with .json to get right file format
				filepath += ".json";
			}
			// Write the file
			Writer writer = new FileWriter(filepath);
			writer.write(jsonArray.toString());
			writer.close();
		}
		// Resume game
		mainframe.getBoard().resume();
	}
	
	public void loadGame() throws IOException {
		// Pause Game
		mainframe.getBoard().pause();
		// Open load dialog to load json file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setDialogTitle("Load Game");
		fileChooser.setSelectedFile(new File("java-chess.json"));
		fileChooser.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return null;
			}
			@Override
			public boolean accept(File file) {
				if(file.getName().endsWith(".json")) {
					return true;
				} else {
					return false;
				}
			}
		});
		// Check which option user choosed
		int userSelection = fileChooser.showOpenDialog(mainframe);
		if(userSelection==JFileChooser.APPROVE_OPTION) {
			// User choosed a file to load
			// Check for right file format (json)
			String filepath = fileChooser.getSelectedFile().toString();
			if(filepath.endsWith(".json")) {
				// Load Game
				// Start a new game first to get a clean setup
				mainframe.getBoard().newGame();
				// Delete all chesspieces
				for(int i=0; i<=63; i++) {
					mainframe.getBoard().getFieldByID(i).deleteChesspiece();
				}
				// Load save game (json)
				Gson gson = new Gson();
				// Reader
				BufferedReader reader = new BufferedReader(new FileReader(filepath));
				String bufferedString = reader.readLine();
				// This Json Array contains all rows and general settings
				JsonArray jsonArrayAll = (JsonArray) new JsonParser().parse(bufferedString).getAsJsonArray();
				// Load general settings
				JsonArray jsonArrayGeneralSettings = jsonArrayAll.get(10).getAsJsonArray();
				JsonObject jsonObjectGeneralSettings = jsonArrayGeneralSettings.get(0).getAsJsonObject();
				// Set Turn and Round
				mainframe.getBoard().setTurn(jsonObjectGeneralSettings.get("turn").getAsInt());
				mainframe.getBoard().setRound(jsonObjectGeneralSettings.get("round").getAsInt());
				// Set is playing
				if(mainframe.getBoard().getTurn()%2==0) {
					// White is playing
					mainframe.getBoard().getPlayerByColor(false).setIsPlaying(true);
					mainframe.getBoard().getPlayerByColor(true).setIsPlaying(false);
				} else {
					mainframe.getBoard().getPlayerByColor(false).setIsPlaying(false);
					mainframe.getBoard().getPlayerByColor(true).setIsPlaying(true);
				}
				// Restore clocks
				mainframe.getBoard().getPlayerByColor(false).getClock().setMinutes(jsonObjectGeneralSettings.get("minutesWhite").getAsInt());
				mainframe.getBoard().getPlayerByColor(false).getClock().setSeconds(jsonObjectGeneralSettings.get("secondsWhite").getAsInt());
				mainframe.getBoard().getPlayerByColor(true).getClock().setMinutes(jsonObjectGeneralSettings.get("minutesBlack").getAsInt());
				mainframe.getBoard().getPlayerByColor(true).getClock().setSeconds(jsonObjectGeneralSettings.get("secondsBlack").getAsInt());
				// Load notation
				JsonArray jsonArrayNotation = jsonArrayAll.get(11).getAsJsonArray();
				for(int i=0; i<jsonArrayNotation.size(); i++) {
					JsonObject jsonEntry = jsonArrayNotation.get(i).getAsJsonObject();
					mainframe.getBoard().getNotation().getEntryList().add(new Entry(jsonEntry.get("id").getAsInt(),jsonEntry.get("entry").getAsString()));
					mainframe.getBoard().getNotation().getListPanel().add(mainframe.getBoard().getNotation().getEntryList().get(i));
				}
				JsonObject jsonEntryIDIterator = jsonArrayNotation.get(jsonArrayNotation.size()-1).getAsJsonObject();
				mainframe.getBoard().getNotation().setEntryIDIterator(jsonEntryIDIterator.get("id").getAsInt());
				// Load lost pieces
				JsonArray jsonArrayLostPieces = jsonArrayAll.get(12).getAsJsonArray();
				JsonObject jsonLostPieces = jsonArrayLostPieces.get(0).getAsJsonObject();
				getLostPieces().setLostPawnsWhite(jsonLostPieces.get("lostPawnsWhite").getAsInt());
				getLostPieces().setLostRooksWhite(jsonLostPieces.get("lostRooksWhite").getAsInt());
				getLostPieces().setLostKnightsWhite(jsonLostPieces.get("lostKnightsWhite").getAsInt());
				getLostPieces().setLostBishopsWhite(jsonLostPieces.get("lostBishopsWhite").getAsInt());
				getLostPieces().setLostQueensWhite(jsonLostPieces.get("lostQueensWhite").getAsInt());
				getLostPieces().setLostPawnsBlack(jsonLostPieces.get("lostPawnsBlack").getAsInt());
				getLostPieces().setLostRooksBlack(jsonLostPieces.get("lostRooksBlack").getAsInt());
				getLostPieces().setLostKnightsBlack(jsonLostPieces.get("lostKnightsBlack").getAsInt());
				getLostPieces().setLostBishopsBlack(jsonLostPieces.get("lostBishopsBlack").getAsInt());
				getLostPieces().setLostQueensBlack(jsonLostPieces.get("lostQueensBlack").getAsInt());
				getLostPieces().refreshLostChesspieces();
				if(mainframe.getBoard().getTurn()%2==0) {
					mainframe.getBoard().getNotation().setEntryIDIterator(mainframe.getBoard().getNotation().getEntryList().get(mainframe.getBoard().getNotation().getEntryList().size()-1).getID()+1);
				} else {
					mainframe.getBoard().getNotation().setEntryIDIterator(mainframe.getBoard().getNotation().getEntryList().get(mainframe.getBoard().getNotation().getEntryList().size()-1).getID());
				}
				// Load chesspieces
				// Go through this array and get single rows which are also json arrays
				// Last row is for general settings (not important for this loop)
				for(int i=0; i<jsonArrayAll.size(); i++) {
					JsonArray jsonArrayRow = jsonArrayAll.get(i).getAsJsonArray();
					// Go through json row array
					for(int j=0; j<jsonArrayRow.size()-1; j++) {
						// Get json objects in this array and check if the id is in the right range (0 - 63)
						JsonObject jsonField = (JsonObject) jsonArrayRow.get(j);
						int jsonFieldID = Integer.parseInt(jsonField.get("id").toString());
						if(jsonFieldID>=0 && jsonFieldID<=63) {
							// Check if there was a chesspiece
							JsonObject jsonChesspiece = (JsonObject) jsonField.get("chesspiece");
							if(jsonChesspiece!=null) {
								// Check which kind of chesspiece
								boolean isBlack = jsonChesspiece.get("isBlack").getAsBoolean();
								String type = jsonChesspiece.get("type").getAsString();
								Field tempField = mainframe.getBoard().getFieldByID(jsonFieldID);
								Player tempPlayer = mainframe.getBoard().getPlayerByColor(isBlack);
								if(type.equals("Pawn")) {
									Pawn tempPawn = new Pawn(tempField,isBlack,tempPlayer,null,"Pawn");
									tempPawn.setIsUntouched(jsonChesspiece.get("isUntouched").getAsBoolean());
									tempPawn.setIsUsingEnPassant(jsonChesspiece.get("isUsingEnPassant").getAsBoolean());
									tempPawn.setIsEnPassant(jsonChesspiece.get("isEnPassant").getAsBoolean());
									tempField.setChesspiece(tempPawn);
								} else if(type.equals("Rook")) {
									Rook tempRook = new Rook(tempField,isBlack,tempPlayer,null,"Rook");
									tempRook.setIsUntouched(jsonChesspiece.get("isUntouched").getAsBoolean());
									tempField.setChesspiece(tempRook);
								} else if(type.equals("Knight")) {
									Knight tempKnight = new Knight(tempField,isBlack,tempPlayer,null,"Knight");
									tempField.setChesspiece(tempKnight);
								} else if(type.equals("Bishop")) {
									Bishop tempBishop = new Bishop(tempField,isBlack,tempPlayer,null,"Bishop");
									tempField.setChesspiece(tempBishop);
								} else if(type.equals("Queen")) {
									Queen tempQueen = new Queen(tempField,isBlack,tempPlayer,null,"Queen");
									tempField.setChesspiece(tempQueen);
								} else if(type.equals("King")) {
									King tempKing = new King(tempField,isBlack,tempPlayer,null,"King");
									tempKing.setIsUntouched(jsonChesspiece.get("isUntouched").getAsBoolean());
									tempKing.setIsUsingShortRochade(jsonChesspiece.get("isUsingShortRochade").getAsBoolean());
									tempKing.setIsUsingLongRochade(jsonChesspiece.get("isUsingLongRochade").getAsBoolean());
									tempKing.setIsInCheck(jsonChesspiece.get("isInCheck").getAsBoolean());
									tempField.setChesspiece(tempKing);
								}
							}
						}
					}
				}
				// Set game started
				mainframe.getBoard().setIsGameStarted(true);
			} else {
				System.out.println("Wrong datatype selected. Only .json supported!");
			}
		}
		// Resume game
		mainframe.getBoard().resume();
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
					try {
						saveGame();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else if(event.getSource().equals(btnLoadGame)) {
				// Check for running break
				if(!mainframe.getBoard().isGameInBreak()) {
					try {
						loadGame();
					} catch (IOException e) {
						e.printStackTrace();
					}
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
