package model.character;

import model.Army;
import model.Coordinates;
import model.board.Board;
import model.board.Cell;

/**
 * Class corresponding to a Knight inherited from the Character class
 * @author vincent acila
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Knight extends Character {

	/**
	 * {@inheritDoc}
	 */
	public Knight(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, char symbol) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board, symbol);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAPossibleMove(Cell destination) {
		
		Coordinates absolute = destination.getCoordinates();
		/*
		 * Verify if the cell is in the knight scope
		 */
		return absolute.equals(coords.getX() + 1, coords.getY() + 2)
				|| absolute.equals(coords.getX() + 1, coords.getY() - 2)
				|| absolute.equals(coords.getX() - 1, coords.getY() - 2)
				|| absolute.equals(coords.getX() - 1, coords.getY() + 2)
				|| absolute.equals(coords.getX() + 2, coords.getY() - 1)
				|| absolute.equals(coords.getX() - 2, coords.getY() - 1)
				|| absolute.equals(coords.getX() - 2, coords.getY() + 1)
				|| absolute.equals(coords.getX() + 2, coords.getY() + 1);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Knight fighting for the " + army + " army and I'm located at " + getCoordinates();
	}

	public void move() {
	}
}
