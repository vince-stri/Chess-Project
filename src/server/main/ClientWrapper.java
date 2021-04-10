package server.main;

import java.rmi.RemoteException;
import java.util.ArrayList;

import server.model.board.Board;
import server.model.board.Cell;
import server.model.character.Character;
import shared.iClient;
public class ClientWrapper {
	
	private iClient client;
	
	public ClientWrapper(iClient client2) {
		this.client = client2;
	}
	
	public boolean wantToSave() {
		String ret = null;
		do {
			try {				
				client.PostMsg("Want to save? [yes: 'y' or no: 'n']");
				ret = client.GetInfo();
			} catch(RemoteException e) {
				e.printStackTrace();
			}
		} while(ret != "y" && ret != "n");
		return ret == "y";
	}
	
	public void displayBoard(Board board) {
		try {
			client.PostBoard(board);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void displayText(String msg) {
		try {
			client.PostMsg(msg);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public Character getCharacterToMove(ArrayList<Character> fighters) {
		Character chara = null;
		return chara;
	}
	
	public Cell getRecquiredCell(Board board) {
		Cell cell = null;
		return cell;
	}
	
	public iClient getClient() {
		return client;
	}
	
	public boolean equals(ClientWrapper client) {
		try {
			return this.client.equals(client.getClient());
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
}
