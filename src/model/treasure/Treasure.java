package model.treasure;

import model.TreasureType;
import model.character.Character;

public abstract class Treasure {
    private TreasureType treasureType;

    public abstract void equipTreasure(Character character);

}
