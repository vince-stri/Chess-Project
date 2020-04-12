package test;

import model.*;
import model.board.BoardShape;
import view.Journal;

public class Test_Basic_Components {

	public static void main(String[] args) {
		GameManager gm = new GameManager(BoardShape.CHESS, "saves/allGame");
		//gm.setUpBattle();
		//gm.save();
		//gm.test();
		gm.load();
		gm.test_dump();
		//gm.startGame();
		Journal.displayText("--- End of the game ---");
	}

}
