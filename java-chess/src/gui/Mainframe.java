package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import board.Board;
import gui.menu.Chessmenu;
import gui.menu.Topmenu;

public class Mainframe extends JFrame {
	
	private Topmenu topmenu;
	private Chessmenu chessmenu;
	private Board board;

	public Mainframe() {
		initializeComponents();
	}
	
	public void initializeComponents() {
		// Style, Size, Behaviour
		setTitle("Java-Chess by Patrick Czarnetzki");
		setMinimumSize(new Dimension(990,768));
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		// North Component (Topmenu)
		topmenu = new Topmenu(this);
		getContentPane().add(topmenu,BorderLayout.NORTH);
		// Center Component (Board)
		board = new Board();
		getContentPane().add(board,BorderLayout.CENTER);
		// East Component (Recording Panel)
		JPanel recordingPanel = new JPanel(new BorderLayout());
		// North Component in Recording Panel (Clock Panel)
		JPanel clockPanel = new JPanel(new FlowLayout());
		clockPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		clockPanel.setBackground(Color.decode("#4B5869"));
		clockPanel.add(board.getPlayerByColor(false).getClock());
		clockPanel.add(board.getPlayerByColor(true).getClock());
		recordingPanel.add(clockPanel,BorderLayout.NORTH);
		// Center Component in Recording Panel (Notation)
		recordingPanel.add(board.getNotation());
		// South Component in Recording Panel (Chessmenu)
		chessmenu = new Chessmenu(this);
		recordingPanel.add(chessmenu,BorderLayout.SOUTH);
		// Adding recording Panel to East
		getContentPane().add(recordingPanel,BorderLayout.EAST);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Mainframe().setVisible(true);
			}
		});
	}
}