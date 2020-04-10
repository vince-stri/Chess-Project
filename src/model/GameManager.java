package model;

import controller.*;

public class GameManager {
	
    private GameState gameState = GameState.RUNNING;
    private BoardShape boardShape;
    private Board board;
    private Save save;
    private int armiesNb;
    private int maxFightersNb;
    private Army armies[];
    private InputController input = new InputController();
    
    
    public GameManager(BoardShape boardShape, int armiesNb, int maxFightersNb) {
    	this.boardShape = boardShape;
    	this.armiesNb = armiesNb;
    	this.maxFightersNb = maxFightersNb;
    	this.armies = new Army[armiesNb];
    }

    public void setUpBattle() {
    	board = new Board(BoardShape.CLASSIC, 8);
    	board.setUpBoard();
    	
    	armies[0] = new Army(board, "DarkSide", maxFightersNb);
    	//armies[1] = new Army(board, "DarkSide", maxFightersNb);
    	//TO-DO: implement save
    }

	public void playARound(Army playingArmy) {
    	Character chara = null;
    	Cell cell = null;
    	do {
    		chara = input.getCharacterToMove(playingArmy);
    		cell = input.getRecquiredCell(board);
    	} while(board.isCellAvailable(cell));
    	
    	playingArmy.moveCharacter(chara, cell);
    }

    public int changeState() {
    	return 0;
    }

    public void setArmyToPlayer() {
    }
    
    public Army[] getArmies() {
    	return armies;
    }

}
