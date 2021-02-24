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
import gui.Mainframe;

public class DrawDialog extends JFrame {

	private MenuButton btnAccept;
	private MenuButton btnDenie;
	private Mainframe mainframe;
	private DrawButtonListener btnListener;
	
	public DrawDialog(Mainframe mainframe) {
		// Initialize variables
		this.mainframe = mainframe;
		btnListener = new DrawButtonListener();
		btnAccept = new MenuButton("Accept");
		btnDenie = new MenuButton("Denie");
		// Add listener to buttons
		btnAccept.addActionListener(btnListener);
		btnDenie.addActionListener(btnListener);
		// Create panel to store buttons
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(new GridLayout(2,1));
		btnPanel.setBackground(Color.decode("#3D454F"));
		// Add buttons to panel
		btnPanel.add(btnAccept);
		btnPanel.add(btnDenie);
		// Create JPanel to store headline
		JPanel lblPanel = new JPanel(new GridLayout(1,1));
		// Create the headline
		JLabel lblDraw;
		// Check which player is asking for draw
		if(mainframe.getBoard().getPlayerByColor(false).isPlaying()) {
			// White is playing and asking for draw
			lblDraw = new JLabel("WHITE IS ASKING FOR A DRAW. ACCEPT?");
			lblPanel.setBackground(Color.WHITE);
			lblDraw.setForeground(Color.BLACK);
		} else {
			// Black is playing and asking for draw
			lblDraw = new JLabel("BLACK IS ASKING FOR A DRAW. ACCEPT?");
			lblPanel.setBackground(Color.BLACK);
			lblDraw.setForeground(Color.WHITE);
		}
		// Style rest of headline
		lblDraw.setFont(new Font("SansSerif",Font.BOLD,20));
		lblDraw.setHorizontalAlignment(JLabel.CENTER);
		lblPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		// Add headline to lblPanel
		lblPanel.add(lblDraw);
		// Frame style, behaviour, etc.
		setTitle("Asking for a Draw");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setResizable(false);
		setSize(450,250);
		setBackground(Color.decode("#3D454F"));
		// Add panels to frame
		add(lblPanel,BorderLayout.NORTH);
		add(btnPanel,BorderLayout.CENTER);
		// Set visible
		setVisible(true);
	}
	
	private class DrawButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if(event.getSource().equals(btnAccept)) {
				mainframe.getTopmenu().startNewGame();
				dispose();
			} else if(event.getSource().equals(btnDenie)) {
				mainframe.getBoard().resume();
				dispose();
			}
		}
	}
}