package model.board;

import model.Coordinates;

public class ChessBoard extends Board {
	
    private Cell cells[][] = null;
	private int sideCellsNb;
	
	public ChessBoard() {
		this.sideCellsNb = 8;
		this.cells = new Cell[sideCellsNb][sideCellsNb];

		for(int i = 0; i < sideCellsNb; i++) {
			for(int j = 0; j < sideCellsNb; j++) {
				cells[i][j] = new Cell(CellType.CLASSIC, new Coordinates(i, j));				
			}
		}
	}
	
	public Cell getACell(Coordinates coord) {
		int x = coord.getX(), y = coord.getY();
		if(x >= 0 && x < sideCellsNb && y >= 0 && y < sideCellsNb) {
			return cells[x][y];
		} return null;
	}
	
	

}