package client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.stream.events.Characters;

import client.view.Journal;
import server.model.board.Board;


public interface iClient extends Remote{
	public int GetIdAccount();
	public void SetIdAccount(int idAccount);
	public String GetPseudo();
	public void SetPseudo(String pseudo);
	public String GetToken();
	public void SetToken(String token);
	public void PostMsg(String message) throws RemoteException;
	public String GetInfo() throws RemoteException;
	public void PostBoard(Board board) throws RemoteException;
	public void PostInfo(String info) throws RemoteException;
}
