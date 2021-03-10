package server.main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import shared.Client;

public interface Iserver extends Remote {
	public String login(Client client, String password) throws RemoteException, SQLException;
	public String disconnect(Client client) throws RemoteException, SQLException;
	public String startMatchMaking(Client client) throws RemoteException;
	public String startDuel(Client client) throws RemoteException;
	public int register(String pseudo, String password) throws RemoteException, SQLException;
	
	//MoveChar
	//Is
	
}
