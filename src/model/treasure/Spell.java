package model.treasure;

import model.character.Character;

public class Spell extends Treasure {

	private int damages = 10;
	
	public Spell() {
		super(TreasureType.SPELL);
	}
	
	@Override
	public void equipTreasure(Character character) {
		character.addDamage(damages);
		
	}
}
