package shared;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.model.iClient;
import client.view.Journal;
import server.model.board.*;

public class Client extends UnicastRemoteObject implements iClient, Serializable {
	
	private int idAccount;
	private String pseudo;
	private String token;
	private Journal journal;
	
	public Client(int idAccount, String pseudo, String token, Journal journal) throws RemoteException {
		this.SetIdAccount(idAccount);
		this.SetPseudo(pseudo);
		this.SetToken(token);
		this.journal = journal;
	}


	public int GetIdAccount() throws RemoteException {
		return idAccount;
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
	
	public String GetInfo() throws RemoteException {
		return null;
	}
	
	public void PostBoard(Board board) throws RemoteException {
		journal.displayBoard(board);
	}
	
	public void PostInfo(String info) throws RemoteException {
		journal.displayText("[SERVEUR] "+info);
	}


	
}
