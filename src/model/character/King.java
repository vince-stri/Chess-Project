package model.character;

import model.Army;
import model.Coordinates;
import model.board.Board;
import model.board.Cell;

@SuppressWarnings("serial")
public class King extends Character {

	public King(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board);
	}

	@Override
	public boolean isAPossibleMove(Cell destination) {
		Coordinates absolute = destination.getCoordinates();
		return absolute.equals(new Coordinates(coords.getX() + 1, coords.getY()))
				|| absolute.equals(new Coordinates(coords.getX() + 1, coords.getY() - 1))
				|| absolute.equals(new Coordinates(coords.getX() + 1, coords.getY() + 1))
				|| absolute.equals(new Coordinates(coords.getX() - 1, coords.getY() + 1))
				|| absolute.equals(new Coordinates(coords.getX() - 1, coords.getY() - 1))
				|| absolute.equals(new Coordinates(coords.getX() - 1, coords.getY()))
				|| absolute.equals(new Coordinates(coords.getX(), coords.getY() - 1))
				|| absolute.equals(new Coordinates(coords.getX(), coords.getY() + 1));
	}

}
