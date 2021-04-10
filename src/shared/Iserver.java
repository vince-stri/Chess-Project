package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

import server.main.ClientWrapper;
import server.model.GameManager;

public interface Iserver extends Remote {
	public String login(iClient client, String password) throws RemoteException, SQLException;
	public String disconnect(iClient client) throws RemoteException, SQLException;
	public String startMatchMaking(iClient client) throws RemoteException;
	public String startDuel(iClient client) throws RemoteException;
	public int register(String pseudo, String password) throws RemoteException, SQLException;
	public int playMove(int source, int destination, String GMId, iClient client) throws RemoteException;
	public int isGameOver(String GMId, iClient client) throws RemoteException;
	public int checks_user(String pseudo) throws SQLException, RemoteException;
	public boolean isAGoodMove(int source, int destination, String GMId, iClient client) throws RemoteException;
	public boolean minimumPlayersAreConnected(String GMId) throws RemoteException;
	public void save(String GMId, iClient client) throws RemoteException;
	public void sendMessage(String GMId, iClient sender, String msg) throws RemoteException;
}
