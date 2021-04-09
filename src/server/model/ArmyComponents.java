package server.model;

import server.main.ClientWrapper;
import server.model.board.Board;
import server.model.character.*;
import server.model.character.Character;

/**
 * An abstract class that set up all the components of the game according to the type of
 * game wanted. 
 * @version 1.0
 * @author axel gauthier
 */
public abstract class ArmyComponents {

	/**
	 * Generate the armies for a classical chess board game.
	 * @param board the board where the characters will move
	 * @return the statically created armies
	 */
	public static Army[] generateChessBoardArmies(Board board, int numberOfArmies) {
		Army armies[] = new Army[numberOfArmies];
		Character character;
		
		// If the game as to be updated to be able to be played by more than two players, it would be interesting to assign those players randomly to the armies below
		
		armies[0] = new Army(board, "DarkSight", null);
		
		character = new Pawn(100, "Stormtroopeer_1", new Coordinates(0, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'a');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_2", new Coordinates(1, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'b');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_3", new Coordinates(2, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'c');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);

		character = new Pawn(100, "Stormtroopeer_4", new Coordinates(3, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'd');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_5", new Coordinates(4, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'e');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_6", new Coordinates(5, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'f');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_7", new Coordinates(6, 1), armies[0], 10,  20, board, new Coordinates(0, 1), 'g');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_8", new Coordinates(7, 1), armies[0], 10, 20, board, new Coordinates(0, 1), 'h');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Captain Grievious", new Coordinates(0, 0), armies[0], 10, 25, board, 'i');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Count Dooku", new Coordinates(7, 0), armies[0], 20, 25, board, 'j');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Darth Maul", new Coordinates(1, 0), armies[0], 40, 40, board, 'k');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Kylo Ren", new Coordinates(6, 0), armies[0], 40, 40, board, 'l');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Darth Plagueis", new Coordinates(2, 0), armies[0], 0, 30, board, 'm');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Snoke", new Coordinates(5, 0), armies[0], 0, 30, board, 'n');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Queen(300, "Darth Vader", new Coordinates(3, 0), armies[0], 100, 50, board, 'o');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new King(200, "Emperor Palpatine", new Coordinates(4, 0), armies[0], 200, 35, board, 'p');
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		/* --- */
		
		armies[1] = new Army(board, "LightSight", null);
		
		character = new Pawn(100, "Rebel_1", new Coordinates(0, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'A');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_2", new Coordinates(1, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'B');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_3", new Coordinates(2, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'C');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);

		character = new Pawn(100, "Rebel_4", new Coordinates(3, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'D');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_5", new Coordinates(4, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'E');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_6", new Coordinates(5, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'F');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_7", new Coordinates(6, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'G');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_8", new Coordinates(7, 6), armies[1], 10,  20, board, new Coordinates(0, -1), 'H');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Qui-Gon Jinn", new Coordinates(7, 7), armies[1], 10, 25, board, 'I');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Obi-wan Kenobi", new Coordinates(0, 7), armies[1], 20, 25, board, 'J');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Rey", new Coordinates(1, 7), armies[1], 40, 40, board, 'K');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Mace Windu", new Coordinates(6, 7), armies[1], 40, 40, board, 'L');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Chewbaka", new Coordinates(2, 7), armies[1], 0, 30, board, 'M');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Han Solo", new Coordinates(5, 7), armies[1], 0, 30, board, 'N');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Queen(300, "Luke Skywalker", new Coordinates(3, 7), armies[1], 100, 50, board, 'O');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new King(200, "Master Yoda", new Coordinates(4, 7), armies[1], 200, 35, board, 'P');
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		if(numberOfArmies > 2) {
			// will be changed if the game allows more than 2 players
		}
		
		return armies;
	}
}
