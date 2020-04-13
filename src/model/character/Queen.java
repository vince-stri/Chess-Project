package model.character;

import model.Army;
import model.Coordinates;
import model.board.Board;
import model.board.Cell;

@SuppressWarnings("serial")
public class Queen extends Character{

	public Queen(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board);
	}

	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
			int deltaX, deltaY;
			Coordinates absolute = destination.getCoordinates();
			deltaX = absolute.getX() - coords.getX();
			deltaY = absolute.getY() - coords.getY();
			return deltaX == deltaY || deltaX == 0 || deltaY == 0;
		}
	}
}