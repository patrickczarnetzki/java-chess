package gui.menu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import board.Field;
import pieces.Bishop;
import pieces.Chesspiece;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;

public class Transformation extends JFrame {
	
	private MenuButton btnQueen;
	private MenuButton btnRook;
	private MenuButton btnBishop;
	private MenuButton btnKnight;
	private Field field;

	public Transformation(Field field) {
		// Initialize Field
		this.field = field;
		// Initialize Buttons
		btnQueen = new MenuButton("Queen");
		btnRook = new MenuButton("Rook");
		btnBishop = new MenuButton("Bishop");
		btnKnight = new MenuButton("Knight");
		// Add ActionListener to buttons
		btnQueen.addActionListener(new TransformationListener());
		btnRook.addActionListener(new TransformationListener());
		btnBishop.addActionListener(new TransformationListener());
		btnKnight.addActionListener(new TransformationListener());
		// Styling, Behaviour, etc.
		setTitle("Java-Chess Transformation");
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setMinimumSize(new Dimension(350,250));
		setMaximumSize(new Dimension(350,250));
		setPreferredSize(new Dimension(350,250));
		setResizable(false);
		setBackground(Color.decode("#3D454F"));
		// Create Panel to store buttons
		JPanel pnlTransformation = new JPanel();
		pnlTransformation.setLayout(new GridLayout(4,1));
		pnlTransformation.setBackground(Color.decode("#3D454F"));
		// Add buttons to panel
		pnlTransformation.add(btnQueen);
		pnlTransformation.add(btnRook);
		pnlTransformation.add(btnBishop);
		pnlTransformation.add(btnKnight);
		// Add Panel to JFrame
		add(pnlTransformation);
		// Set visible
		setVisible(true);
	}
	
	private class TransformationListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Chesspiece tmpChesspiece = field.getChesspiece();
			if(event.getSource().equals(btnQueen)) {
				// Set a new chesspiece to the field, in this case the user choosed a Queen
				field.setChesspiece(new Queen(field,tmpChesspiece.isBlack(),tmpChesspiece.getPlayer(),tmpChesspiece.getIcon(),"Queen"));
				// Game was paused until transformation is done, transformation is done now, resume the game
				field.getBoard().resume();
				// Close the frame without closing the complete game
				dispose();
			} else if(event.getSource().equals(btnRook)) {
				field.setChesspiece(new Rook(field,tmpChesspiece.isBlack(),tmpChesspiece.getPlayer(),tmpChesspiece.getIcon(),"Rook"));
				field.getBoard().resume();
				dispose();
			} else if(event.getSource().equals(btnBishop)) {
				field.setChesspiece(new Bishop(field,tmpChesspiece.isBlack(),tmpChesspiece.getPlayer(),tmpChesspiece.getIcon(),"Bishop"));
				field.getBoard().resume();
				dispose();
			} else if(event.getSource().equals(btnKnight)) {
				field.setChesspiece(new Knight(field,tmpChesspiece.isBlack(),tmpChesspiece.getPlayer(),tmpChesspiece.getIcon(),"Knight"));
				field.getBoard().resume();
				dispose();
			}
		}
	}
}
