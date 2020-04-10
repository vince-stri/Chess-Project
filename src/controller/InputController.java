package controller;

import model.*;
import model.board.Board;
import model.character.Character;

public class InputController {
	
    public Character getCharacterToMove(Army army) {
    	return army.getCharacter(2);
    }
    
    public Cell getRecquiredCell(Board board) {
    	return board.getACell(new Coordinates(0, 2));
    }

}
