package model.board;

import java.io.Serializable;

import model.Coordinates;
import model.character.Character;

public class Cell implements Serializable {
    private CellType cellType;
    private Coordinates coordinates;
    private transient Character character;

    public boolean isEmpty() {
    	return character == null;
    }

    public Character getCharacter() {
    	return character;
    }

    public Cell(CellType cellType, Coordinates coordinates) {
    	this.cellType = cellType;
    	this.coordinates = coordinates;
    }
    
    public Cell(CellType cellType, Coordinates coordinates, Character character) {
    	this.cellType = cellType;
    	this.coordinates = coordinates;
    	this.character = character;
    }

    public void setCharacter(Character character) {
    	this.character = character;
    }
    
    public boolean isLocatedHere(Coordinates coord) {
    	return coordinates.equals(coord);
    }
    
    public String toString() {
    	String str = "";
    	switch (cellType) {
			default:
				str = "Classic cell at ";
			break;
		}
    	return str + coordinates;
    }
    
    public Coordinates getCoordinates() {
    	return coordinates;
    }

}
