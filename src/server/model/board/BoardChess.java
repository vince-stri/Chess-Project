package server.model.board;

import server.model.Army;
import server.model.Coordinates;
import server.model.character.Character;

/**
 * Implementation of the Board abstract class
 * @version 1.0
 */
@SuppressWarnings("serial")
public class BoardChess extends Board {
	
	/**
	 * All the cells composing the board
	 */
    private Cell cells[][] = null;
    
    /**
     * The side of the 2D board
     */
	private int sideWidthsNb;
	
	public BoardChess() {
		super(BoardShape.CHESS);
		this.boardShape = BoardShape.CHESS;
		this.sideWidthsNb = 8;
		this.cells = new Cell[sideWidthsNb][sideWidthsNb];

		/* 
		 * Create all the board with classic cells
		 */
		for(int i = 0; i < sideWidthsNb; i++) {
			for(int j = 0; j < sideWidthsNb; j++) {
				cells[i][j] = new Cell(CellType.CLASSIC, new Coordinates(i, j));				
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Cell getACell(Coordinates coord) {
		int x = coord.getX(), y = coord.getY();
		if(x >= 0 && x < sideWidthsNb && y >= 0 && y < sideWidthsNb) {
			return cells[x][y];
		} return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public int getWidthsNb() {
		return sideWidthsNb;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public BoardShape getBoardShape() {
		return boardShape;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void loadCharacters(Army[] armies) {
		Character chara;
		for(int i = 0; i < armies.length; i++) {
			for(int j = 0; (chara = armies[i].getCharacter(j)) != null; j++) {
				chara.getCell(this).setCharacter(chara);
			}
		}
	}
	

}