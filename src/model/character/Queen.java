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
			int deltaX, deltaY, absX, absY;
			Coordinates absolute = destination.getCoordinates();
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
	
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Queen fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
}