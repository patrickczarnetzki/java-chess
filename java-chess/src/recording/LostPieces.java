package recording;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.gson.annotations.Expose;

import pieces.Chesspiece;

public class LostPieces extends JPanel {
	
	// Integer values
	private int lostPawnsWhite = 0;
	private int lostPawnsBlack = 0;
	private int lostRooksWhite = 0;
	private int lostRooksBlack = 0;
	private int lostKnightsWhite = 0;
	private int lostKnightsBlack = 0;
	private int lostBishopsWhite = 0;
	private int lostBishopsBlack = 0;
	private int lostQueensWhite = 0;
	private int lostQueensBlack = 0;
	// Label values
	private JLabel lblWhitePawnsValue = new JLabel(Integer.toString(lostPawnsWhite));
	private JLabel lblBlackPawnsValue = new JLabel(Integer.toString(lostPawnsBlack));
	private JLabel lblWhiteRooksValue = new JLabel(Integer.toString(lostRooksWhite));
	private JLabel lblBlackRooksValue = new JLabel(Integer.toString(lostRooksBlack));
	private JLabel lblWhiteKnightsValue = new JLabel(Integer.toString(lostKnightsWhite));
	private JLabel lblBlackKnightsValue = new JLabel(Integer.toString(lostKnightsBlack));
	private JLabel lblWhiteBishopsValue = new JLabel(Integer.toString(lostBishopsWhite));
	private JLabel lblBlackBishopsValue = new JLabel(Integer.toString(lostBishopsBlack));
	private JLabel lblWhiteQueensValue = new JLabel(Integer.toString(lostQueensWhite));
	private JLabel lblBlackQueensValue = new JLabel(Integer.toString(lostQueensBlack));

	
	public LostPieces() {
		// Styling, Behaviour, etc.
		setBackground(Color.decode("#3D454F"));
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(BorderFactory.createEmptyBorder(0,30,0,0));
		// Create labels which stores icons
		JLabel lblWhitePawns = new JLabel(new ImageIcon("src/gfx/PawnWhiteSmall.png"));
		JLabel lblBlackPawns = new JLabel(new ImageIcon("src/gfx/PawnBlackSmall.png"));
		JLabel lblWhiteRooks = new JLabel(new ImageIcon("src/gfx/RookWhiteSmall.png"));
		JLabel lblBlackRooks = new JLabel(new ImageIcon("src/gfx/RookBlackSmall.png"));
		JLabel lblWhiteKnights = new JLabel(new ImageIcon("src/gfx/KnightWhiteSmall.png"));
		JLabel lblBlackKnights = new JLabel(new ImageIcon("src/gfx/KnightBlackSmall.png"));
		JLabel lblWhiteBishops = new JLabel(new ImageIcon("src/gfx/BishopWhiteSmall.png"));
		JLabel lblBlackBishops = new JLabel(new ImageIcon("src/gfx/BishopBlackSmall.png"));
		JLabel lblWhiteQueens = new JLabel(new ImageIcon("src/gfx/QueenWhiteSmall.png"));
		JLabel lblBlackQueens = new JLabel(new ImageIcon("src/gfx/QueenBlackSmall.png"));
		// Create a panel which stores lost white chesspieces
		JPanel pnlLostWhitePieces = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLostWhitePieces.setBackground(Color.decode("#3D454F"));
		// Adding label / int combinations to lost white pieces panel
		pnlLostWhitePieces.add(lblWhitePawns);
		pnlLostWhitePieces.add(lblWhitePawnsValue).setForeground(Color.WHITE);
		pnlLostWhitePieces.add(lblWhiteRooks);
		pnlLostWhitePieces.add(lblWhiteRooksValue).setForeground(Color.WHITE);
		pnlLostWhitePieces.add(lblWhiteKnights);
		pnlLostWhitePieces.add(lblWhiteKnightsValue).setForeground(Color.WHITE);
		pnlLostWhitePieces.add(lblWhiteBishops);
		pnlLostWhitePieces.add(lblWhiteBishopsValue).setForeground(Color.WHITE);
		pnlLostWhitePieces.add(lblWhiteQueens);
		pnlLostWhitePieces.add(lblWhiteQueensValue).setForeground(Color.WHITE);
		// Create a panel which stores lost black chesspieces
		JPanel pnlLostBlackPieces = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnlLostBlackPieces.setBackground(Color.decode("#3D454F"));
		// Add label / int combinations to lost black pieces panel
		pnlLostBlackPieces.add(lblBlackPawns);
		pnlLostBlackPieces.add(lblBlackPawnsValue).setForeground(Color.WHITE);
		pnlLostBlackPieces.add(lblBlackRooks);
		pnlLostBlackPieces.add(lblBlackRooksValue).setForeground(Color.WHITE);
		pnlLostBlackPieces.add(lblBlackKnights);
		pnlLostBlackPieces.add(lblBlackKnightsValue).setForeground(Color.WHITE);
		pnlLostBlackPieces.add(lblBlackBishops);
		pnlLostBlackPieces.add(lblBlackBishopsValue).setForeground(Color.WHITE);
		pnlLostBlackPieces.add(lblBlackQueens);
		pnlLostBlackPieces.add(lblBlackQueensValue).setForeground(Color.WHITE);
		// Add white and black lost chesspieces panels to this component
		add(pnlLostWhitePieces);
		add(pnlLostBlackPieces);
	}
	
	public void addLostChesspiece(Chesspiece chesspiece) {
		// Check which color was beaten
		if(chesspiece.isBlack()) {
			// Black was beaten
			// Check which type of chesspiece was beaten
			switch(chesspiece.getType()) {
			case "Pawn": lostPawnsBlack++; lblBlackPawnsValue.setText(Integer.toString(lostPawnsBlack)); break;
			case "Rook": lostRooksBlack++; lblBlackRooksValue.setText(Integer.toString(lostRooksBlack)); break;
			case "Knight": lostKnightsBlack++; lblBlackKnightsValue.setText(Integer.toString(lostKnightsBlack)); break;
			case "Bishop": lostBishopsBlack++; lblBlackBishopsValue.setText(Integer.toString(lostBishopsBlack)); break;
			case "Queen": lostQueensBlack++; lblBlackQueensValue.setText(Integer.toString(lostQueensBlack)); break;
			}
		} else {
			// White was beaten
			// Check which type of chesspiece was beaten
			switch(chesspiece.getType()) {
			case "Pawn": lostPawnsWhite++; lblWhitePawnsValue.setText(Integer.toString(lostPawnsWhite)); break;
			case "Rook": lostRooksWhite++; lblWhiteRooksValue.setText(Integer.toString(lostRooksWhite)); break;
			case "Knight": lostKnightsWhite++; lblWhiteKnightsValue.setText(Integer.toString(lostKnightsWhite)); break;
			case "Bishop": lostBishopsWhite++; lblWhiteBishopsValue.setText(Integer.toString(lostBishopsWhite)); break;
			case "Queen": lostQueensWhite++; lblWhiteQueensValue.setText(Integer.toString(lostQueensWhite)); break;
			}
		}
	}
	
	public void clearLostChesspieces() {
		lostPawnsWhite = 0;
		lostPawnsBlack = 0;
		lostRooksWhite = 0;
		lostRooksBlack = 0;
		lostKnightsWhite = 0;
		lostKnightsBlack = 0;
		lostBishopsWhite = 0;
		lostBishopsBlack = 0;
		lostQueensWhite = 0;
		lostQueensBlack = 0;
		lblWhitePawnsValue.setText(Integer.toString(lostPawnsWhite));
		lblBlackPawnsValue.setText(Integer.toString(lostPawnsBlack));
		lblWhiteRooksValue.setText(Integer.toString(lostRooksWhite));
		lblBlackRooksValue.setText(Integer.toString(lostRooksBlack));
		lblWhiteKnightsValue.setText(Integer.toString(lostKnightsWhite));
		lblBlackKnightsValue.setText(Integer.toString(lostKnightsBlack));
		lblWhiteBishopsValue.setText(Integer.toString(lostBishopsWhite));
		lblBlackBishopsValue.setText(Integer.toString(lostBishopsBlack));
		lblWhiteQueensValue.setText(Integer.toString(lostQueensWhite));
		lblBlackQueensValue.setText(Integer.toString(lostQueensBlack));
	}
	
	public void refreshLostChesspieces() {
		lblWhitePawnsValue.setText(Integer.toString(lostPawnsWhite));
		lblBlackPawnsValue.setText(Integer.toString(lostPawnsBlack));
		lblWhiteRooksValue.setText(Integer.toString(lostRooksWhite));
		lblBlackRooksValue.setText(Integer.toString(lostRooksBlack));
		lblWhiteKnightsValue.setText(Integer.toString(lostKnightsWhite));
		lblBlackKnightsValue.setText(Integer.toString(lostKnightsBlack));
		lblWhiteBishopsValue.setText(Integer.toString(lostBishopsWhite));
		lblBlackBishopsValue.setText(Integer.toString(lostBishopsBlack));
		lblWhiteQueensValue.setText(Integer.toString(lostQueensWhite));
		lblBlackQueensValue.setText(Integer.toString(lostQueensBlack));
	}

	public int getLostPawnsWhite() {
		return lostPawnsWhite;
	}

	public void setLostPawnsWhite(int lostPawnsWhite) {
		this.lostPawnsWhite = lostPawnsWhite;
	}

	public int getLostPawnsBlack() {
		return lostPawnsBlack;
	}

	public void setLostPawnsBlack(int lostPawnsBlack) {
		this.lostPawnsBlack = lostPawnsBlack;
	}

	public int getLostRooksWhite() {
		return lostRooksWhite;
	}

	public void setLostRooksWhite(int lostRooksWhite) {
		this.lostRooksWhite = lostRooksWhite;
	}

	public int getLostRooksBlack() {
		return lostRooksBlack;
	}

	public void setLostRooksBlack(int lostRooksBlack) {
		this.lostRooksBlack = lostRooksBlack;
	}

	public int getLostKnightsWhite() {
		return lostKnightsWhite;
	}

	public void setLostKnightsWhite(int lostKnightsWhite) {
		this.lostKnightsWhite = lostKnightsWhite;
	}

	public int getLostKnightsBlack() {
		return lostKnightsBlack;
	}

	public void setLostKnightsBlack(int lostKnightsBlack) {
		this.lostKnightsBlack = lostKnightsBlack;
	}

	public int getLostBishopsWhite() {
		return lostBishopsWhite;
	}

	public void setLostBishopsWhite(int lostBishopsWhite) {
		this.lostBishopsWhite = lostBishopsWhite;
	}

	public int getLostBishopsBlack() {
		return lostBishopsBlack;
	}

	public void setLostBishopsBlack(int lostBishopsBlack) {
		this.lostBishopsBlack = lostBishopsBlack;
	}

	public int getLostQueensWhite() {
		return lostQueensWhite;
	}

	public void setLostQueensWhite(int lostQueensWhite) {
		this.lostQueensWhite = lostQueensWhite;
	}

	public int getLostQueensBlack() {
		return lostQueensBlack;
	}

	public void setLostQueensBlack(int lostQueensBlack) {
		this.lostQueensBlack = lostQueensBlack;
	}
}
