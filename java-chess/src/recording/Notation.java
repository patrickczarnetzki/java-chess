package recording;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import board.Board;
import pieces.Chesspiece;

// Good colors for entries: 657180 / 89929D

public class Notation extends JPanel {
	private Board board;
	private List<Entry> entryList;
	private int entryIDIterator;
	private JPanel listPanel;
	private JScrollPane listScrollPane;
	
	public Notation(Board board) {
		// Initialize components and variables
		this.board = board;
		entryIDIterator = 1;
		entryList = new ArrayList<Entry>();
		// Setup this JPanel
		setLayout(new BorderLayout());
		setBorder(null);
		// Adding a headline
		JLabel lblHeadline = new JLabel("NOTATION");
		Font fntHeadline = new Font("SansSerif",Font.BOLD,20);
		lblHeadline.setOpaque(true);
		lblHeadline.setForeground(Color.WHITE);
		lblHeadline.setBackground(Color.decode("#3D454F"));
		lblHeadline.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
		lblHeadline.setFont(fntHeadline);
		lblHeadline.setHorizontalAlignment(JLabel.LEFT);
		add(lblHeadline,BorderLayout.NORTH);
		// Initialize JPanel to store single entries. Will be used as list in JScrollPane
		listPanel = new JPanel();
		// Styling of listPanel
		listPanel.setLayout(new GridLayout(0,1));
		listPanel.setBackground(Color.decode("#657180"));
		listPanel.setBorder(null);
		// Create a JScrollPane
		listScrollPane = new JScrollPane(listPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// Styling JScrollPane
		listScrollPane.setBorder(null);
		listScrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
			@Override
			protected void configureScrollBarColors() {
				this.thumbColor = Color.decode("#93827F");
				this.trackColor = Color.decode("#DDE2C6");
				this.thumbDarkShadowColor = Color.WHITE;
				this.thumbHighlightColor = Color.WHITE;
				this.trackHighlightColor = Color.BLACK;
			}
		});
		// Add JScrollPane to this JPanel
		add(listScrollPane,BorderLayout.CENTER);
	}
	
	public void addEntry(int endingFieldID, Chesspiece chesspiece) {
		// Get ending Column
		int endingColumn = board.getFieldByID(endingFieldID).getColumn();
		// Get ending Row
		int endingRow = board.getFieldByID(endingFieldID).getRow();
		// Create chess coordinate strings
		// Check if it is the beginning of the entry string
		if(!chesspiece.isBlack()) {
			// It is a note for white so it is the beginning of the entry string
			String whiteEntry = entryIDIterator + ".     " + getChessCoordinates(endingColumn,endingRow,chesspiece);
			entryList.add(new Entry(entryIDIterator, whiteEntry));
			listPanel.add(entryList.get(entryList.size()-1));
		} else {
			// It is a note for black so we concatenate the new entry with the last entry in the entryList
			entryList.get(entryList.size()-1).setEntry(entryList.get(entryList.size()-1).getEntry() + "     " + getChessCoordinates(endingColumn,endingRow,chesspiece));
			entryIDIterator++;
		}
	}
	
	public void setEntryList(List<Entry> entryList) {
		this.entryList = entryList;
	}
	
	public void clearEntryList() {
		entryIDIterator = 1;
		entryList.clear();
		listPanel.removeAll();
		listPanel.repaint();
	}

	public String getChessCoordinates(int column, int row, Chesspiece chesspiece) {
		String convertedColumn = "";
		String convertedRow = "";
		String convertedChesspiece = "";
		// Convert columns from numbers to letters
		switch(column) {
		case 0: convertedColumn = "a"; break;
		case 1: convertedColumn = "b"; break;
		case 2: convertedColumn = "c"; break;
		case 3: convertedColumn = "d"; break;
		case 4: convertedColumn = "e"; break;
		case 5: convertedColumn = "f"; break;
		case 6: convertedColumn = "g"; break;
		case 7: convertedColumn = "h"; break;
		}
		// Convert rows into right numbers
		switch(row) {
		case 0: convertedRow = "8"; break;
		case 1: convertedRow = "7"; break;
		case 2: convertedRow = "6"; break;
		case 3: convertedRow = "5"; break;
		case 4: convertedRow = "4"; break;
		case 5: convertedRow = "3"; break;
		case 6: convertedRow = "2"; break;
		case 7: convertedRow = "1"; break;
		}
		// Convert chesspieces into letters
		switch(chesspiece.getType()) {
		case "Pawn": convertedChesspiece = ""; break;
		case "Rook": convertedChesspiece = "R"; break;
		case "Knight": convertedChesspiece = "N"; break;
		case "Bishop": convertedChesspiece = "B"; break;
		case "Queen": convertedChesspiece = "Q"; break;
		case "King": convertedChesspiece = "K"; break;
		}
		// Create chess coordinate String
		String chessCoordinates = convertedChesspiece + convertedColumn + convertedRow;
		return chessCoordinates;
	}
	
	public List<Entry> getEntryList() {
		return entryList;
	}
	
	public void setEntryIDIterator(int entryIDIterator) {
		this.entryIDIterator = entryIDIterator;
	}
	
	public JPanel getListPanel() {
		return listPanel;
	}
	
	public int getEntryIDIterator() {
		return entryIDIterator;
	}
}