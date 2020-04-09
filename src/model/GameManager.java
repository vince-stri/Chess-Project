package model;

import java.util.ArrayList;
import java.util.List;
import controller.*;

public class GameManager {
	
    private GameState gameState = GameState.RUNNING;
    private BoardShape boardShape;
    private Board board;
    private Save save;
    private int armiesNb;
    private int maxFightersNb;
    private Army armies[];
    private List<InputController> input = new ArrayList<InputController> ();
    
    
    public GameManager(BoardShape boardShape, int armiesNb, int maxFightersNb) {
    	this.boardShape = boardShape;
    	this.armiesNb = armiesNb;
    	this.maxFightersNb = maxFightersNb;
    	this.armies = new Army[armiesNb];
    }

    public void setUpBattle() {
    	board = new Board(BoardShape.CLASSIC, 8);
    	board.setUpBoard();
    	
    	armies[0] = new Army(board, "LightSide", maxFightersNb);
    	armies[1] = new Army(board, "DarkSide", maxFightersNb);
    	//TO-DO: implement save
    }

    public void playARound(Army playingArmy) {
    }

    public int changeState() {
    	return 0;
    }

    public void setArmyToPlayer() {
    }

}
