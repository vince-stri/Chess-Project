package model.board;

import java.io.Serializable;
import model.Army;
import model.Coordinates;

/**
 * An abstract class to factorize the different potential boards that would be implemented
 * @version 1.0
 */
@SuppressWarnings("serial")
public abstract class Board implements Serializable{
	/**
	 * The type of board used.
	 */
	protected BoardShape boardShape;
	
	/**
	 * Constructor for board depending on the shape given
	 * @param boardShape the shape of the board
	 */
	public Board(BoardShape boardShape) {
		this.boardShape = boardShape;
	}
	
	/**
	 * Get a cell according to given coordinates
	 * @param coord the given coordinates
	 * @return the corresponding cell
	 */
	public abstract Cell getACell(Coordinates coord);
	
	/**
	 * Get the side a board
	 * @return the wanted side size
	 */
	public abstract int getWidthsNb();
	
	/**
	 * Connect a cell to its corresponding character after a reboot of the game
	 * @param armies the armies having the characters
	 */
    public abstract void loadCharacters(Army[] armies);
    
    /**
     * Getter of BoardShape
     * @return the boardShape
     */
    public BoardShape getBoardShape() {
    	return boardShape;
    }
}
