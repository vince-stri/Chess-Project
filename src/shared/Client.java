package shared;


import java.io.Serializable;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.model.iClient;
import client.view.Journal;
import server.model.board.*;

public class Client extends UnicastRemoteObject implements iClient {
	
	private int idAccount;
	private String pseudo;
	private String token;
	
	
	public Client(int idAccount, String pseudo, String token) throws RemoteException {
		this.SetIdAccount(idAccount);
		this.SetPseudo(pseudo);
		this.SetToken(token);
	}


	public int GetIdAccount() {
		return idAccount;
	}


	public void SetIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}


	public String GetPseudo() {
		return pseudo;
	}


	public void SetPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String GetToken() {
		return token;
	}


	public void SetToken(String token) {
		this.token = token;
	}
	
	public void PostMsg(String message) throws RemoteException {
		System.out.println("[JOUEUR] "+message);
	}
	
	public String GetInfo() throws RemoteException {
		return null;
	}
	
	public void PostBoard(Board board) throws RemoteException {
		Journal.displayBoard(board);
	}
	
	public void PostInfo(String info) throws RemoteException {
		System.out.println("[SERVEUR] "+info);
	}


	
}
