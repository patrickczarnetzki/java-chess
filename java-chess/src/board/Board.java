package board;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import pieces.King;
import player.Player;
import recording.Notation;

public class Board extends JPanel {
	private Player[] players;
	private Notation notation;
	private Field[][] fields;
	private FieldListener fieldListener;
	private boolean isGameStarted;
	private boolean isGamePaused;
	private boolean isStartingMovement;
	private int turn;
	private int round;
	
	public Board() {
		createFields(fields);
		setupChesspieces(fields);
		// Initialize players with clocks
		players = new Player[2];
		players[0] = new Player(false,false,this);
		players[1] = new Player(false,true,this);
		// Initialize notation
		notation = new Notation(this);
		// Set background to grey-blue color, add border to get some space between the end of this panel
		setBackground(Color.decode("#4B5869"));
		setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
	}
	
	public void createFields(Field[][] fields) {
		// Initialize field array, 8x8 fields for the playing fields, rest for the borders
		fields = new Field[10][10];
		// Add GridBagLayout to this panel
		setLayout(new GridBagLayout());
		// Create a GridBagConstraint to change properties of each field
		GridBagConstraints constraintsFields = new GridBagConstraints();
		// Create iterators for ids and color switching
		int iteratorID = 0;
		int iteratorColor = 0;
		// r=row (horizontal) c=column (vertical)
		for(int r=0; r<fields.length; r++) {
			for(int c=0; c<fields[r].length; c++) {
				// Find fields which represent left and right ending of the board
				if(r==0 || r==9) {
					// Initialize left and right border fields
					fields[r][c] = new Field(this,true,null,r-1,c-1,100,true);
					fields[r][c].setBackground(Color.decode("#3D454F"));
					fields[r][c].setForeground(Color.WHITE);
					fields[r][c].setPreferredSize(new Dimension(45,70));
					// Set chess coordinates
					switch(c) {
					case 1: fields[r][c].setText("8"); break;
					case 2: fields[r][c].setText("7"); break;
					case 3: fields[r][c].setText("6"); break;
					case 4: fields[r][c].setText("5"); break;
					case 5: fields[r][c].setText("4"); break;
					case 6: fields[r][c].setText("3"); break;
					case 7: fields[r][c].setText("2"); break;
					case 8: fields[r][c].setText("1"); break;
					}
					// Check for corners
					if(c==0 || c==9) {
						fields[r][c].setPreferredSize(new Dimension(45,45));
					}
					// Set constraints
					constraintsFields.gridx = r;
					constraintsFields.gridy = c;
					constraintsFields.gridwidth = 1;
					constraintsFields.gridheight = 1;
					constraintsFields.fill = GridBagConstraints.BOTH;
					constraintsFields.weightx = 1;
					constraintsFields.weighty = 1;
					// Add to GridBagLayout
					add(fields[r][c],constraintsFields);
					// Find fields which represent top and bottom ending of the board
				} else if(c==0 || c==9) {
					// Initialize top and bottom border fields
					fields[r][c] = new Field(this,true,null,r-1,c-1,100,true);
					fields[r][c].setBackground(Color.decode("#3D454F"));
					fields[r][c].setForeground(Color.WHITE);
					fields[r][c].setPreferredSize(new Dimension(70,45));
					// Set chess coordinates
					switch(r) {
					case 1: fields[r][c].setText("a"); break;
					case 2: fields[r][c].setText("b"); break;
					case 3: fields[r][c].setText("c"); break;
					case 4: fields[r][c].setText("d"); break;
					case 5: fields[r][c].setText("e"); break;
					case 6: fields[r][c].setText("f"); break;
					case 7: fields[r][c].setText("g"); break;
					case 8: fields[r][c].setText("h"); break;
					}
					// Set constraints
					constraintsFields.gridx = r;
					constraintsFields.gridy = c;
					constraintsFields.gridwidth = 1;
					constraintsFields.gridheight = 1;
					constraintsFields.fill = GridBagConstraints.BOTH;
					constraintsFields.weightx = 1;
					constraintsFields.weighty = 1;
					// Add to GridBagLayout
					add(fields[r][c],constraintsFields);
				} else {
					// Initialize playing fields (8x8 of two different colors)
					if(iteratorColor%2==0) {
						fields[r][c] = new Field(this,true,null,r-1,c-1,iteratorID,false);
						fields[r][c].setBackground(Color.decode("#DDE2C6"));
						fields[r][c].setPreferredSize(new Dimension(70,70));
						// Set constraints
						constraintsFields.gridx = r;
						constraintsFields.gridy = c;
						constraintsFields.gridwidth = 1;
						constraintsFields.gridheight = 1;
						constraintsFields.fill = GridBagConstraints.BOTH;
						constraintsFields.weightx = 1;
						constraintsFields.weighty = 1;
						// Add to GridBagLayout
						add(fields[r][c],constraintsFields);
						// Increment iterators to get new ids and switch color
						iteratorColor++;
						iteratorID++;
					} else {
						fields[r][c] = new Field(this,false,null,r-1,c-1,iteratorID,false);
						fields[r][c].setBackground(Color.decode("#93827F"));
						fields[r][c].setPreferredSize(new Dimension(70,70));
						// Set constraints
						constraintsFields.gridx = r;
						constraintsFields.gridy = c;
						constraintsFields.gridwidth = 1;
						constraintsFields.gridheight = 1;
						constraintsFields.fill = GridBagConstraints.BOTH;
						constraintsFields.weightx = 1;
						constraintsFields.weighty = 1;
						// Add to GridBagLayout
						add(fields[r][c],constraintsFields);
						// Increment iterators to get new ids and switch color
						iteratorColor++;
						iteratorID++;
					}
				}
			}
			// Increment color iterator to start the next row with opposide color
			iteratorColor++;
		}		
	}
	
	public Field getFieldByID(int ID) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void setupChesspieces(Field[][] fields) {
		// TODO Auto-generated method stub
	}
	
	public King searchAndGetKing(Boolean isBlack) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean isGameStarted() {
		return isGameStarted;
	}
	
	public boolean isGamePaused() {
		return isGamePaused;
	}
	
	public boolean isStartingMovement() {
		return isStartingMovement;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public int getRound() {
		return round;
	}
	
	public void pause() {
		// TODO Auto-generated method stub
	}
	
	public void resume() {
		// TODO Auto-generated method stub
	}
	
	public Notation getNotation() {
		return notation;
	}
	
	public Player getPlayerByColor(boolean isBlack) {
		if(!isBlack) {
			return players[0];
		} else {
			return players[1];
		}
	}
	
	private class FieldListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
		}
	}
}
