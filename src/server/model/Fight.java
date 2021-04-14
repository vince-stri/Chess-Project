package server.model;

import client.view.Journal;
import server.main.ClientWrapper;
import server.model.character.Character;
import server.model.treasure.Armor;
import server.model.treasure.Spell;
import server.model.treasure.Treasure;
import server.model.treasure.Weapon;

/**
 * The Fight class to make two characters fight 
 * @version 1.0
 * @author Hugo BONNET
 */
public class Fight {
	
	/**
	 * The treasure that will be given to the winner
	 */
    private Treasure treasure;
    
    /**
     * The character who wants to come on the cell
     */
    private Character challenger;
    
    /**
     * The character who is on the attacked cell
     */
    private Character challenged;
    
    /**
     * The dice giving random numbers
     */
    private Dice dice;
    
    /**
     * Constructor of Fight
     * @param challenger the attacker character
     * @param challenged the attacked character
     */
    public Fight(Character challenger, Character challenged) {
    	this.challenger = challenger;
    	this.challenged = challenged;
    	this.dice = new Dice();
    	int diceValue = dice.roll(1, 3);
    	
    	/*
    	 * According to the the dice, the reward will not be the same
    	 */
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
    
    /**
     * Start the fight between the players
     * @return true if the attacker won the fight, false if not
     */
    public boolean startFight(ClientWrapper[] players) {
    	boolean challengerDead = false;
    	boolean challengedDead = false;
    	
    	for(ClientWrapper player : players) {
    		player.displayInfo(challenger.getName() + " attaque " + challenged.getName() + " ! LET'S FIGHT !");
    	}
    	
    	while(!challengerDead && !challengedDead) {
    		/*
    		 * The challenger has more chance to strike damages
    		 */
    		if(dice.roll() > 3) {
    			for(ClientWrapper player : players) {
    				player.displayInfo(challenger.getName() + " deals " + challenged.getDamagePoints() + " damages to " + challenged.getName() );
    			}
    			challengedDead = challenger.strike(challenged);
    		} else {
    			for(ClientWrapper player : players) {
    				player.displayInfo(challenged.getName() + " deals " + challenger.getDamagePoints() + " damages to " + challenger.getName() );
    			}
    			challengerDead = challenged.strike(challenger);
    		}
    	}
    	if(challengerDead) {
    		for(ClientWrapper player : players) {
    			player.displayInfo(challenger.getName() + " has been killed by " + challenged.getName());
    		}
    		challenger.kill();
    		challenged.heal();
    		challenged.addTreasure(treasure);
    		challenged.equipTreasure(treasure);
    		return false;
    	} else {
    		for(ClientWrapper player : players) {
    			player.displayInfo(challenged.getName() + " has been killed by " + challenger.getName() + "\n");
    		}
    		challenged.kill();
    		challenger.heal();
    		challenger.addTreasure(treasure);
    		challenger.equipTreasure(treasure);
    		return true;
    	}
    }

}
