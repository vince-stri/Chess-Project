package server;

public class ClientWrapper {
	
	private Client client;
	
	public ClientWrapper(Client client) {
		this.client = client;
	}
	
	public boolean wantToSave() {
		String ret = null;
		do {
			client.PostMsg("Want to save? [yes: 'y' or no: 'n']");
			ret = client.getInfo();
		} while(ret != 'y' && ret != 'n')
		return ret == 'y';
	}
	
	public void displayBoard(Board board) {
		client.postBoard(board);
	}
	
	public Character getCharacterToMove(Army army) {
		Character chara = null
		return chara;
	}
	
	public Cell getRecquiredCell(Board board) {
		Cell cell = null;
		return cell;
	}
}
