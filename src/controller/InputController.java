package controller;

import model.*;
import model.board.Board;
import model.board.Cell;
import model.character.Character;
import view.Journal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputController {

	Scanner myScanner = new Scanner(System.in);
	
    public Character getCharacterToMove(Army army) {
    	Journal.displayText(army.dumpArmy());
    	Journal.displayText("\nPlease, select the character you want to move by giving the number printed between [] on its line:");
    	boolean isInteger, isCorrect;
    	int index = 0;
    	Character chara = null;
    	do {
    		isInteger = true;
    		try {
    			index = myScanner.nextInt();	
			} catch (InputMismatchException e) {
				isInteger = false;
				Journal.displayText("Would you select a NUMBER please:");
			} finally {
				myScanner.nextLine();			
			}
    		
    		if(isInteger) {
    			chara = army.getCharacter(index);
    			if(chara == null) {
    				isCorrect = false;
    				Journal.displayText("Would you select a number CORRESPONDING to one of the propositions above?");
    			} else {
    				isCorrect = true;
    			}
    		} else {
    			isCorrect = false;
    		}
    	} while(!isCorrect);
    	return chara;
    }
    
    public Cell getRecquiredCell(Board board) {
    	switch (board.getBoardShape()) {
    		default:
    			Journal.displayText("Please, select the cell where you want to move on your character:\n\tx:");
    			int x = 0, y = 0;
    			boolean isInteger, isCorrect;
    			/* get x */
    			do {
    				isInteger = true;
    				isCorrect = true;
    				try {
    					x = myScanner.nextInt();	
    				} catch (InputMismatchException e) {
    					isInteger = false;
    					isCorrect = false;
    					Journal.displayText("Would you select a NUMBER please:");
    				} finally {
    					myScanner.nextLine();			
    				}
    				if(isInteger) {
    					if(x < 0 || x >= board.getWidthsNb()) {
    						Journal.displayText("Would you select a VALID x-coordinate belonging to [0," + board.getWidthsNb() + "[:");
    						isCorrect = false;
    					}
    				}
    			} while(!isCorrect);
    			/* get y */
    			Journal.displayText("\ty:");
    			do {
    				isInteger = true;
    				isCorrect = true;
    				try {
    					y = myScanner.nextInt();	
    				} catch (InputMismatchException e) {
    					isInteger = false;
    					isCorrect = false;
    					Journal.displayText("Would you select a NUMBER please:");
    				} finally {
    					myScanner.nextLine();			
    				}
    				if(isInteger) {
    					if(y < 0 || y >= board.getWidthsNb()) {
    						Journal.displayText("Would you select a VALID y-coordinate belonging to [0," + board.getWidthsNb() + "[:");
    						isCorrect = false;
    					}
    				}
    			} while(!isCorrect);
    			return board.getACell(new Coordinates(x, y));
		}
    }
    
    public boolean wantToSave() {
    	String str;
    	boolean error;
    	do {
    		error = false;
			Journal.displayText("Would you stop your game here and come back later? [y or n]");
    		str = myScanner.nextLine();
    		if(str != "y" || str != "n") {
    			error = true;
    			Journal.displayText("Would you enter a CORRECT character?");
    		}
    	} while(error);
    	return str == "y";
    }

}
