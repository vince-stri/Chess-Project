package model;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.board.Board;

public interface iClient extends Remote{
	void postBoard(Board board) throws RemoteException;
	void PostMsg(String message) throws RemoteException;
	void PostInfo(String info) throws RemoteException;
	String getInfo() throws RemoteException;
	String getCharacterToMove(ArrayList<Characters> fightersAlive) throws RemoteException;
}