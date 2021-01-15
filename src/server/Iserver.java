package server;

import java.rmi.*;
import java.sql.SQLException;


public interface Iserver extends Remote {
	public String login(String id, String password) throws RemoteException, SQLException;
	public String disconnect() throws RemoteException;
	public String startMatchMaking() throws RemoteException;
	public String startDuel() throws RemoteException;
	public String register() throws RemoteException;
}
