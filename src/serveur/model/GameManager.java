package serveur.model;

import java.util.ArrayList;

import client.view.Journal;
import serveur.main.ClientWrapper;
import serveur.model.board.Board;
import serveur.model.board.BoardChess;
import serveur.model.board.BoardShape;
import serveur.model.board.Cell;




/**
 * The game manager class which supervises all the game
 * @version 1.0
 * @author axel gauthier
 */
public class GameManager {
	
	/**
	 * The number of armies
	 */
	private int armiesNb;
	
	/**
	 * The number of players
	 */
	private int playersNb;
	
	/**
	 * The actual round of game
	 */
	private int round;
	
	/**
	 * The type of board where the game takes place
	 */
    private BoardShape boardShape;
    
    /**
     * The board object where the game takes place
     */
    private Board board;
    
    /**
     * The save object
     */
    private Save save;
    
    /**
     * The armies playing the game
     */
    private Army armies[];
    
    /**
     * An array holding all the references to the players objects playing this game
     */
    private ClientWrapper players[];
    
    /**
     * Constructor of GameManager
     * @param boardShape the type of board
     * @param nameFileToSave the path of the save file
     * @param players the references to the players
     */
    public GameManager(BoardShape boardShape, String nameFileToSave, ClientWrapper[] players) {
    	if(players == null) { throw new NullPointerException("Players must not be null"); }
    	this.boardShape = boardShape;
    	this.save = new Save(nameFileToSave);
    	this.players = players;
    	armiesNb = 2;//players.lenght;
    	playersNb = 2;
    	
    	setUpBattle();
    }

    /**
     * Set up the game components according to the type of game wanted
     * Warning: This method must be called before all the other
     */
    private void setUpBattle() {
    	round = 0;
    	switch (boardShape) {
			default:
				board = new BoardChess();
				armies = ArmyComponents.generateChessBoardArmies(board, 2, players); 
			break;
		}
    }
    
    /**
     * Start the game until one the players wins
     */
    public void startGame() {
    	switch (boardShape) {
    		default:
    			boolean darkSideAlive = !armies[0].isEmpty();
    			boolean lightSideAlive = !armies[1].isEmpty();
    			boolean stopGame = false;
    			ClientWrapper playingClient = null;
    			Army playingArmy = armies[(1 + round) % armiesNb];
    			playingClient = playingArmy.getClient();
    			do {
	    				playARound(playingArmy);
	    				darkSideAlive = !armies[0].isEmpty();
	    				lightSideAlive = !armies[1].isEmpty();
	    				playingArmy = armies[(1 + round) % armiesNb];
	    				playingClient = playingArmy.getClient();
	    				/**
	    				 * display the armies
	    				 */
	    				playingClient.displayBoard(board);
	    				/*
	    				Journal.displayText(armies[0].dumpArmy());
	    				Journal.displayText(armies[1].dumpArmy());
	    				*/
	    				if(playingClient.wantToSave()) {
	    					save();
	    					stopGame = true;
	    				}
    			} while(darkSideAlive && lightSideAlive && !stopGame);
    			if(stopGame) {
    				playingClient.displayText("We hope to see you back soon.");
    			} else if(lightSideAlive){
    				playingClient.displayText("The light side has won.");
    			} else {
    				playingClient.displayText("The dark side has won.");
    			}
			break;
		}
    }

    /**
     * Play a round of the game
     * @param playingArmy the army playing the game
     */
	private void playARound(Army playingArmy) {
    	Character chara = null;
    	Cell cell = null;
    	ClientWrapper client = playingArmy.getClient();
    	int ret;
    	boolean hasToPlayAgain;
    	do {
    		hasToPlayAgain = false;
    		
    		do {
    			chara = playingArmy.getCharacterToMove();
    			chara = client.getCharacterToMove(playingArmy);
	    		cell = client.getRecquiredCell(board);
	    	} while(cell == null);
    		
    		ret = playingArmy.moveCharacter(chara, cell); 
    		if(ret == 0) {
    			client.displayText("You cannot go on a cell where an ally is.");
    			hasToPlayAgain = true;
    		} else if(ret == 1){
    			client.displayText("Such a character cannot move like this.");
    			hasToPlayAgain = true;
    		}
    	} while(hasToPlayAgain);
    	round++;
    }
    
	/**
	 * Save the game choosing the elements to save 
	 */
    public void save() {
    	ArrayList<Object> list = new ArrayList<Object>();
    	list.add((Integer) round);
    	list.add(board);
    	list.add(armies);
    	save.save(list);
    }
    
    /**
     * Load the game from a save file
     * @return the list of objects loaded
     */
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
    
    /**
     * Start the game from scratch or from a save
     */
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
    	}
    	Journal.displayBoard(board);
    }
    

}
