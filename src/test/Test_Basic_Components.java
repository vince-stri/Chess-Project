package test;

import model.*;
import model.board.BoardShape;

public class Test_Basic_Components {

	public static void main(String[] args) {
		GameManager gm = new GameManager(BoardShape.CHESS);
		gm.setUpBattle();
		Army armies[] = gm.getArmies();
		gm.playARound(armies[0]);
	}

}
