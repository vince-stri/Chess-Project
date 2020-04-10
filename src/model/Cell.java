package model;

import model.character.Character;

public class Cell {
    private CellType cellType;
    private Coordinates coordinates;
    private Character character;

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

}
