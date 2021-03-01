package pieces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;

import com.google.gson.annotations.Expose;

import board.Field;
import player.Player;

public class King extends Chesspiece {
	@Expose
	private boolean isUntouched;
	@Expose
	private boolean isUsingShortRochade;
	@Expose
	private boolean isUsingLongRochade;
	@Expose
	private boolean isInCheck;
	
	public King(Field field, boolean isBlack, Player player, ImageIcon icon, String type) {
		super(field, isBlack, player, icon, type);
		isUntouched = true;
		isUsingShortRochade = false;
		isUsingLongRochade = false;
		isInCheck = false;
	}
	
	@Override
	public boolean isValidMovement(Field endingField) {
		// Store IDs of starting and ending field
		int startingFieldID = getField().getID();
		int endingFieldID = endingField.getID();
		// Store starting and ending column for exception handling on column 0 and 7 and movement calculation
		int startingColumn = getField().getColumn();
		int endingColumn = endingField.getColumn();
		// Store starting and ending row for movement calculation
		int startingRow = getField().getRow();
		int endingRow = endingField.getRow();
		// Create a list which stores valid field id's
		List<Integer> validMovement = new ArrayList<Integer>();
		// Attacking and Movement is the same
		// Check for exception on column 0
		if(startingColumn==0) {
			validMovement.add(-8);
			validMovement.add(-7);
			validMovement.add(1);
			validMovement.add(8);
			validMovement.add(9);
		// Check for exception on column 7
		} else if(startingColumn==7) {
			validMovement.add(-9);
			validMovement.add(-8);
			validMovement.add(-1);
			validMovement.add(7);
			validMovement.add(8);
		// Has to be normal movement
		} else {
			// Check for long and short rochade
			// Check if king wasn't moved yet
			if(isUntouched() ) {
				// Check for long rochade
				if(endingFieldID==startingFieldID-2) {
					// Check if Rook is on starting field
					Chesspiece tmpChesspiece = endingField.getBoard().getFieldByID(startingFieldID-4).getChesspiece();
					if(tmpChesspiece!=null) {
						if(tmpChesspiece.isBlack()==isBlack()) {
							if(tmpChesspiece.getType().equals("Rook")) {
								Rook tmpRook = (Rook) tmpChesspiece;
								// Check if Rook wasn't moved before
								if(tmpRook.isUntouched()) {
									// Check for a free route between King and Rook
									for(int i=startingFieldID-1; i>startingFieldID-4; i--) {
										if(endingField.getBoard().getFieldByID(i).isOccupied()) {
											isUsingLongRochade = false;
											break;
										} else {
											isUsingLongRochade = true;
											validMovement.add(-2);
										}
									}
								}
							}
						}
					}
				} else if(endingFieldID==startingFieldID+2) {
					// Check if Rook is on starting field
					Chesspiece tmpChesspiece = endingField.getBoard().getFieldByID(startingFieldID+3).getChesspiece();
					if(tmpChesspiece!=null) {
						if(tmpChesspiece.isBlack()==isBlack()) {
							if(tmpChesspiece.getType().equals("Rook")) {
								Rook tmpRook = (Rook) tmpChesspiece;
								// Check if Rook wasn't moved before
								if(tmpRook.isUntouched()) {
									// Check for a free route between King and Rook
									for(int i=startingFieldID+1; i<startingFieldID+3; i++) {
										if(endingField.getBoard().getFieldByID(i).isOccupied()) {
											isUsingShortRochade = false;
											break;
										} else {
											isUsingShortRochade = true;
											validMovement.add(2);
										}
									}
								}
							}
						}
					}
				}
			}
			// Normal movement
			validMovement.add(-9);
			validMovement.add(-8);
			validMovement.add(-7);
			validMovement.add(-1);
			validMovement.add(1);
			validMovement.add(7);
			validMovement.add(8);
			validMovement.add(9);
		}
		// Check for a match
		for(int i=0; i<validMovement.size(); i++) {
			// Check for own color (Invalid movement)
			if(endingField.isOccupied()) {
				if(endingField.getChesspiece().isBlack()==isBlack()) {
					return false;
				} else {
					// Field is occupied by opponent color
					if((validMovement.get(i)+startingFieldID)==endingFieldID) {
						isUntouched = false;
						setHasBeatenEnemy(true);
						return true;
					}
				}
			} else {
				// Field is not occupied
				if((validMovement.get(i)+startingFieldID)==endingFieldID) {
					isUntouched = false;
					setHasBeatenEnemy(false);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isUntouched() {
		return isUntouched;
	}
	
	public boolean isUsingShortRochade() {
		return isUsingShortRochade;
	}
	
	public void setIsUsingShortRochade(boolean isUsingShortRochade) {
		this.isUsingShortRochade = isUsingShortRochade;
	}
	
	public boolean isUsingLongRochade() {
		return isUsingLongRochade;
	}
	
	public void setIsUsingLongRochade(boolean isUsingLongRochade) {
		this.isUsingLongRochade = isUsingLongRochade;
	}
	
	public boolean isInCheck() {
		// Get field id of king
		int kingID = getField().getID();
		// Column of the check test for iteration and detecting the end of the board
		int checkTestColumn = getField().getColumn()-1;
		// Row of the check test for iteration and detectin the end of the board
		int checkTestRow = getField().getRow()-1;
		// Checking horizontal left
		// Start with the first left field beside the king and go on until you reach the end of the board
		for(int i=kingID-1; i>=0; i--) {
			// Check if we have reached the end of the board (left border)
			if(checkTestColumn<0) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Rook")) {
						return true;
					} else if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) {
						// Check if King would be exactly one field away
						if(kingID-1==i) {
							return true;
						}
					} else {
						// Chesspiece is an enemy but not a Queen, Rook or King
						isInCheck = false;
						break;
					}
				}
			}
			checkTestColumn--;
		}
		// Start to check right route
		// Set check test column to new position
		checkTestColumn = getField().getColumn()+1;
		// Checking horizontal right
		// Start with the first right field beside the king and go on until you reach the end of the board
		for(int i=kingID+1; i<=63; i++) {
			// Check if we have reached the end of the board (right border)
			if(checkTestColumn>7) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Rook")) {
						return true;
					} else if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) { 
						if(kingID+1==i) {
							return true;
						}
					} else {
						// Chesspiece is an enemy but not a Queen, Rook or King
						isInCheck = false;
						break;
					}
				}
			}
			checkTestColumn++;
		}
		// Checking vertical top
		// Start with the first field above the king and go on until you reach the end of the board
		for(int i=kingID-8; i>=0; i=i-8) {
			// Check if we have reached the end of the board (top border)
			if(checkTestRow<0) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Rook")) {
						return true;
					} else if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) { 
						if(kingID-8==i) {
							return true;
						}
					} else {
						// Chesspiece is an enemy but not a Queen or Rook
						isInCheck = false;
						break;
					}
				}
			}
			checkTestRow--;
		}
		checkTestRow = getField().getRow()+1;
		// Checking vertical bottom
		// Start with the first field under the king and go on until you reach the end of the board
		for(int i=kingID+8; i<=63; i=i+8) {
			// Check if we have reached the end of the board (bottom border)
			if(checkTestRow>7) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Rook")) {
						return true;
					} else if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) {
						if(kingID+8==i) {
							return true;
						}
					} else {
						// Chesspiece is an enemy but not a Queen or Rook
						isInCheck = false;
						break;
					}
				}
			}
			checkTestRow++;
		}
		// Checking diagonals
		// Upper left
		checkTestColumn = getField().getColumn()-1;
		for(int i=kingID-9; i>=0; i=i-9) {
			//Check if we have reached column 0 (break)
			if(checkTestColumn<0) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) {
						if(kingID-9==i) {
							return true;
						}
					} else if(tmpChesspiece.getType().equals("Pawn")) {
						if(!isBlack()) {
							if(kingID-9==i) {
								return true;
							} else {
								break;
							}
						}
					} else if(tmpChesspiece.getType().equals("Bishop")) {
						return true;
					} else {
						// Chesspiece is an enemy but not a Queen, Rook or King
						isInCheck = false;
						break;
					}
				}
			}
			checkTestColumn--;
		}
		// Upper right
		checkTestColumn = getField().getColumn()+1;
		for(int i=kingID-7; i>=0; i=i-7) {
			//Check if we have reached column 0 (break)
			if(checkTestColumn>7) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) {
						if(kingID-7==i) {
							return true;
						}
					} else if(tmpChesspiece.getType().equals("Pawn")) {
						if(!isBlack()) {
							if(kingID-7==i) {
								return true;
							} else {
								break;
							}
						}
					} else if(tmpChesspiece.getType().equals("Bishop")) {
						return true;
					} else {
						// Chesspiece is an enemy but not a Queen or Rook
						isInCheck = false;
						break;
					}
				}
			}
			checkTestColumn++;
		}
		// Lower left
		checkTestColumn = getField().getColumn()-1;
		for(int i=kingID+7; i<=63; i=i+7) {
			//Check if we have reached column 0 (break)
			if(checkTestColumn<0) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) {
						if(kingID+7==i) {
							return true;
						}
					} else if(tmpChesspiece.getType().equals("Pawn")) {
						if(isBlack()) {
							if(kingID+7==i) {
								return true;
							} else {
								break;
							}
						}
					} else if(tmpChesspiece.getType().equals("Bishop")) {
						return true;
					} else {
						// Chesspiece is an enemy but not a Queen or Rook
						isInCheck = false;
						break;
					}
				}
			}
			checkTestColumn--;
		}
		// Lower right
		checkTestColumn = getField().getColumn()+1;
		for(int i=kingID+9; i<=63; i=i+9) {
			//Check if we have reached column 7 (break)
			if(checkTestColumn>7) {
				break;
			}
			// Check if field is occupied
			if(getField().getBoard().getFieldByID(i).isOccupied()) {
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(i).getChesspiece();
				// Check if it is occupied by own color (no check on this route possible)
				if(tmpChesspiece.isBlack()==isBlack()) {
					isInCheck = false;
					break;
				} else {
					// It is occupied by enemy color
					// Check for Rook and Queen on that position (Would be check)
					if(tmpChesspiece.getType().equals("Queen")) {
						return true;
					} else if(tmpChesspiece.getType().equals("King")) {
						if(kingID+9==i) {
							return true;
						}
					} else if(tmpChesspiece.getType().equals("Pawn")) {
						if(isBlack()) {
							if(kingID+9==i) {
								return true;
							} else {
								break;
							}
						}
					} else if(tmpChesspiece.getType().equals("Bishop")) {
						return true;
					} else {
						// Chesspiece is an enemy but not a Queen or Rook
						isInCheck = false;
						break;
					}
				}
			}
			checkTestColumn++;
		}
		// Check test for Knights
		checkTestColumn = getField().getColumn();
		List<Integer> knightMovement = new ArrayList<Integer>();
		// Exception handling on column 0
		if(checkTestColumn==0) {
			knightMovement = Arrays.asList(-15,-6,10,17);
		// Exception handling on column 7
		} else if(checkTestColumn==7) {
			knightMovement = Arrays.asList(-17,-10,6,15);
			// No exception
		} else {
			knightMovement = Arrays.asList(-17,-15,-10,-6,6,10,15,17);
		}
		for(int i=0; i<knightMovement.size(); i++) {
			if(getField().getBoard().getFieldByID(kingID+knightMovement.get(i))!=null) {
				// Get chesspiece on that position
				Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(kingID+knightMovement.get(i)).getChesspiece();
				if(tmpChesspiece!=null) {
					// Check if it is an enemy
					if(tmpChesspiece.isBlack()!=isBlack()) {
						// Check if it is an Knight
						if(tmpChesspiece.getType().equals("Knight")) {
							return true;
						}
					}
				}
			}
		}
 		return isInCheck;
	}
	
	public void setIsUntouched(boolean isUntouched) {
		this.isUntouched = isUntouched;
	}
	
	public boolean isCheckmate() {
		boolean isCheckmate = false;
		// Check if king is in check
		if(isInCheck()) {
			// Go through all fields and search for the first chesspiece of own color
			for(int i=0; i<=63; i++) {
				// Check if field is occupied
				if(getField().getBoard().getFieldByID(i).isOccupied()) {
					// Check if field is occupied by own color
					if(getField().getBoard().getFieldByID(i).getChesspiece().isBlack()==isBlack()) {
						// Check all possible ending fields of the board for valid movement
						for(int j=0; j<=63; j++) {
							if(getField().getBoard().getFieldByID(i).getChesspiece().isValidMovement(getField().getBoard().getFieldByID(j))) {
								// Restore chesspiece on new position for restoring
								Chesspiece tmpChesspiece = getField().getBoard().getFieldByID(j).getChesspiece();
								if(tmpChesspiece!=null) {
								} else {
								}
								// Set chesspiece to new position
								getField().getBoard().getFieldByID(j).setChesspiece(getField().getBoard().getFieldByID(i).getChesspiece());
								// Delete chesspiece on old position
								getField().getBoard().getFieldByID(i).deleteChesspiece();
								// Do the check test again
								if(isInCheck()) {
									// Set chesspiece to old position
									getField().getBoard().getFieldByID(i).setChesspiece(getField().getBoard().getFieldByID(j).getChesspiece());
									// Delete chesspiece on new position
									getField().getBoard().getFieldByID(j).deleteChesspiece();
									// If there was a chesspiece on new position restore it
									if(tmpChesspiece!=null) {
										getField().getBoard().getFieldByID(j).setChesspiece(tmpChesspiece);
									}
									isCheckmate = true;
								} else {
									// Set chesspiece to old position
									getField().getBoard().getFieldByID(i).setChesspiece(getField().getBoard().getFieldByID(j).getChesspiece());
									// Delete chesspiece on new position
									getField().getBoard().getFieldByID(j).deleteChesspiece();
									// If there was a chesspiece on new position restore it
									if(tmpChesspiece!=null) {
										getField().getBoard().getFieldByID(j).setChesspiece(tmpChesspiece);
									}
									return false;
								}
							}
						}
					}
				}
			}
		}
		return isCheckmate;
	}
}
