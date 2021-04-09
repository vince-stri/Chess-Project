package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import server.model.board.Board;


public interface iClient extends Remote {
	public int GetIdAccount() throws RemoteException;
	public void SetIdAccount(int idAccount) throws RemoteException;
	public String GetPseudo() throws RemoteException;
	public void SetPseudo(String pseudo) throws RemoteException;
	public String GetToken() throws RemoteException;
	public void SetToken(String token) throws RemoteException;
	public void PostMsg(String message) throws RemoteException;
	public String GetInfo() throws RemoteException;
	public void PostBoard(Board board) throws RemoteException;
	public void PostInfo(String info) throws RemoteException;
}
