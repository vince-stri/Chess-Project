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
			if(deltaX == 0) {
				int ySign = deltaY / Math.abs(deltaY);
				for(int i = 1; i < deltaY; i++) {
					Coordinates temp = new Coordinates(coords.getX(), coords.getY() + (ySign * i));
					if(board.getACell(temp).getCharacter() != null) {
						return false;
					}
				} return true;
			} else if(deltaY == 0) {
				int xSign = deltaX / Math.abs(deltaX);
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
	
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Rook fighting for the " + army + " army and I'm located at " + getCoordinates();
	}

}
