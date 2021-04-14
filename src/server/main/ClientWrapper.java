package server.main;

import java.io.Serializable;
import java.rmi.RemoteException;
import server.model.board.Board;
import shared.iClient;

/**
 * Gives a controlled interface to the server to communicate with the client
 * @version 1.0
 * @author axel gauthier
 */
public class ClientWrapper implements Serializable {
	
	/**
	 * Reference to the client to call operations
	 */
	private iClient client;
	
	public ClientWrapper(iClient client) {
		this.client = client;
	}

	/**
	 * Tells to the client to display the given board
	 * @param board the board to display
	 */
	public void displayBoard(Board board) {
		try {
			client.PostBoard(board);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Display a message coming from another player
	 * @param msg the message to diplay
	 */
	public void displayText(String msg) {
		try {
			client.PostMsg(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Display a message coming from the system
	 * @param msg the message to display
	 */
	public void displayInfo(String msg) {
		try {
			client.PostInfo(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the client attribute
	 * @return iClient object
	 */
	public iClient getClient() {
		return client;
	}
	
	/**
	 * Tests if two ClientWrapper are equals, based upon the equality of their iClient attribute
	 * @param client the second ClientWrapper to compare
	 * @return true if the ClientWrapper are equals, false otherwise
	 */
	public boolean equals(ClientWrapper client) {
		try {
			return this.client.equals(client.getClient());
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
}
