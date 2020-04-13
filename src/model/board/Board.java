package model.board;

import java.io.Serializable;
import model.Army;
import model.Coordinates;

@SuppressWarnings("serial")
public abstract class Board implements Serializable{
	protected BoardShape boardShape;
	
	public Board(BoardShape boardShape) {
		this.boardShape = boardShape;
	}
	
	public abstract Cell getACell(Coordinates coord);
	public abstract int getWidthsNb();
    public abstract void loadCharacters(Army[] armies);
    
    public BoardShape getBoardShape() {
    	return boardShape;
    }
}
