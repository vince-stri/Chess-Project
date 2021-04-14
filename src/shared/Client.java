package shared;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.view.Journal;
import server.model.board.*;

/**
 * Used by the server to call remote operations on the client
 * @version 1.0
 * @author enzo moretto
 */
public class Client extends UnicastRemoteObject implements iClient, Serializable {
	
	private int idAccount;
	
	/**
	 * The player's user name
	 */
	private String pseudo;
	
	/**
	 * Token identifying the client universally
	 */
	private String token;
	
	/**
	 * The journal displaying information to the player
	 */
	private Journal journal;
	
	/**
	 * Identifies the one game the client is able to save
	 */
	private String GMId;
	
	public Client(int idAccount, String pseudo, String token, Journal journal) throws RemoteException {
		this.SetIdAccount(idAccount);
		this.SetPseudo(pseudo);
		this.SetToken(token);
		this.journal = journal;
	}

	public int GetIdAccount() throws RemoteException {
		return idAccount;
	}

	public void setGMId(String GMId) throws RemoteException {
		this.GMId = GMId;
	}
	
	public String getGMId() throws RemoteException {
		return GMId;
	}

	public void SetIdAccount(int idAccount) throws RemoteException {
		this.idAccount = idAccount;
	}

	public String GetPseudo() throws RemoteException {
		return pseudo;
	}

	public void SetPseudo(String pseudo) throws RemoteException {
		this.pseudo = pseudo;
	}

	public String GetToken() throws RemoteException {
		return token;
	}

	public void SetToken(String token) throws RemoteException {
		this.token = token;
	}

	public void PostMsg(String message) throws RemoteException {
		journal.displayText("[JOUEUR] "+message);
	}

	public void PostBoard(Board board) throws RemoteException {
		journal.displayBoard(board);
	}

	public void PostInfo(String info) throws RemoteException {
		journal.displayText("[SERVEUR] "+info);
	}

	public boolean equals(iClient client) throws RemoteException {
		return token.equals(client.GetToken());
	}

}
