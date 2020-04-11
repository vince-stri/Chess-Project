package model.board;

import model.Coordinates;

public abstract class Board {
	protected BoardShape boardShape;
	
	public Board(BoardShape boardShape) {
		this.boardShape = boardShape;
	}
	
	public abstract Cell getACell(Coordinates coord);
	public abstract int getWidthsNb();
	public abstract BoardShape getBoardShape();
    
}
