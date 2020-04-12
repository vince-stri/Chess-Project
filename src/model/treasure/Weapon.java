package model.treasure;

import model.character.Character;

public class Weapon extends Treasure {

	private int damages = 10;
	
	public Weapon() {
		super(TreasureType.WEAPON);
	}
	
	
	@Override
	public void equipTreasure(Character character) {
		character.addDamage(damages);
	}
}
