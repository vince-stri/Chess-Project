package client.view;

import server.model.board.Board;

public abstract class Journal {
	
	public abstract void displayText(String text);
	
	public abstract void displayTextError(String text);
	
	public abstract void displayBoard(Board board);
	
	public abstract void clearTerminal();
	
}
