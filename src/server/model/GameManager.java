package server.model;

import java.util.ArrayList;

import client.view.Journal;
import server.main.ClientWrapper;
import server.model.board.Board;
import server.model.board.BoardChess;
import server.model.board.BoardShape;
import server.model.board.Cell;
import server.model.character.Character;




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
    
    private Army playingArmy;
    
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
     * Warning: This method must be called before all the others
     */
    public void setUpBattle() {
    	round = 0;
    	switch (boardShape) {
			default:
				board = new BoardChess();
				armies = ArmyComponents.generateChessBoardArmies(board, 2, players);
				playingArmy = armies[0];
			break;
		}
    }
    
    /**
     * Verify if the move made by the player is valid or not
     * @param srcX X-coordinate of the character to be moved 
     * @param srcY Y-coordinate of the character to be moved
     * @param destX X-coordinate of the landing place 
     * @param destY Y-coordinate of the landing place
     * @param player The player
     * @return true if the move is valid, false otherwise
     */
    public boolean isAGoodMove(int srcX, int srcY, int destX, int destY, ClientWrapper player) {
		Coordinates src = new Coordinates(srcX, srcY);
		Coordinates dest = new Coordinates(destX, destY);
		Cell source = board.getACell(src);
		Cell destination = board.getACell(dest);
		if(source == null || destination == null) { // verify if the coordinates were correct
			return false;
		}
		Character selectedChara = source.getCharacter();
		if(selectedChara.getArmy().getClient() != player) { // verify if the selected character belongs to the player's army  
			return false;
		}
		if(selectedChara.isAPossibleMove(destination) == false) { // verify if the required move can be done
			return false;
		}
		if(destination.getCharacter() != null) { // verify if there is a character on the destination cell 
			return destination.getCharacter().getArmy().getClient() != player; // verify if this character is an ennemy 
		} return true;
    }

    /**
     * Play the move required by the player
     * @param srcX X-coordinate of the character to be moved 
     * @param srcY Y-coordinate of the character to be moved
     * @param destX X-coordinate of the landing place 
     * @param destY Y-coordinate of the landing place
     * @param player The player
     * @return Information code from moveCharacter() method
     */
    public int playMove(int srcX, int srcY, int destX, int destY, ClientWrapper player) {
    	Coordinates src = new Coordinates(srcX, srcY);
    	Coordinates dest = new Coordinates(destX, destY);
    	Cell source = board.getACell(src);
    	Cell destination = board.getACell(dest);
    	Character playingCharacter = source.getCharacter();
    	int infCode = playingCharacter.getArmy().moveCharacter(playingCharacter, destination);
    	this.playingArmy = armies[(1 + round) % armiesNb];
    	return infCode;
    }
    
    /**
     * Indicates if the game is won or not
     * @return Code corresponding to the winner. -1 if the game is still playing
     */
    public int isGameOver() {
    	for(int i = 0; i < armiesNb; i++) {
    		if(armies[i].isEmpty()) {
    			return i;
    		}
    	}
    	return -1;
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
    /*public void setUpGame() {
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
    }*/
    

}
