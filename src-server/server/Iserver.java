package server;

import java.rmi.*;
import java.sql.SQLException;

import model.Client;


public interface Iserver extends Remote {
	public String login(Client client, String password) throws RemoteException, SQLException;
	public String disconnect(Client client) throws RemoteException, SQLException;
	public String startMatchMaking() throws RemoteException;
	public String startDuel() throws RemoteException;
	public int register(String pseudo, String password) throws RemoteException, SQLException;
	
}
