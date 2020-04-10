package model.character;

import java.util.ArrayList;
import java.util.List;

import model.Army;
import model.Cell;
import model.Coordinates;
import model.Fight;
import model.treasure.Treasure;

public class Character {
	
    private int healthPoints;
    private int maxHP;
    private int damagePoints;
    private int armor;
    private int maxArmor;
    private CharacterType type;
    private String name;
    private Cell cell;
    private Army army;
    private Coordinates[] moves;
    
    public List<Treasure> treasures = new ArrayList<Treasure> ();
    public List<Coordinates> coordinates = new ArrayList<Coordinates> (); // ?
    
    public Character(int maxHP, String name, Cell cell, Army army, int maxArmor, CharacterType type) {
    	this.maxHP = maxHP;
    	this.healthPoints = maxHP;
    	this.name = name;
    	this.cell = cell;
    	this.army = army;
    	this.maxArmor = maxArmor;
    	this.armor = 0;
    	this.type = type;
    }
    
    private void goToCell(Cell destination) {
    	destination.setCharacter(this);
    	this.cell = destination;
    }
    
    public int goTo(Cell destination) {
    	if(!destination.isEmpty()) {
    		Character challenged = destination.getCharacter();
    		Fight fight = new Fight(this, challenged);
    		if(fight.startFight()) {
    			goToCell(destination);
    			return 1;
    		} else {
    			return 2;
    		}
    	} else {
    		goToCell(destination);
    		return 0;
    	}
    }

    public int addTreasure(Treasure reward) {
    	treasures.add(reward);
    	return 0;
    }
    
    public int equipTreasure(Treasure toEquip) {
    	if(treasures.contains(toEquip)) {
    		toEquip.equipTreasure(this);
    		treasures.remove(toEquip);
    		return 0;
    	} else {
    		return 1;
    	}
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
    	return healthPoints <= 0;
    }

    public boolean isDead() {
    	return healthPoints <= 0;
    }

    public void addHP(int hp) {
    }

    public void addArmor(int armor) {
    	this.armor += armor;
    	if(armor > maxArmor) {
    		armor = maxArmor;
    	}
    }

    public void addDamage(int damages) {
    }

    public void kill() {
    	army.rmCharacter(this);
    }

    public void heal() {
    	this.addHP(maxHP);
    }
    
    public Army getArmy() {
    	return army;
    }
    
    public String toString() {
    	return name + " from " + army;
    }
    
    public Cell getCell() {
    	return cell;
    }

}
