package model.character;

import model.board.Cell;
import model.Army;
import model.Coordinates;
import model.board.Board;

/**
 * Class corresponding to a Pawn inherited from the Character class
 * @version 1.0
 */
@SuppressWarnings("serial")
public class Pawn extends Character {

	private boolean firstMove = true;
	private Coordinates direction;
	
	/**
	 * {@inheritDoc}
	 * @param direction the direction where the pawn is supposed to go
	 */
	public Pawn(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, Coordinates direction) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board);
		
		this.direction = direction;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
				Coordinates absoluteDestination = destination.getCoordinates();
				
				/*
				 * Verify if this the first move of the pawn or not
				 */
				if(firstMove 
						&& absoluteDestination.equals(coords.getX() + (direction.getX() * 2), coords.getY() + (direction.getY() * 2))) {
					/*
					 * In this case it can move two cells forward and it's not its first move anymore
					 */
					firstMove = false;
					return true;
				}
				
				firstMove = false;
				
				/*
				 * Verify if the pawn moves just in front of it
				 */
				if(absoluteDestination.equals(coords.getX() + direction.getX(), coords.getY() + direction.getY())) {
					return true;
				} else {
					/*
					 * Verify if there is an enemy on the diagonals in front of it where the pawn
					 * can jump
					 */
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

	/**
	 * {@inheritDoc}
	 */
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Pawn fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
}
