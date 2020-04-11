package controller;

import model.*;
import model.board.Board;
import model.board.Cell;
import model.character.Character;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputController {

	Scanner myScanner = new Scanner(System.in);
	
    public Character getCharacterToMove(Army army) {
    	System.out.println(army.dumpArmy());
    	System.out.println("\nPlease, select the character you want to move by giving the number printed between [] on its line:");
    	boolean isInteger, isCorrect;
    	int index = 0;
    	Character chara = null;
    	do {
    		isInteger = true;
    		try {
    			index = myScanner.nextInt();	
			} catch (InputMismatchException e) {
				isInteger = false;
				System.out.println("Would you select a NUMBER please:");
			} finally {
				myScanner.nextLine();			
			}
    		
    		if(isInteger) {
    			chara = army.getCharacter(index);
    			if(chara == null) {
    				isCorrect = false;
    				System.out.println("Would you select a number CORRESPONDING to one of the propositions above?");
    			} else {
    				isCorrect = true;
    			}
    		} else {
    			isCorrect = false;
    		}
    	} while(!isCorrect);
    	System.out.println("oui ?");
    	return chara;
    }
    
    public Cell getRecquiredCell(Board board) {
    	return board.getACell(new Coordinates(0, 2));
    }

}
