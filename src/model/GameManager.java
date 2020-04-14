package model;

import java.util.ArrayList;

import controller.*;
import model.board.Board;
import model.character.Character;
import view.Journal;
import model.board.BoardShape;
import model.board.Cell;
import model.board.BoardChess;

public class GameManager {
	
	private int armiesNb;
	private int round;
    private BoardShape boardShape;
    private Board board;
    private Save save;
    private Army armies[];
    private InputController input = new InputController();
    
    
    public GameManager(BoardShape boardShape, String nameFileToSave) {
    	this.boardShape = boardShape;
    	this.save = new Save(nameFileToSave);
    	armiesNb = 2;
    }

    private void setUpBattle() {
    	round = 0;
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
    			boolean darkSideAlive = !armies[0].isEmpty();
    			boolean lightSideAlive = !armies[1].isEmpty();
    			boolean stopGame = false;
    			Army playingArmy = armies[(1 + round) % armiesNb];
    			do {
    				if(input.wantToSave()) {
    					save();
    					stopGame = true;
    				} else {
	    				playARound(playingArmy);
	    				darkSideAlive = !armies[0].isEmpty();
	    				lightSideAlive = !armies[1].isEmpty();
	    				playingArmy = armies[(1 + round) % armiesNb];
	    				Journal.displayText(armies[0].dumpArmy());
	    				Journal.displayText(armies[1].dumpArmy());
    				}
    			} while(darkSideAlive && lightSideAlive && !stopGame);
    			if(darkSideAlive) {
    				Journal.displayText("The dark side has won.");
    			} else {
    				Journal.displayText("The light side has won.");
    			}
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
    		} else if(ret == 1){
    			Journal.displayText("Such a character cannot move like this.");
    			hasToPlayAgain = true;
    		}
    	} while(hasToPlayAgain);
    	round++;
    }
    
    public void save() {
    	ArrayList<Object> list = new ArrayList<Object>();
    	list.add((Integer) round);
    	list.add(board);
    	list.add(armies);
    	System.out.println("" + save.save(list));
    }
    
    public int load() {
    	ArrayList<Object> list = save.load();
    	if(list == null) {
    		return 1;
    	} else {
    		round = (Integer) list.get(0);
    		board = (BoardChess) list.get(1);
    		armies = (Army[]) list.get(2);
    		list.clear();
    		
    		board.loadCharacters(armies);
    		for(int i = 0; i < armies.length; i++) {
    			armies[i].setBoard(board);
    			armies[i].reloadCharacter();
    		}
    		return 0;
    	}
    }
    
    public void setUpGame() {
    	if(input.wantANewGame()) {
    		setUpBattle();
    	} else {
    		while(load() != 0) {
    			if(input.wantToRecoverFile()) {
    				String path = input.getAPath();
    				save.openFile(path);
    			} else {
    				setUpBattle();
    				break;
    			}
    		}
    		Journal.displayText(armies[0].dumpArmy());
			Journal.displayText(armies[1].dumpArmy());
    	}
    }
    

}
