package server.main;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import client.model.iClient;

public interface Iserver extends Remote {
	public String login(iClient client, String password) throws RemoteException, SQLException;
	public String disconnect(iClient client) throws RemoteException, SQLException;
	public String startMatchMaking(iClient client) throws RemoteException;
	public String startDuel(iClient client) throws RemoteException;
	public int register(String pseudo, String password) throws RemoteException, SQLException;
	public boolean isAGoodMove(int source, int destination, String GMId, iClient client) throws RemoteException;
	public int playMove(int source, int destination, String GMId, iClient client) throws RemoteException;
	public int isGameOver(String GMId, iClient client) throws RemoteException;
	public int checks_user(String pseudo) throws SQLException, RemoteException;	
}
