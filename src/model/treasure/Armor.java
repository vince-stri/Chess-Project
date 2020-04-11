package model.treasure;

import model.character.Character;

public class Armor extends Treasure {

	private int armorValue = 20;
	
	public Armor() {
		super(TreasureType.ARMOR);
	}
	
	@Override
	public void equipTreasure(Character character) {
		character.addArmor(armorValue);
		
	}
}
