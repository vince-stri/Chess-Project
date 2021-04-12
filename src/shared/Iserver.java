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
	public int checks_user(String pseudo) throws SQLException, RemoteException;
	public int register(String pseudo, String password) throws RemoteException, SQLException;
	public int playMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException;
	public int isGameOver(String GMId, iClient client) throws RemoteException, NullPointerException;
	public int isAGoodMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException;
	public boolean minimumPlayersAreConnected(String GMId) throws RemoteException, NullPointerException;
	public void save(String GMId, iClient client) throws RemoteException, NullPointerException;
	public void sendMessage(String GMId, iClient sender, String msg, boolean systemMessage) throws RemoteException, NullPointerException;
	public void clientQuit(String GMId, iClient client) throws RemoteException, NullPointerException;
}
