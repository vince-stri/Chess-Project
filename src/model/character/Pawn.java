package model.character;

import model.board.Cell;
import model.Army;
import model.Coordinates;
import model.board.Board;

@SuppressWarnings("serial")
public class Pawn extends Character {

	private boolean firstMove = true;
	private Coordinates direction;
	
	public Pawn(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, Coordinates direction) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board);
		this.board = board;
		this.direction = direction;
	}
	
	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
				Coordinates absoluteDestination = destination.getCoordinates();
				
				if(firstMove 
						&& absoluteDestination.equals(coords.getX() + (direction.getX() * 2), coords.getY() + (direction.getY() * 2))) {
						firstMove = false;
						return true;
					}
				
				firstMove = false;
				
				if(absoluteDestination.equals(coords.getX() + direction.getX(), coords.getY() + direction.getY())) {
					return true;
				} else {
					if(direction.getX() != 0) {
						if(absoluteDestination.equals(coords.getX() + direction.getX(), coords.getY() + direction.getY() + 1)
								|| absoluteDestination.equals(coords.getX() + direction.getX(), coords.getY() + direction.getY() - 1)) {
							return destination.getCharacter() != null;
						} else {
							return false;
						}
					} else {
						if(absoluteDestination.equals(coords.getX() + direction.getX() + 1, coords.getY() + direction.getY())
								|| absoluteDestination.equals(coords.getX() + direction.getX() - 1, coords.getY() + direction.getY())) {
							return destination.getCharacter() != null;
						} else {
							return false;
						}
					}
				}
		}
	}

	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Pawn fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
}
