package model.character;

import model.Army;
import java.lang.Math;
import model.Coordinates;
import model.board.Board;
import model.board.Cell;

@SuppressWarnings("serial")
public class Bishop extends Character{

	public Bishop(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board) {
		super(maxHP, name, coords, army, maxArmor, damagePoints, board);
	}

	public boolean isAPossibleMove(Cell destination) {
		switch (board.getBoardShape()) {
			default:
			int absX, absY, xSign, ySign;
			Coordinates absolute = destination.getCoordinates();
			xSign = absolute.getX() - coords.getX();
			ySign = absolute.getY() - coords.getY();
			absX = Math.abs(xSign);
			absY = Math.abs(ySign);
			xSign /= absX;
			ySign /= absY;
			if(absX == absY) {
				for(int i = 1; i < absX; i++) {
					if(board.getACell(new Coordinates(coords.getX() + (xSign * i), coords.getY() + (ySign * i))).getCharacter() != null) {
						return false;
					}
				} return true;
			} else {
				return false;
			}
		}
	}
	
	public String dumpCharacter( ) {
		return "I'm " + name + ", I'm a Bishop fighting for the " + army + " army and I'm located at " + getCoordinates();
	}
}
