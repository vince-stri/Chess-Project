package model;

import java.util.ArrayList;
import java.util.List;

public class Character {
	
    private int healthPoints;
    private int maxHP;
    private int damagePoints;
    private int armor;
    private int maxArmor;
    private String name;
    private CharacterType characterType;
    public Cell cell;
    public Army army;
    private Coordinates[] moves;
    
    public List<Treasure> treasures = new ArrayList<Treasure> ();
    public List<Coordinates> coordinates = new ArrayList<Coordinates> (); // ?
    
    public Character(int maxHP, String name, CharacterType charType, Cell cell, Army army, int maxArmor) {
    	this.maxHP = maxHP;
    	this.healthPoints = maxHP;
    	this.name = name;
    	this.characterType = charType;
    	this.cell = cell;
    	this.army = army;
    	this.maxArmor = maxArmor;
    	this.armor = 0;
    	/* To complete. We must think about the Coordinates class
    	switch (charType) {
			case KNIGHT:
				moves = 
			break;
	
			default:
				
			break;
		}
		*/
    }
    
    public int goTo(Cell destination) {
    	if(!destination.isCellEmpty()) {
    		Character challenged = destination.getCharacter();
    		Fight fight = new Fight(this, challenged);
    		fight.startFight();
    	}
    }

    public void addTreasure(Treasure reward) {
    }

    public boolean strike(Character striked) {
    	return striked.takeDamages(this.damagePoints);
    }

    public boolean takeDamages(int damages) {
    	if(armor > damages) {
    		armor -= damages;
    		return false;
    	} else if(armor == 0) {
    		healthPoints -= damages;
    	} else {
    		healthPoints = damages - armor;
    		armor = 0;
    	}
    	if(healthPoints <= 0) {
    		kill();
    		return true;
    	}
    	return false;
    }

    public boolean isDead() {
    	return false;
    }

    public void addHP(int hp) {
    }

    public void addArmor(int armor) {
    }

    public void addDamage(int damages) {
    }

    public void kill() {
    	army.removeCharacter(this);
    }

    public void heal() {
    }
    
    public Army getArmy() {
    	return army;
    }
    
    public String toString() {
    	return name + " from " + army;
    }

}
