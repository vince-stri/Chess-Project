package model.board;

import java.io.Serializable;

import model.Coordinates;

public abstract class Board implements Serializable{
	protected BoardShape boardShape;
	
	public Board(BoardShape boardShape) {
		this.boardShape = boardShape;
	}
	
	public abstract Cell getACell(Coordinates coord);
	public abstract int getWidthsNb();
	public abstract BoardShape getBoardShape();
    
}
