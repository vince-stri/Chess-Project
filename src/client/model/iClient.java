package client.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.xml.stream.events.Characters;

import server.model.board.Board;


public interface iClient extends Remote{
	void postBoard(Board board) throws RemoteException;
	void PostMsg(String message) throws RemoteException;
	void PostInfo(String info) throws RemoteException;
	String getInfo() throws RemoteException;
	String getCharacterToMove(ArrayList<Characters> fightersAlive) throws RemoteException;
}
