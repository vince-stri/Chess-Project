package model;


public class Cell {
    private CellType cellType;

    public Coordinates coordinates;

    public Character character;

    public boolean isCellEmpty() {
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
    }

    public Cell getCell() {
    	return null;
    }
    
    public boolean isLocatedHere(int x, int y, int z) {
    	return coordinates.equals(x, y, z);
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
