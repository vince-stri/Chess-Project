package controller;

import model.*;
import model.board.Board;
import model.board.Cell;
import model.character.Character;

/**
 * Getting inputs abstract class
 * @version 1.0
 * @author axel gauthier
 */
public abstract class InputController {

	/**
	 * Get a Character from an army
	 * @param army the army to select the character
	 * @return the wanted character
	 */
	public abstract Character getCharacterToMove(Army army);
	
	/**
	 * Get a cell from the board
	 * @param board the board to select the cell
	 * @return the wanted board
	 */
	public abstract Cell getRecquiredCell(Board board);
	
	/**
	 * Check if the player wants to save or not
	 * @return true if the player wants to save, else otherwise
	 */
	public abstract boolean wantToSave();
	
	/**
	 * Check if the player wants to recover the save file
	 * @return true if the player wants to recover
	 */
	public abstract boolean wantToRecoverFile();
	
	/**
	 * Check if the player wants to create a game from scratch or load it from a file
	 * @return true if the player wants to create a game from scratch
	 */
	public abstract boolean wantANewGame();
	
	/**
	 * Get a path
	 * @return the got path
	 */
	public abstract String getAPath();
}
