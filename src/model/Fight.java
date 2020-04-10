package model;

import model.character.Character;
import model.treasure.Armor;
import model.treasure.Spell;
import model.treasure.Treasure;
import model.treasure.Weapon;

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
    	fightState = FightState.AVAILABLE;
    }
    
    public boolean startFight() {
    	fightState = FightState.RUNNING;
    	boolean challengerDead = false;
    	boolean challengedDead = false;
    	while(challengerDead && challengedDead) {
    		if(dice.roll() > 2) {
    			challengedDead = challenger.strike(challenged);
    		} else {
    			challengerDead = challenged.strike(challenger);
    		}
    	}
    	if(challengerDead) {
    		challenger.kill();
    		challenged.heal();
    		challenged.addTreasure(treasure);
    		challenged.equipTreasure(treasure);
    		fightState = FightState.CHALLENGEDWON;
    		return false;
    	} else {
    		challenged.kill();
    		challenger.heal();
    		challenger.addTreasure(treasure);
    		challenger.equipTreasure(treasure);
    		fightState = FightState.CHALLENGERWON;
    		return true;
    	}
    }
    
    public FightState getFightState() {
    	return fightState;
    }

}
