package controller;

import model.*;
import model.Character;

public class InputController {
	
    public Character getCharacterToMove(Army army) {
    	return army.getCharacter(2);
    }
    
    public Cell getRecquiredCell(Board board) {
    	return board.getACell(2, 2, 0);
    }

}
