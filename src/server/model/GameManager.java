package server.model;

import java.rmi.RemoteException;
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
	 * The number of clients currently connected to the game
	 */
	private int connectedClientsNb;
	
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
     * The pseudos of the connected players
     */
    private String pseudos[];
    
    /**
     * The army which is supposed to play the current move
     */
    private Army playingArmy;
    
    /**
     * Id for the game manager
     */
    private String idGM;
    
    /**
     * Constructor of GameManager
     * @param boardShape the type of board
     * @param nameFileToSave the path of the save file
     * @param playersNb the number of players
     */
    public GameManager(BoardShape boardShape, String nameFileToSave, int playerNb) {
    	this.boardShape = boardShape;
    	this.save = new Save(nameFileToSave);
    	this.armiesNb = playerNb;
    	this.playersNb = playerNb;
    	this.connectedClientsNb = 0;
    	this.players = new ClientWrapper[2];
    	this.pseudos = new String[2];
    }

    /**
     * Add a client to the GameManager
     * @param client the client to add
     * @return false if the client could not be added
     * @throws RemoteException 
     */
    public boolean addClient(ClientWrapper client) throws RemoteException {
    	if(connectedClientsNb >= playersNb) {
    		return false;
    	} else {
    		for(int i = 0; i < connectedClientsNb; i++) {
    			players[i].displayInfo("Un joueur vient d'arriver");
    		}
    		pseudos[connectedClientsNb] = client.getClient().GetPseudo();
    		players[connectedClientsNb++] = client;
    		System.out.println("The number of connected clients: " + connectedClientsNb);
    		return true;
    	}
    }
    
    /**
     * Set up the game components according to the type of game wanted
     * Warning: This method must be called before all the others
     * @throws RemoteException raised during connection issue by RMI
     */
    public void setUpBattle() throws RemoteException {
    	round = 0;
    	switch (boardShape) {
			default:
				board = new BoardChess();
				armies = ArmyComponents.generateChessBoardArmies(board, 2);
				playingArmy = armies[0];
			break;
		}
    }
    
    /**
     * Set the playing army
     * @throws RemoteException raised during connection issue by RMI
     */
    public void setPlayingArmy() throws RemoteException {
    	playingArmy = armies[0];
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

			player.displayInfo("/!\\ ------ Les coordonn�es entr�es sont invalides ------ /!\\");

			return false;
		}
		Character selectedChara = source.getCharacter();
		if(selectedChara == null) {

			player.displayInfo("/!\\ ------ Il n'y a pas de personnage dans la case s�l�ctionn�e ------ /!\\");

			return false;
		}
		if(! selectedChara.getArmy().getClientWrapper().equals(player)) { // verify if the selected character belongs to the player's army

			player.displayInfo("/!\\ ------ Le personnage d�sign� n'appartient pas � votre arm�e ! ------ /!\\");


			return false;
		}
		if(selectedChara.isAPossibleMove(destination) == false) { // verify if the required move can be done

			player.displayInfo("/!\\ ------ Ce mouvement est impossible pour un " + selectedChara.getName() + " ------ /!\\");

			return false;
		}
		if(destination.getCharacter() != null) { // verify if there is a character on the destination cell
			boolean tmp = ! destination.getCharacter().getArmy().getClientWrapper().equals(player);


			return tmp; // verify if this character is an ennemy 
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
    	ClientWrapper [] players = this.getPlayers(); 
    	int infCode = playingCharacter.getArmy().moveCharacter(playingCharacter, destination,players);
    	this.playingArmy = armies[(++round) % armiesNb];
    	for(ClientWrapper p : players) {
    		p.displayInfo(playingCharacter.getName() + " a �t� d�plac� en (" + destX + ";" + destY +")" );
    	}
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
     * Tests if the given player has won the game.
     * @param client the given player
     * @return true if the client won, false otherwise
     */
    public boolean isWinner(ClientWrapper client) {
    	if(armies[0].getClientWrapper().equals(client)) {
    		return armies[1].isEmpty();
    	} else {
    		return armies[0].isEmpty();
    	}
    }
    
    /**
     * Tests if the given player has loose the game.
     * @param client the given player
     * @return true if the client loose, false otherwise
     */
    public boolean isLoser(ClientWrapper client) {
    	if(armies[0].getClientWrapper().equals(client)) {
    		return armies[0].isEmpty();
    	} else {
    		return armies[1].isEmpty();
    	}
    }
    
    /**
     * Tests if the minimum numbers of clients are connected
     * @return true if the number is greater than 1
     */
    public boolean isMinimumClientsConnected() {
    	return connectedClientsNb > 1;
    }
    
    /**
     * Get the board
     * @return the board
     */
    public Board getBoard() {
    	return this.board;
    }
    
    /**
     * Associates a client to an army
     */
    public void setPlayersToArmies() {    	
    	for(int i = 0; i < playersNb; i++) {				
    		armies[i].setClientWrapper(players[i]);

    		armies[i].getClientWrapper().displayText("Votre arm�e est dirig�e par le personnage " + armies[i].getKing().getSymbol());

    	}
    }
    
    /**
     * Gets the army which is supposed to play
     * @return the army
     */
    public Army getPlayingAmry() {
    	return playingArmy;
    }
    
    /**
     * Gets all the players connected to the game 
     * @return
     */
    public ClientWrapper[] getPlayers() {
    	return players;
    }

    /**
     * Gets the identification string associated with the GameManager
     * @return
     */
	public String getIdGM() {
		return idGM;
	}

	/**
	 * Sets an identification string to the GameManager
	 * @param idGM
	 */
	public void setIdGM(String idGM) {
		this.idGM = idGM;
	}
	
	/**
	 * Gets the recorded pseudos
	 * @return the pseudos
	 */
	public String[] getPseudos() {
		return this.pseudos;
	}
	
}
