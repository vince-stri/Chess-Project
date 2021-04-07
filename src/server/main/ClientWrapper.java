package server.main;

import java.rmi.RemoteException;
import java.util.ArrayList;

import server.model.Army;
import server.model.board.Board;
import server.model.board.Cell;
import server.model.character.Character;
import shared.Client;

public class ClientWrapper {
	
	private Client client;
	
	public ClientWrapper(Client client) {
		this.client = client;
	}
	
	public boolean wantToSave() throws RemoteException {
		String ret = null;
		do {
			client.PostMsg("Want to save? [yes: 'y' or no: 'n']");
			ret = client.GetInfo();
		} while(ret != "y" && ret != "n");
		return ret == "y";
	}
	
	public void displayBoard(Board board) throws RemoteException {
		client.PostBoard(board);
	}
	
	public void displayText(String msg) throws RemoteException {
		client.PostMsg(msg);
	}
	
	public Character getCharacterToMove(ArrayList<Character> fighters) {
		Character chara = null;
		return chara;
	}
	
	public Cell getRecquiredCell(Board board) {
		Cell cell = null;
		return cell;
	}
	
	public Client getClient() {
		return client;
	}
}
