package server.model.character;


import java.lang.Math;

import server.model.Army;
import server.model.Coordinates;
import server.model.board.Board;
import server.model.board.Cell;

/**
 * Class corresponding to a Bishop inherited from the Character class
 * @author vincent acila
 * @version 1.0
 */
public class Bishop extends Character{

	/**
	 * {@inheritDoc}
	 */
	public Bishop(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, char symbol) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board, symbol);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
			int absX, absY, xSign, ySign;
			Coordinates absolute = destination.getCoordinates();
			/**
			 * Get the delta between the cell where the character is
			 * and the cell where we want to move on the character
			 */
			xSign = absolute.getX() - coords.getX();
			ySign = absolute.getY() - coords.getY();
			
			/**
			 * The absolute of those deltas
			 */
			absX = Math.abs(xSign);
			absY = Math.abs(ySign);
			
			/*
			 * And the sign (positive or negative) of those
			 * coordinates
			 */
			xSign /= absX;
			ySign /= absY;
			
			/*
			 * Verify is the destination is diagonal relative to
			 * the character
			 */
			if(absX == absY) {
				/*
				 * Verify if a character is on the path
				 */
				for(int i = 1; i < absX; i++) {
					if(board.getACell(new Coordinates(coords.getX() + (xSign * i), coords.getY() + (ySign * i))).getCharacter() != null) {
						return false;
					}
				} return true;
			} else {
				return false;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Bishop fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
	
	public void move() {
	}
}
