package gui.menu;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class MenuButton extends JButton {
	
	public MenuButton(String title) {
		// Set button title
		setText(title);
		// Styling
		Border btnPaddingBorder = BorderFactory.createEmptyBorder(10,10,10,10);
		setBackground(Color.decode("#3D454F"));
		setForeground(Color.WHITE);
		setBorder(btnPaddingBorder);
		setFocusable(false);
		// Add mouse listener
		addMouseListener(new MenuButtonMouseListener());
	}
	
	private class MenuButtonMouseListener implements MouseListener {
		@Override
		public void mouseEntered(MouseEvent event) {
			setBackground(Color.decode("#4B5869"));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			setBackground(Color.decode("#3D454F"));
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}		
	}

}
