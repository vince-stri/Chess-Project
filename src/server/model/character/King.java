package server.model.character;

import server.model.Army;
import server.model.Coordinates;
import server.model.board.Board;
import server.model.board.Cell;

/**
 * Class corresponding to a King inherited from the Character class
 * @author vincent acila
 * @version 1.0
 */
public class King extends Character {

	/**
	 * {@inheritDoc}
	 */
	public King(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, char symbol) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board, symbol);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isAPossibleMove(Cell destination) {
		Coordinates absolute = destination.getCoordinates();
		/*
		 * Verify if the cell is next to the king
		 */
		return absolute.equals(coords.getX() + 1, coords.getY())
				|| absolute.equals(coords.getX() + 1, coords.getY() - 1)
				|| absolute.equals(coords.getX() + 1, coords.getY() + 1)
				|| absolute.equals(coords.getX() - 1, coords.getY() + 1)
				|| absolute.equals(coords.getX() - 1, coords.getY() - 1)
				|| absolute.equals(coords.getX() - 1, coords.getY())
				|| absolute.equals(coords.getX(), coords.getY() - 1)
				|| absolute.equals(coords.getX(), coords.getY() + 1);
	}

	/**
	 * {@inheritDoc}
	 */
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm the King fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
	
	public void move() {
	}
}
