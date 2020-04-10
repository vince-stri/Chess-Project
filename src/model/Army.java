package model;

import java.util.ArrayList;
//import java.util.List;

import model.board.Board;
import model.character.Character;

public class Army {
	
    private String name;
    private int fightersNb;
    private int maxFightersNb;

    public Board board;
    public ArrayList<Character> fightersAlive = new ArrayList<Character>();
    
    public Army(Board board, String name, int fightersNb) {
    	this.board = board;
    	this.name = name;
    	this.fightersNb = fightersNb;
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
    	return fightersAlive.get(arrayIndex);
    }
    
    public String toString() {
    	return name;
    }

}
