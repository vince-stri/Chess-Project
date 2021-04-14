package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import server.main.ClientWrapper;
import server.model.GameManager;

/**
 * Interface of the ServerImpl object, used by RMI
 * @version 1.0
 * @author vincent acila
 */
public interface Iserver extends Remote {
	
	/**
	 * According to the given credentials, tries to login the player
	 * @param client the client requesting to connect
	 * @param password the password used to identify the client
	 * @return the token's session or '0' if the identification failed
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws SQLException raised if the database cannot be requested
	 */
	public String login(iClient client, String password) throws RemoteException, SQLException;
	
	/**
	 * Disconnect a client : delete the token in the database.
	 * @param client the client who wants quit
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public void disconnect(iClient client) throws RemoteException, SQLException;
	
	/**
	 * Initialize a new game if there is no player in standby or add the player to an existing game.
	 * @param player who launched a game
	 * @return GameManagerID (String) that refers to the GM joined or the GM created.  
	 * @throws RemoteException
	 */
	public String startMatchMaking(iClient client) throws RemoteException;
	
	/**
	 * Create a duel for a player, against the opponent player specified.
	 * It's create a new game manager puts in a special queue, waiting for the opponent player.
	 * @param player
	 * @param opponentPlayer
	 * @return GameManager ID
	 * @throws RemoteException
	 */
	public String startDuel(iClient client, String opponentPlayer) throws RemoteException;
	
	/**
	 * Allows to a player to join a duel create by an other player
	 * @param player
	 * @return GameManager ID or null if there is no game available
	 * @throws RemoteException
	 */
	public String joinDuel(iClient client) throws RemoteException;
	
	/**
	 * 
	 * Checks if the user already exists in the database with the given pseudo
	 * @param pseudo
	 * @return 1 for true or 0 for false
	 * @throws SQLException
	 * @throws RemoteException
	 */
	public int checks_user(String pseudo) throws SQLException, RemoteException;
	
	/**
	 * Create a new user in the database
	 * @param pseudo
	 * @param password
	 * @return 1 : the user is created
	 * 			0 : Error occurred
	 * @throws RemoteException
	 * @throws SQLException
	 */
	public int register(String pseudo, String password) throws RemoteException, SQLException;
	
	/**
	 * Allows the client to play a move
	 * @param source the source cell of the move
	 * @param destination the destination cell of the move
	 * @param GMId the string identifying the GameManager
	 * @param client the client sending the request
	 * @return An information code.
	 * 		-1 : It doesn't belong to the player to play.
	 * 		x : The code returned by GameManager.playMove()
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public int playMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException;
	
	/**
	 * Tests if one the players won the game
	 * @param GMId the string identifying the GameManager
	 * @param client the client sending the request
	 * @return An information code.
	 * 		-1 : the game is still running
	 * 		0 : the client who sent the request won
	 * 		1 : the client who sent the request loose 
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public int isGameOver(String GMId, iClient client) throws RemoteException, NullPointerException;
	
	/**
	 * Tests if the move wanted by the client is valid
	 * @param source the source cell of the move
	 * @param destination the destination cell of the move
	 * @param GMId the string identifying the GameManager
	 * @param client the client sending the request
	 * @return An information code.
	 * 		-1 : It doesn't belong to the player to play.
	 * 		0 : The move is valid
	 * 		1 : The move is unvalid
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public int isAGoodMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException;
	
	/**
	 * Tests if the number of players is enough to be able to play
	 * @param GMId the string identifying the GameManager
	 * @return true or false
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public boolean minimumPlayersAreConnected(String GMId) throws RemoteException, NullPointerException;
	
	/**
	 * Requests the server to save the game
	 * @param GMId the string identifying the GameManager
	 * @param client the client sending the request
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public void save(String GMId, iClient client) throws RemoteException, NullPointerException;
	
	/**
	 * Allows the client, or the system, to send a message to all the other players
	 * @param GMId the string identifying the GameManager
	 * @param send the client sending the request
	 * @param msg the message to send
	 * @param systemMessage switch telling if the message comes from a player or from the server
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public void sendMessage(String GMId, iClient sender, String msg, boolean systemMessage) throws RemoteException, NullPointerException;
	
	/**
	 * Removes the client from a game
	 * @param GMId the string identifying the GameManager
	 * @param client the client sending the request
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws NullPointerException raised if the GameManager object doesn't exist. Can occurred if a player left the game.
	 */
	public void clientQuit(String GMId, iClient client) throws RemoteException, NullPointerException;
	
	/**
	 * Load a game previously saved
	 * @param client the client loading the game
	 * @return true if the gale has been loaded correctly
	 * @throws RemoteException raised during connection issue by RMI
	 */
	public String load(iClient client) throws RemoteException;
}
