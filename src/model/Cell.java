package model;


public class Cell {
    private CellType cellType;

    public Coordinates coordinates;

    public Character character;

    public boolean isCellEmpty() {
    	return false;
    }

    public Character getCharacter() {
    	return null;
    }

    public Cell(CellType cellType, Coordinates coordinates) {
    }

    public void setCharacter(Character character) {
    }

    public Cell getCell() {
    	return null;
    }
    
    public boolean isLocatedHere(int x, int y, int z) {
    	return coordinates.equals(x, y, z);
    }

}
