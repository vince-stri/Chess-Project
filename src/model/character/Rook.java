package model.character;

import model.Army;
import model.Coordinates;
import model.board.Cell;
import model.board.Board;

@SuppressWarnings("serial")
public class Rook extends Character {

	public Rook(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board);
	}

	@Override
	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
			int deltaX, deltaY;
			Coordinates absolute = destination.getCoordinates();
			deltaX = absolute.getX() - coords.getX();
			deltaY = absolute.getY() - coords.getY();
			return deltaX == 0 || deltaY == 0;
		}
	}

}
