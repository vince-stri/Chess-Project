package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import server.model.board.Board;

/**
 * Interface of the Client object, used by RMI
 * @version 1.0
 * @author enzo moretto
 */
public interface iClient extends Remote {
	
	public int GetIdAccount() throws RemoteException;
	public void SetIdAccount(int idAccount) throws RemoteException;

	/**
	 * Gives the client's user name
	 * @return the user name
	 */
	public String GetPseudo() throws RemoteException;

	/**
	 * Allows the client to change its user name
	 * @param pseudo the new user name
	 */
	public void SetPseudo(String pseudo) throws RemoteException;

	/**
	 * Get the client's token
	 * @return the token
	 */
	public String GetToken() throws RemoteException;

	/**
	 * Allows the server to change the client's token
	 * @param token the newly generated token
	 */
	public void SetToken(String token) throws RemoteException;
	
	/**
	 * Gives a string to display to the client, sent by another player
	 * @param message the string to display
	 */
	public void PostMsg(String message) throws RemoteException;
	
	/**
	 * Gives the board to display to the client
	 * @param board the board to displau
	 */
	public void PostBoard(Board board) throws RemoteException;
	
	/**
	 * Gives a string to display to the client, sent by the server
	 * @param message the string to display
	 */
	public void PostInfo(String info) throws RemoteException;

	/**
	 * Tests equality between two clients, based upon their token (universally unique)
	 * @param client the client to be compared with
	 * @return true if the tokens match, false otherwise
	 */
	public boolean equals(iClient client) throws RemoteException;
	
	/**
	 * Get the GMId saved by the client
	 * @return the GMId saved
	 */
	public String getGMId() throws RemoteException;
	
	/**
	 * Set a saved GMId
	 * @param GMId the saved GMId
	 */
	public void setGMId(String GMId) throws RemoteException;
}
