package model;

import model.board.Board;
import model.character.Bishop;
import model.character.Character;
import model.character.King;
import model.character.Knight;
import model.character.Pawn;
import model.character.Queen;
import model.character.Rook;

/**
 * An abstract class that set up all the components of the game according to the type of
 * game wanted. 
 * @version 1.0
 */
public abstract class ArmyComponents {

	/**
	 * Generate the armies for a classical chess board game.
	 * @param board the board where the characters will move
	 * @return the statically created armies
	 */
	public static Army[] generateChessBoardArmies(Board board) {
		Army armies[] = new Army[2];
		Character character;
		armies[0] = new Army(board, "DarkSight");
		
		character = new Pawn(100, "Stormtroopeer_1", new Coordinates(0, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_2", new Coordinates(1, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_3", new Coordinates(2, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);

		character = new Pawn(100, "Stormtroopeer_4", new Coordinates(3, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_5", new Coordinates(4, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_6", new Coordinates(5, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_7", new Coordinates(6, 1), armies[0], 10,  20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Stormtroopeer_8", new Coordinates(7, 1), armies[0], 10, 20, board, new Coordinates(0, 1));
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Captain Grievious", new Coordinates(0, 0), armies[0], 10, 25, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Count Dooku", new Coordinates(7, 0), armies[0], 20, 25, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Darth Maul", new Coordinates(1, 0), armies[0], 40, 40, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Kylo Ren", new Coordinates(6, 0), armies[0], 40, 40, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Darth Plagueis", new Coordinates(2, 0), armies[0], 0, 30, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Snoke", new Coordinates(5, 0), armies[0], 0, 30, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Queen(300, "Darth Vader", new Coordinates(3, 0), armies[0], 100, 50, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new King(200, "Dark Plagueis", new Coordinates(4, 0), armies[0], 200, 35, board);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		/* --- */
		
		armies[1] = new Army(board, "LightSight");
		
		character = new Pawn(100, "Rebel_1", new Coordinates(0, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_2", new Coordinates(1, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_3", new Coordinates(2, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);

		character = new Pawn(100, "Rebel_4", new Coordinates(3, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_5", new Coordinates(4, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_6", new Coordinates(5, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_7", new Coordinates(6, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Pawn(100, "Rebel_8", new Coordinates(7, 6), armies[1], 10,  20, board, new Coordinates(0, -1));
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Qui-Gon Jinn", new Coordinates(7, 7), armies[1], 10, 25, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Rook(150, "Obi-wan Kenobi", new Coordinates(0, 7), armies[1], 20, 25, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Rey", new Coordinates(1, 7), armies[1], 40, 40, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Knight(150, "Mace Windu", new Coordinates(6, 7), armies[1], 40, 40, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Chewbaka", new Coordinates(2, 7), armies[1], 0, 30, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Bishop(300, "Han Solo", new Coordinates(5, 7), armies[1], 0, 30, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Queen(300, "Luke Skywalker", new Coordinates(3, 7), armies[1], 100, 50, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new King(200, "Master Yoda", new Coordinates(4, 7), armies[1], 200, 35, board);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		return armies;
	}
}
