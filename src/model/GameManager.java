package model;

import java.util.ArrayList;

import controller.*;
import model.board.Board;
import model.character.Character;
import model.character.CharacterType;
import view.Journal;
import model.board.BoardShape;
import model.board.Cell;
import model.board.BoardChess;

public class GameManager {
	
	private int armiesNb;
    private GameState gameState;
    private BoardShape boardShape;
    private Board board;
    private Save save;
    private Army armies[];
    private InputController input = new InputController();
    
    
    public GameManager(BoardShape boardShape, String nameFileToSave) {
    	this.boardShape = boardShape;
    	this.gameState = GameState.RUNNING;
    	this.save = new Save(nameFileToSave);
    }

    public void setUpBattle() {
    	switch (boardShape) {
			default:
				board = new BoardChess();
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
    				if(input.wantToSave()) {
    					
    				}
    				playARound(playingArmy);
    				darkSideAlive = !armies[0].isEmpty();
    				darkSideAlive = !armies[1].isEmpty();
    				playingArmy = armies[i = ((i + 1) % 2)];
    				Journal.displayText(armies[0].dumpArmy());
    				Journal.displayText(armies[1].dumpArmy());
    			} while(darkSideAlive && lightSideAlive);
			break;
		}
    }

	private void playARound(Army playingArmy) {
    	Character chara = null;
    	Cell cell = null;
    	int ret;
    	boolean hasToPlayAgain;
    	do {
    		hasToPlayAgain = false;
    		do {
	    		chara = input.getCharacterToMove(playingArmy);
	    		cell = input.getRecquiredCell(board);
	    	} while(cell == null);
    		ret = playingArmy.moveCharacter(chara, cell); 
    		if(ret == 0) {
    			Journal.displayText("You cannot go on a cell where an ally is.");
    			hasToPlayAgain = true;
    		}
    	} while(hasToPlayAgain);
    }

    public int changeState() {
    	return 0;
    }
    
    public void save() {
    	ArrayList<Object> list = new ArrayList<Object>();
    	list.add(board);
    	list.add(armies);
    	System.out.println("" + save.save(list));
    }
    
    public void load() {
    	ArrayList<Object> list = save.load();
    	board = (Board) list.get(0);
    	armies = (Army[]) list.get(1);
    }
    
    public void test_dump() {
    	if(armies != null) {
    		System.out.println(armies[0].dumpArmy());
    	} else {
    		System.out.println("you noob");
    	}
    }
    

}
