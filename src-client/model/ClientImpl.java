package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.board.Board;
import view.Journal;

public class ClientImpl extends UnicastRemoteObject implements iClient {

	private Client Client;
	protected ClientImpl() throws RemoteException {
		super();
	}

	@Override
	public void postBoard(Board board) throws RemoteException {
		Journal.displayBoard(board);
	}

	@Override
	public void PostMsg(String message) throws RemoteException {
			System.out.println("[JOUEUR] "+message);
	}

	@Override
	public void PostInfo(String info) throws RemoteException {
			System.out.println("[SERVEUR] "+info);
	}

	@Override
	public String getInfo() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCharacterToMove(ArrayList<Character> fightersAlive) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
