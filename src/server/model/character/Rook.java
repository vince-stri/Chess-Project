package server.model.character;

import java.io.Serializable;

import server.model.Army;
import server.model.Coordinates;
import server.model.board.Board;
import server.model.board.Cell;

/**
 * Class corresponding to a Rook inherited from the Character class
 * @version 1.0
 * @author vincent acila
 */
public class Rook extends Character {

	/**
	 * {@inheritDoc}
	 */
	public Rook(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, char symbol) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board, symbol);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
			int deltaX, deltaY;
			Coordinates absolute = destination.getCoordinates();
			deltaX = absolute.getX() - coords.getX();
			deltaY = absolute.getY() - coords.getY();
			
			/**
			 * Get the delta between the cell where the rook is
			 * and the cell where we want to move on the rook
			 */
			if(deltaX == 0) {
				int ySign = deltaY / Math.abs(deltaY);
				
				/*
				 * Verify if a character is on the path
				 */
				for(int i = 1; i < deltaY; i++) {
					Coordinates temp = new Coordinates(coords.getX(), coords.getY() + (ySign * i));
					if(board.getACell(temp).getCharacter() != null) {
						return false;
					}
				} return true;
			} else if(deltaY == 0) {
				int xSign = deltaX / Math.abs(deltaX);
				
				/*
				 * Verify if a character is on the path
				 */
				for(int i = 1; i < deltaX; i++) {
					if(board.getACell(new Coordinates(xSign * i, coords.getY())).getCharacter() != null) {
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
		return "I'm " + name + ", I'm a Rook fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
	
	public void move() {
	}

}
