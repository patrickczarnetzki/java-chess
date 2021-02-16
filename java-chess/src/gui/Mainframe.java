package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import board.Board;
import gui.menu.Chessmenu;
import gui.menu.Topmenu;

public class Mainframe extends JFrame {
	
	private Topmenu topmenu;
	private Chessmenu chessmenu;
	private Board board;

	public Mainframe() {
		initializeComponents();
		// TODO Auto-generated method stub
	}
	
	public void initializeComponents() {
		// TODO Auto-generated method stub
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Mainframe().setVisible(true);
			}
		});
	}
}
