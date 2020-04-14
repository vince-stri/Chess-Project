package model.treasure;

import model.character.Character;

/**
 * Class corresponding to a Spell inherited from the Treasure class
 * @version 1.0
 */
public class Spell extends Treasure {

	/**
	 * The damage value that will be added to the character
	 */
	private int damages = 10;
	
	/**
	 * {@inheritDoc}
	 */
	public Spell() {
		super(TreasureType.SPELL);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void equipTreasure(Character character) {
		character.addDamage(damages);
		
	}
}
