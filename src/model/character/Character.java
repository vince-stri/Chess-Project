package model.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import model.Army;
import model.Coordinates;
import model.Fight;
import model.board.Board;
import model.board.Cell;
import model.treasure.Treasure;
import view.Journal;

@SuppressWarnings("serial")
public abstract class Character implements Serializable{
	
    protected int healthPoints;
    protected int maxHP;
    protected int damagePoints;
    protected int armor;
    protected int maxArmor;
    protected Board board;
    protected String name;
    protected Coordinates coords;
    protected transient Army army;
    
    public List<Treasure> treasures = new ArrayList<Treasure> ();
    
    public Character(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board) {
    	this.maxHP = maxHP;
    	this.healthPoints = maxHP;
    	this.name = name;
    	this.army = army;
    	this.maxArmor = maxArmor;
    	this.armor = 0;
    	this.damagePoints = damagePoints;
    	this.coords = coords;
    	this.board = board;
    }
    
    private void goToCell(Cell destination) {
    	destination.setCharacter(this);
    	this.coords = destination.getCoordinates();
    }
    
    public int goTo(Cell destination, boolean haveToFight) {
    	if(haveToFight) {
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
    	Journal.displayText(" DAMAGES " + damages);
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
    	healthPoints += hp;
    	if(healthPoints > maxHP) {
    		healthPoints = maxHP;
    	}
    }

    public void addArmor(int armor) {
    	this.armor += armor;
    	if(armor > maxArmor) {
    		armor = maxArmor;
    	}
    }

    public void addDamage(int damages) {
    	this.damagePoints += damages;
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
    
    public void setArmy(Army army) {
    	this.army = army;
    }
    
    public String toString() {
    	return name + " from " + army;
    }
    
    public Cell getCell(Board board) {
    	return board.getACell(coords);
    }
    
    public String getName() {
    	return name;
    }
    
    public String dumpCharacter() {
    	return "I'm " + name + " fighting for the " + army + " army and I'm located at " + getCoordinates();
    }
    
    public int getHealthPoints() {
    	return healthPoints;
    }
    
    public Coordinates getCoordinates() {
    	return coords;
    }
    
    public abstract boolean isAPossibleMove(Cell destination);

}
