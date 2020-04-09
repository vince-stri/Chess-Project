package model;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

public class Army {
	
    private String name;
    private int fightersNb;
    private int maxFightersNb;

    public Character fighters[];
    public Board board;
    
    /* temporary hard coded team components */
    public String charNames[] = {"Stormtroopeer_1", "Stormtroopeer_2", "Stormtroopeer_3",
    		"Stormtroopeer_4", "Stormtroopeer_5", "Stormtroopeer_6", "Stormtroopeer_7", "Stormtroopeer_8", "Darth Maul", "Count Dooku",
    		"Kylo Ren", "Snoke", "Booba Fett", "Captain Grievous", "Darth Vader", "Emperor Palpatine"};
    public CharacterType charTypes[] = {CharacterType.PAWN, CharacterType.PAWN, CharacterType.PAWN, CharacterType.PAWN, CharacterType.PAWN,
    		CharacterType.PAWN, CharacterType.PAWN, CharacterType.PAWN, CharacterType.ROOK, CharacterType.ROOK, CharacterType.BISHOP,
    		CharacterType.BISHOP, CharacterType.KNIGHT, CharacterType.KNIGHT, CharacterType.QUEEN, CharacterType.KING};
    

    public int addCharacter(Character newCharacter) {
    	return 0;
    }

    public int removeCharacter(int removedCharacter) {
    	return 0;
    }

    public boolean isArmyEmpty() {
    	return false;
    }

    public int moveCharacter(Character movingCharacter, Coordinates coord) {
    	return 0;
    }

    public Army(Board board, String name, int maxFightersNb) {
    	this.board = board;
    	this.name = name;
    	this.maxFightersNb = maxFightersNb;
    	this.fightersNb = maxFightersNb;
    	fighters = new Character[maxFightersNb];
    	 
    	/* temporary hard coded team components */
    	Cell charCell[] = {board.getACell(0, 1, 0), board.getACell(1, 1, 0), board.getACell(2, 1, 0), board.getACell(3, 1, 0), 
    			board.getACell(4, 1, 0), board.getACell(5, 1, 0), board.getACell(6, 1, 0), board.getACell(7, 1, 0), 
    			board.getACell(0, 0, 0), board.getACell(1, 0, 0), board.getACell(2, 0, 0), board.getACell(3, 0, 0), 
    			board.getACell(4, 0, 0), board.getACell(5, 0, 0), board.getACell(6, 0, 0), board.getACell(7, 0, 0), };
    	for(int i = 0; i < maxFightersNb; i++) {
    		fighters[i] = new Character(100, charNames[i], charTypes[i], charCell[i]);
    	}
    }

    public List<Character> getAliveCharacters() {
    	return null;
    }

}
