package client.controller;
import java.util.InputMismatchException;
import java.util.Scanner;

import client.view.Journal;
import server.model.Army;
import server.model.Coordinates;
import server.model.board.Board;
import server.model.board.Cell;
import server.model.character.Character;


/**
 * Inherited class from InputController interacting with the player through a terminal
 * @version 1.0
 * @author axel gauthier
 */
public class InputTextualController extends InputController {

	/** 
	 * The scanner used to get inputs from the terminal
	 */
	Scanner myScanner = new Scanner(System.in);
	Journal journal;
	/**
	 * Constructor of InputTextualController
	 */
	public InputTextualController(Journal journal) {
		myScanner = new Scanner(System.in);
		this.journal = journal;
	}
	
	/**
	 * {@inheritDoc}
	 */
	 public server.model.character.Character getCharacterToMove(Army army) {
	    	journal.displayText(army.dumpArmy());
	    	journal.displayText("\nPlease, select the character you want to move by giving the character printed between [] on its line:");
	    	boolean isCorrect = true;
	    	Character chara = null;
	    	char c;
	    	do {
	    		if(!isCorrect) {
	    			journal.displayText("Please would you select a correct character!");
	    		}
	    		isCorrect = true;
				c = myScanner.next().charAt(0);
				if((chara = army.getCharacter(c)) == null) {
	    			isCorrect = false;
	    		}
	    	} while(!isCorrect);
	    	System.out.println(chara.dumpCharacter());
	    	return chara;
	    }
    
    /**
	 * {@inheritDoc}
	 */
    public Cell getRecquiredCell(Board board) {
    	switch (board.getBoardShape()) {
    		default:
    			journal.displayText("Please, select the cell where you want to move on your character:\n\tx:");
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
    					journal.displayText("Would you select a NUMBER please:");
    				} finally {
    					myScanner.nextLine();			
    				}
    				if(isInteger) {
    					if(x < 0 || x >= board.getWidthsNb()) {
    						journal.displayText("Would you select a VALID x-coordinate belonging to [0," + board.getWidthsNb() + "[:");
    						isCorrect = false;
    					}
    				}
    			} while(!isCorrect);
    			/* get y */
    			journal.displayText("\ty:");
    			do {
    				isInteger = true;
    				isCorrect = true;
    				try {
    					y = myScanner.nextInt();	
    				} catch (InputMismatchException e) {
    					isInteger = false;
    					isCorrect = false;
    					journal.displayText("Would you select a NUMBER please:");
    				} finally {
    					myScanner.nextLine();			
    				}
    				if(isInteger) {
    					if(y < 0 || y >= board.getWidthsNb()) {
    						journal.displayText("Would you select a VALID y-coordinate belonging to [0," + board.getWidthsNb() + "[:");
    						isCorrect = false;
    					}
    				}
    			} while(!isCorrect);
    			return board.getACell(new Coordinates(x, y));
		}
    }
    
    /**
	 * {@inheritDoc}
	 */
    public boolean wantToSave() {
    	String str;
    	boolean error;
    	do {
    		error = false;
			journal.displayText("Would you stop your game here and come back later? [y or n]");
    		str = myScanner.nextLine();
    		if(!str.equals("y") && !str.equals("n")) {
    			error = true;
    			journal.displayText("Would you enter a CORRECT character?");
    		}
    	} while(error);
    	return str.equals("y");
    }
    
    public boolean wantToRecoverFile() {
    	String str;
    	boolean error;
    	do {
    		error = false;
			journal.displayText("The save file doesn't exist.\nWould you like to recover it? (if not a new game will be started) [y or n]");
    		str = myScanner.nextLine();
    		if(!str.equals("y") && !str.equals("n")) {
    			error = true;
    			journal.displayText("Would you enter a CORRECT character?");
    		}
    	} while(error);
    	return str.equals("y");
    }
    
    /**
	 * {@inheritDoc}
	 */
    public boolean wantANewGame() {
    	String str;
    	boolean error;
    	do {
    		error = false;
			journal.displayText("Would you like to start a new game or to continue a previous one? [s or c]");
    		str = myScanner.nextLine();
    		if(!str.equals("s") && !str.equals("c")) {
    			error = true;
    			journal.displayText("Would you enter a CORRECT character?");
    		}
    	} while(error);
    	return str.equals("s");
    }
    
    /**
	 * {@inheritDoc}
	 */
    public String getAPath() {
    	journal.displayText("Please, indicate a path:");
    	return myScanner.nextLine();
    }

}
