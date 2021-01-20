package model;

import java.util.ArrayList;
import java.util.Iterator;
import model.board.Board;
import model.board.Cell;
import model.character.Character;
import java.io.Serializable;

/**
 * Class regrouping all the characters belonging to one side of the game
 * @author axel gauthier
 */
@SuppressWarnings("serial")
public class Army implements Serializable {
	
	/**
	 * The name of the army
	 */
    private String name;
    
    /**
     * The number of characters alive
     */
    private int fightersNb;
    
    /**
     * The board where the army is on
     */
    public transient Board board;
    
    /**
     * The list of all characters composing the army
     */
    public ArrayList<Character> fightersAlive = new ArrayList<Character>();
    
    /**
     * Constructor of Army
     * @param board the board where the army is on
     * @param name the name of the army
     */
    public Army(Board board, String name) {
    	this.board = board;
    	this.name = name;
    }

    /**
     * Add a character to the army
     * @param newCharacter the new character to add to the army
     */
    public void addCharacter(Character newCharacter) {
    	fightersAlive.add(newCharacter);
    	fightersNb++;
    }

    /**
     * Remove a character from the army
     * @param character the character to remove
     * @return a return code:
     * 	0 - The army doesn't contain the character
     *  1 - The character has successfully been removed from the character
     */
    public int rmCharacter(Character character) {
    	if(fightersAlive.contains(character)) {
    		character.getCell(board).setCharacter(null);
    		fightersAlive.remove(character);
    		fightersNb--;
    		return 1;
    	} return 0;
    }
    
    /**
     * Inform if the army still has fighters or not
     * @return true if the army has fighters, false if not
     */
    public boolean isEmpty() {
    	return fightersAlive.isEmpty();
    }

    /**
     * Try to move a character to destination cell
     * @param movingCharacter the character to move
     * @param cell the destination cell
     * @return a return code:
     *  0 - There is an ally on the requested cell
     *  1 - The movement is not possible for such a character
     *  2 + goTo() - The return code of the goTo method
     */
    public int moveCharacter(Character movingCharacter, Cell cell) {
    	final int addRetValue = 2;
    	if(movingCharacter.isAPossibleMove(cell)) {
	    	if(cell.isEmpty()) {
	    		return movingCharacter.goTo(cell, false) + addRetValue;
	    	} else {
	    		if(cell.getCharacter().getArmy() != movingCharacter.getArmy()) {
	    			return movingCharacter.goTo(cell, true) + addRetValue;
	    		} else {
	    			return 0;
	    		}
	    	}
    	} else {
    		return 1;
    	}
    }
    
    /**
     * Get a character inside the army according to an index
     * @param arrayIndex the index
     * @return the character is returned from the list or null if it doesn't exist
     */
    public Character getCharacter(int arrayIndex) {
    	Character chara;
    	try {
    		chara = fightersAlive.get(arrayIndex);	
		} catch (IndexOutOfBoundsException e) {
			chara = null;
		}
    	
    	return chara;
    }
    
    public Character getCharacter(char c) {
    	int i = 0;
    	for(Character elem : fightersAlive) {
    		if(elem.getSymbol() == c) {
    			return getCharacter(i);
    		} i++;
    	} return null;
    }
    
    /**
     * Override of the toString method
     * @return the name of the army
     */
    public String toString() {
    	return name;
    }
    
    /**
     * Get the army name, the number of characters alive and a description of all the
     * characters present in the army.
     * @return the representing string
     */
    public String dumpArmy() {
    	String str = "The " + name + " army has " + fightersNb + " alive and they are:\n";
    	Iterator<Character> fightersAliveIterator = fightersAlive.iterator();
    	Character chara;
    	while(fightersAliveIterator.hasNext()) {
    		chara = fightersAliveIterator.next();
    		str += "\t[" + chara.getSymbol() + "] " + chara.dumpCharacter() + "\n";
    	}
    	return str;
    }
    
    /**
     * Getter of fightersNb
     * @return fightersNB
     */
    public int getFightersNb() {
    	return fightersNb;
    }
    
    /**
     * Setter of board
     * @param board the board to set
     */
    public void setBoard(Board board) {
    	this.board = board;
    }
    
    /**
     * Link the army and the board to each fighters of the army after a reboot
     * of the game.
     */
    public void reloadCharacter() {
    	Iterator<Character> fightersAliveIterator = fightersAlive.iterator();
    	Character chara;
    	while(fightersAliveIterator.hasNext()) {
    		chara = fightersAliveIterator.next(); 
    		chara.setArmy(this);
    		chara.setBoard(board);
    	}
    }

}
