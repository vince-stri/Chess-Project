package model.treasure;

import model.character.Character;

public abstract class Treasure {
    private TreasureType treasureType;

    protected Treasure(TreasureType treasureType) {
    	this.treasureType = treasureType;
    }
    
    public abstract void equipTreasure(Character character);

}
