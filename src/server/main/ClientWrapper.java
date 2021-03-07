package server.main;

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
	
	public boolean wantToSave() {
		String ret = null;
		do {
			client.postMsg("Want to save? [yes: 'y' or no: 'n']");
			ret = client.getInfo();
		} while(ret != "y" && ret != "n");
		return ret == "y";
	}
	
	public void displayBoard(Board board) {
		client.postBoard(board);
	}
	
	public void displayText(String msg) {
		client.postMsg(msg);
	}
	
	public Character getCharacterToMove(ArrayList<Character> fighters) {
		Character chara = null;
		return chara;
	}
	
	public Cell getRecquiredCell(Board board) {
		Cell cell = null;
		return cell;
	}
}
