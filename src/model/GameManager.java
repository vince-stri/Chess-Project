package model;

import controller.*;
import model.board.Board;
import model.character.Character;
import model.character.CharacterType;
import model.board.BoardShape;
import model.board.Cell;
import model.board.ChessBoard;

public class GameManager {
	
	private int armiesNb;
    private GameState gameState;
    private BoardShape boardShape;
    private Board board;
    private Save save;
    private Army armies[];
    private InputController input = new InputController();
    
    
    public GameManager(BoardShape boardShape) {
    	this.boardShape = boardShape;
    	this.gameState = GameState.RUNNING;
    }

    public void setUpBattle() {
    	switch (boardShape) {
			default:
				board = new ChessBoard();
				armies = ArmyComponents.generateChessBoardArmies(board);
			break;
		}

    }
    
    public void startGame() {
    	switch (boardShape) {
    		default:
    			boolean darkSideAlive = true;
    			boolean lightSideAlive = true;
    			int i = 1;
    			Army playingArmy = armies[i];
    			do {
    				playARound(playingArmy);
    				darkSideAlive = !armies[0].isEmpty();
    				darkSideAlive = !armies[1].isEmpty();
    				playingArmy = armies[++i % 2];
    			} while(darkSideAlive && lightSideAlive);
			break;
		}
    }

	private void playARound(Army playingArmy) {
    	Character chara = null;
    	Cell cell = null;
    	do {
    		chara = input.getCharacterToMove(playingArmy);
    		cell = input.getRecquiredCell(board);
    	} while(cell == null);
    	
    	playingArmy.moveCharacter(chara, cell);
    }

    public int changeState() {
    	return 0;
    }

}
