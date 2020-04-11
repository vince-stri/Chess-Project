package model;

import java.util.ArrayList;
//import java.util.List;
import java.util.Iterator;

import model.board.Board;
import model.board.Cell;
import model.character.Character;

public class Army {
	
    private String name;
    private int fightersNb;
    private int maxFightersNb;

    public Board board;
    public ArrayList<Character> fightersAlive = new ArrayList<Character>();
    
    public Army(Board board, String name) {
    	this.board = board;
    	this.name = name;
    }

    public int addCharacter(Character newCharacter) {
    	fightersAlive.add(newCharacter);
    	fightersNb++;
    	return 0;
    }

    public int rmCharacter(Character character) {
    	if(fightersAlive.contains(character)) {
    		character.getCell().setCharacter(null);
    		fightersAlive.remove(character);
    		fightersNb--;
    		return 1;
    	} return 0;
    }
    
    public boolean isEmpty() {
    	return fightersAlive.isEmpty();
    }

    public int moveCharacter(Character movingCharacter, Cell cell) {
    	movingCharacter.goTo(cell);
    	return 0;
    }
    
    public Character getCharacter(int arrayIndex) {
    	Character chara;
    	try {
    		chara = fightersAlive.get(arrayIndex);	
		} catch (IndexOutOfBoundsException e) {
			chara = null;
		}
    	
    	return chara;
    }
    
    public String toString() {
    	return name;
    }
    
    public String dumpArmy() {
    	String str = "The " + name + " army has " + fightersNb + " alive and they are:\n";
    	Iterator<Character> fightersAliveIterator = fightersAlive.iterator();
    	for(int i = 0; fightersAliveIterator.hasNext(); i++) {
    		str += "\t[" + i + "] " + fightersAliveIterator.next().dumpCharacter() + "\n";
    	}
    	return str;
    }
    
    public int getFightersNb() {
    	return fightersNb;
    }

}
