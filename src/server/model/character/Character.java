package server.model.character;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import server.main.ClientWrapper;
import server.model.Army;
import server.model.Coordinates;
import server.model.Fight;
import server.model.board.Board;
import server.model.board.Cell;
import server.model.treasure.Treasure;



/**
 * It corresponds to the tokens that will be moved on the board during the game.
 * @author axel gauthier
 * @version 1.0
*/
public abstract class Character implements Serializable{
	/**
	 * The current health points of the character
	 */
    protected int healthPoints;
    
    /**
     * The maximum health points the character can have
     */
    protected int maxHP;
    
    /**
     * The damage points of the character
     */
    protected int damagePoints;
    
    /**
     * The current armor points of the character
     */
    protected int armor;
    
    /**
     * The maximum armor points the character can have
     */
    protected int maxArmor;
    
    /**
     * The character name
     */
    protected String name;
    
    /**
     * The coordinates on the board of the character
     */
    protected Coordinates coords;
    
    /**
     * The symbol representing the character on a terminal display
     */
    private char symbol;
    
    /**
     * The board where the character is moving on
     */
    protected transient Board board;
    
    /**
     * The army to which the character belongs
     */
    protected transient Army army;
    
    /**
     * The list of treasures the character owns
     */
    public List<Treasure> treasures = new ArrayList<Treasure> ();
    
    /**
     * Constructor for an usual character 
     * @param maxHP the maximum health points
     * @param name the name
     * @param coords the coordinates on the board
     * @param army the army 
     * @param maxArmor the maximum armor points
     * @param damagePoints the damage points
     * @param board the board where the character is playing on
     */
    public Character(int maxHP, String name, Coordinates coords, Army army, int maxArmor, int damagePoints, Board board, char symbol) {
    	this.maxHP = maxHP;
    	this.healthPoints = maxHP;
    	this.name = name;
    	this.army = army;
    	this.maxArmor = maxArmor;
    	this.armor = 0;
    	this.damagePoints = damagePoints;
    	this.coords = coords;
    	this.board = board;
    	this.symbol = symbol;
    }
    
    /**
     * Move the character to the required cell without any verification
     * @param destination the destination cell
     */
    private void goToCell(Cell destination) {
    	move();
    	board.getACell(coords).setCharacter(null);
    	destination.setCharacter(this);
    	this.coords = destination.getCoordinates();
    }
    
    /**
     * Try to move the character to the destination cell.
     * If a fight has to happen, the character will firstly try to win the fight and then,
     * move on the cell. Otherwise it will just go 
     * @param destination the destination cell
     * @param haveToFight the information that a fight has to start or not
     * @return a return code:
     * 	0 - The character didn't have to fight and went directly on the destination
     *  1 - A fight has started and the attacker won
     *  2 - A fight has started and the attacker loose
     */
    public int goTo(Cell destination, boolean haveToFight,ClientWrapper[] players) {
    	if(haveToFight) {
    		Character challenged = destination.getCharacter();
    		Fight fight = new Fight(this, challenged);
    		if(fight.startFight(players)) {
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

    /**
     * Add a treasure to the character list
     * @param reward the treasure to add
     */
    public void addTreasure(Treasure reward) {
    	treasures.add(reward);
    }
    
    /**
     * Equip the wanted treasure. It means that this treasure will be taken of the treasure list
     * and the character will receive the effects contained inside the treasure
     * @param toEquip the treasure to equip
     * @return a return code:
     * 	0 - The treasure has successfully been equipped
     *  1 - The treasure is not inside the treasure list of the character
     */
    public int equipTreasure(Treasure toEquip) {
    	if(treasures.contains(toEquip)) {
    		toEquip.equipTreasure(this);
    		treasures.remove(toEquip);
    		return 0;
    	} else {
    		return 1;
    	}
    }

    /**
     * A character will attack another one.
     * @param attacked the attacked character
     * @return the return code of the takeDamages() methods
     */
    public boolean strike(Character attacked) {
    	return attacked.takeDamages(this.damagePoints);
    }

    /**
     * Remove the damages point received to the character.
     * The result of the action depends on the presence or not of armor.
     * @param damages the damages points received
     * @return true if the character is considered as dead and false if not
     */
    public boolean takeDamages(int damages) {
    	//Journal.displayText(" DAMAGES " + damages);
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

    /**
     * Return if a character is considered dead or not
     * @return true if the character is considered as dead and false if not
     */
    public boolean isDead() {
    	return healthPoints <= 0;
    }

    /**
     * Add health points to the character.
     * Could not add more health points than defined in the maxHP attribute 
     * @param hp the health points to add
     */
    public void addHP(int hp) {
    	healthPoints += hp;
    	if(healthPoints > maxHP) {
    		healthPoints = maxHP;
    	}
    }

    /**
     * Add armor points to the character.
     * Could not add more armor points than defined in the maxArmor attribute
     * @param armor the armor points to add
     */
    public void addArmor(int armor) {
    	this.armor += armor;
    	if(armor > maxArmor) {
    		armor = maxArmor;
    	}
    }

    /**
     * Add damage points to the character 
     * @param damages the damage points to add 
     */
    public void addDamage(int damages) {
    	this.damagePoints += damages;
    }

    /**
     * Remove the character from its army
     */
    public void kill() {
    	army.rmCharacter(this);
    }

    /**
     * Health completely the character
     */
    public void heal() {
    	this.addHP(maxHP);
    }
    
    /**
     * Getter of army
     * @return the required army
     */
    public Army getArmy() {
    	return army;
    }
    
    /**
     * Setter of army
     * @param army the army to set
     */
    public void setArmy(Army army) {
    	this.army = army;
    }
    
    /**
     * Getter of the character cell
     * @param board the board where the character is moving on
     * @return the required cell
     */
    public Cell getCell(Board board) {
    	return board.getACell(coords);
    }
    
    /**
     * Getter of name
     * @return the required name
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Getter of healthPoints
     * @return the required health points
     */
    public int getHealthPoints() {
    	return healthPoints;
    }
    
    /**
     * Getter of coordinates
     * @return the required coordinates
     */
    public Coordinates getCoordinates() {
    	return coords;
    }
    /**
     * Getter to damagePoints
     * @return the required damage
     */
    public int getDamagePoints() {
		return damagePoints;
	}
    
    /**
     * Getter of symbol
     * @return the required symbol
     */
    public char getSymbol() {
    	return symbol;
    }
    
    /**
     * Setter of board
     * @param board the board to set
     */
    public void setBoard(Board board) {
    	this.board = board;
    }
    
    /**
     * Return if the destination cell is reachable according to the character type
     * @param destination the destination cell
     * @return true if the character can go on the cell or not
     */
    public abstract boolean isAPossibleMove(Cell destination);

    /**
     * Give a description of the character
     * @return the character name, its type, the army which its belongs and its coordinates
     */
    public abstract String dumpCharacter();
    
    /**
     * Move the character
     */
    public abstract void move();
}
