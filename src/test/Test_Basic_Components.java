package test;

import model.*;

public class Test_Basic_Components {

	public static void main(String[] args) {
		GameManager gm = new GameManager(BoardShape.CLASSIC, 2, 16);
		gm.setUpBattle();
		Army armies[] = gm.getArmies();
		gm.playARound(armies[0]);
		
	}

}
