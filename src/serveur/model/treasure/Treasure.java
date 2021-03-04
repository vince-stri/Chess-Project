package serveur.model.treasure;



/**
 * Abstract class defining effects that will be given to a Character
 * @author hugo bonnet
 * @version 1.0
 */
public abstract class Treasure {
	
	/**
	 * The type of treasure
	 */
    @SuppressWarnings("unused")
	private TreasureType treasureType;

    /**
     * Constructor of a Treasure
     * @param treasureType the type of treasure
     */
    protected Treasure(TreasureType treasureType) {
    	this.treasureType = treasureType;
    }
    
    /**
     * Affects to the given character effects corresponding to the treasure type
     * @param character the character receiving the effects of the treasure
     */
    public abstract void equipTreasure(Character character);

}
