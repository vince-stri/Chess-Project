package serveur.model.test;

import client.view.Journal;
import serveur.model.GameManager;
import serveur.model.board.BoardShape;

public class Test_Basic_Components {

	public static void main(String[] args) {
		GameManager gm = new GameManager(BoardShape.CHESS, "saves/allGame");
		gm.setUpGame();
		gm.startGame();
		Journal.displayText("--- End of the game ---");
	}

}
