package model.treasure;

import model.character.Character;

public abstract class Treasure {
    @SuppressWarnings("unused")
	private TreasureType treasureType;

    protected Treasure(TreasureType treasureType) {
    	this.treasureType = treasureType;
    }
    
    public abstract void equipTreasure(Character character);

}
