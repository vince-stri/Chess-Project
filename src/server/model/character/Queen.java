package server.model.character;

import server.model.Army;
import server.model.Coordinates;
import server.model.board.Board;
import server.model.board.Cell;

/**
 * Class corresponding to a Queen inherited from the Character class
 * @author vincent acila
 * @version 1.0
 */
public class Queen extends Character{

	/**
	 * {@inheritDoc}
	 */
	public Queen(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, char symbol) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board, symbol);
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
			int deltaX, deltaY, absX, absY;
			Coordinates absolute = destination.getCoordinates();
			/*
			 * Implements a combination of the rook and the bishop movements
			 */
			deltaX = absolute.getX() - coords.getX();
			deltaY = absolute.getY() - coords.getY();
			absX = Math.abs(deltaX);
			absY = Math.abs(deltaY);
			if(deltaX == 0) {
				int ySign = deltaY / Math.abs(deltaY);
				for(int i = 1; i < absY; i++) {
					if(board.getACell(new Coordinates(coords.getX(), coords.getY() + ySign * i)).getCharacter() != null) {
						return false;
					}
				} return true;
			} else if(deltaY == 0) {
				int xSign = deltaX / Math.abs(deltaX);
				for(int i = 1; i < absX; i++) {
					if(board.getACell(new Coordinates(coords.getX() + xSign * i, coords.getY())).getCharacter() != null) {
						return false;
					}
				} return true;
			} else if(absX == absY){
				int xSign = deltaX / absX, ySign = deltaY / absY;
				for(int i = 1; i <= absX; i++) {
					if(board.getACell(new Coordinates(coords.getX() + xSign * i, coords.getY() +  ySign * i)).getCharacter() != null) {
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
		return "I'm " + name + ", I'm a Queen fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
	
	public void move() {
	}
}