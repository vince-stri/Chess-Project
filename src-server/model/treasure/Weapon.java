package model.treasure;

import model.character.Character;

/**
 * Class corresponding to a Weapon inherited from the Treasure class
 * @version 1.0
 * @author Hugo BONNET
 */
public class Weapon extends Treasure {

	/**
	 * The damage value that will be added to the character
	 */
	private int damages = 10;
	
	/**
	 * {@inheritDoc}
	 */
	public Weapon() {
		super(TreasureType.WEAPON);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void equipTreasure(Character character) {
		character.addDamage(damages);
	}
}
