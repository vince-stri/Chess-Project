package server.model.board;

import java.io.Serializable;

import server.model.Coordinates;
import server.model.character.Character;


/**
 * A class corresponding to the cell composing a board.
 * @version 1.0
 * @author axel gauthier
 */
@SuppressWarnings("serial")
public class Cell implements Serializable {
	/**
	 * The type of the cell
	 */
    private CellType cellType;
    
    /**
     * The coordinates of the cell
     */
    private Coordinates coordinates;
    
    /**
     * The character who stands on the cell
     */
    private transient Character character;


    /**
     * Constructor for a Cell without character on it
     * @param cellType the type of the cell
     * @param coordinates the coordinates of the cell
     */
    public Cell(CellType cellType, Coordinates coordinates) {
    	this.cellType = cellType;
    	this.coordinates = coordinates;
    }
    
    /**
     * Constructor for a Cell with a character on it
     * @param cellType the type of the cell
     * @param coordinates the coordinates of the cell
     * @param character the character standing on the cell
     */
    public Cell(CellType cellType, Coordinates coordinates, Character character) {
    	this.cellType = cellType;
    	this.coordinates = coordinates;
    	this.character = character;
    }
    
    /**
     * Does the cell have a character on it?
     * @return true or false
     */
    public boolean isEmpty() {
    	return character == null;
    }

    /**
     * Getter of character
     * @return character or null
     */
    public Character getCharacter() {
    	return character;
    }

    /**
     * Setter of character
     * @param character the character to put on the cell
     */
    public void setCharacter(Character character) {
    	this.character = character;
    }
    
    /**
     * Is the character located at the same coordinates as the one given?
     * @param coord the coordinates we want to verify
     * @return true or false
     */
    public boolean isLocatedHere(Coordinates coord) {
    	return coordinates.equals(coord);
    }
    
    /**
	 * Override of the toString method.
	 * Return the type of the coordinates as also as its localization
	 * @return the string describing Coordinates
	 */
    public String toString() {
    	String str = "";
    	switch (cellType) {
			default:
				str = "Classic cell at ";
			break;
		}
    	return str + coordinates;
    }
    
    /**
     * Getter of Coordinates
     * @return the coordinates
     */
    public Coordinates getCoordinates() {
    	return coordinates;
    }

}
