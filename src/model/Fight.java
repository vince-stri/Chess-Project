package model;



public class Fight {
    private FightState fightState;
    private Treasure treasure;
    private Character challenger;
    private Character challenged;
    private Dice dice;
    
    public Fight(Character challenger, Character challenged) {
    	this.challenger = challenger;
    	this.challenged = challenged;
    	this.dice = new Dice();
    	int diceValue = dice.roll(1, 3);
    	switch (diceValue) {
			case 1:
				treasure = new Armor();
			break;
			case 2:
				treasure = new Spell();
			break;
			default:
				treasure = new Weapon();
			break;
		}
    }
    
    public boolean startFight() {
    	boolean challengerDead = false;
    	boolean challengedDead = false;
    	while(challengerDead && challengedDead) {
    		if(dice.roll() > 2) {
    			challengedDead = challenger.strike(challenged);
    		} else {
    			challengerDead = challenged.strike(challenger);
    		}
    	}
    	return true;
    }

}
