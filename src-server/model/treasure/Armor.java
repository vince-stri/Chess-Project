package model.treasure;

import model.character.Character;

/**
 * Class corresponding to an Armor inherited from the Treasure class
 * @version 1.0
 * @author Hugo BONNET
 */
public class Armor extends Treasure {

	/**
	 * The armor value that will be added to the character
	 */
	private int armorValue = 20;
	
	/**
	 * {@inheritDoc}
	 */
	public Armor() {
		super(TreasureType.ARMOR);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void equipTreasure(Character character) {
		character.addArmor(armorValue);
		
	}
}
