package model;

public class Board {
    private int cellsNb;
    private int cellsSideNb;
    private BoardShape shape;
    
    /* The (0, 0) couple of coordinates is located at the top left of the board
     * On an usual chess game it means that the (8, 8) is located at the bottom right */
    public Cell cells[] = null;
    
    public Board(BoardShape shape, int cellsSideNb) {
    	this.cellsSideNb = cellsSideNb;
    	this.shape = shape;
    	switch (shape) {
			default: // CLASSIC
				this.cellsNb = cellsSideNb * cellsSideNb;				
			break;
		}
    }

    public Cell getACell(int x, int y, int z) {
    	for(int i = 0; i < cellsNb; i++) {
    		if(cells[i].isLocatedHere(x, y, z)) {
    			return cells[i];
    		}
    	}
    	return null;
    }

    public boolean isCellAvailable(Coordinates coordinates, Character character) {
    	return false;
    }

    /* The board is created line after line inside the array. */
    public int setUpBoard() {
    	switch (shape) {
			default: // CLASSIC
				cells = new Cell[cellsNb];
				Coordinates coord;
				int mod, div;
				for(int i = 0; i < cellsNb; i++) {
					mod = i % cellsSideNb;
					div = i / cellsSideNb;
					coord = new Coordinates(mod, div, 0);
					cells[i] = new Cell(CellType.CLASSIC, coord);
				}
			break;
		}
    	
    	return 0;
    }

}
