package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import server.main.ClientWrapper;
import server.model.GameManager;

/**
 * Interface of the ServerImpl object, used by RMI
 * @version 1.0
 * @author enzo moretto
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
	
	public void disconnect(iClient client) throws RemoteException, SQLException;
	public String startMatchMaking(iClient client) throws RemoteException;
	public String startDuel(iClient client, String opponentPlayer) throws RemoteException;
	public String joinDuel(iClient client) throws RemoteException;
	public int checks_user(String pseudo) throws SQLException, RemoteException;
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
}
