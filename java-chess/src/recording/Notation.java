package recording;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;
import board.Board;

public class Notation extends JPanel {
	private Board board;
	private List<Entry> entryList;
	
	public Notation(Board board) {
		// Initialize components and variables
		this.board = board;
		entryList = new ArrayList<Entry>();
		// Setup this JPanel
		setLayout(new BorderLayout());
		// Adding a headline
		JLabel lblHeadline = new JLabel("NOTATION");
		Font fntHeadline = new Font("SansSerif",Font.BOLD,20);
		lblHeadline.setOpaque(true);
		lblHeadline.setForeground(Color.WHITE);
		lblHeadline.setBackground(Color.decode("#32312F"));
		lblHeadline.setBorder(BorderFactory.createEmptyBorder(5,5,5,0));
		lblHeadline.setFont(fntHeadline);
		lblHeadline.setHorizontalAlignment(JLabel.LEFT);
		add(lblHeadline,BorderLayout.NORTH);
		// Create a JPanel to store single entries. Will be used as list in JScrollPane
		JPanel listPanel = new JPanel();
		// Styling of listPanel
		listPanel.setLayout(new BoxLayout(listPanel,BoxLayout.Y_AXIS));
		listPanel.setBackground(Color.decode("#262521"));
		listPanel.setBorder(null);
		// Create a JScrollPane
		JScrollPane listScrollPane = new JScrollPane(listPanel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
	
	public void addEntry(Entry entry) {
		// TODO Auto-generated method stub
	}
}