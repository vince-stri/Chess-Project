package model;

import model.board.Board;
import model.character.Character;
import model.character.CharacterType;

public abstract class ArmyComponents {

	public static Army[] generateChessBoardArmies(Board board) {
		Army armies[] = new Army[2];
		Character character;
		armies[0] = new Army(board, "DarkSight");
		
		character = new Character(100, "Stormtroopeer_1", new Coordinates(0, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_2", new Coordinates(1, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_3", new Coordinates(2, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);

		character = new Character(100, "Stormtroopeer_4", new Coordinates(3, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_5", new Coordinates(4, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_6", new Coordinates(5, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_7", new Coordinates(6, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_8", new Coordinates(7, 1), armies[0], 10, CharacterType.PAWN, 20);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Captain Grievious", new Coordinates(0, 0), armies[0], 10, CharacterType.ROOK, 25);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Count Dooku", new Coordinates(0, 7), armies[0], 20, CharacterType.ROOK, 25);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Darth Maul", new Coordinates(0, 1), armies[0], 40, CharacterType.KNIGHT, 40);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Kylo Ren", new Coordinates(0, 6), armies[0], 40, CharacterType.KNIGHT, 40);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(300, "Darth Plagueis", new Coordinates(0, 2), armies[0], 0, CharacterType.BISHOP, 30);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(300, "Snoke", new Coordinates(0, 5), armies[0], 0, CharacterType.BISHOP, 30);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(300, "Darth Vader", new Coordinates(0, 3), armies[0], 100, CharacterType.QUEEN, 50);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(200, "Dark Plagueis", new Coordinates(0, 4), armies[0], 200, CharacterType.KING, 35);
		armies[0].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		/* --- */
		
		armies[1] = new Army(board, "LightSight");
		
		character = new Character(100, "Rebel_1", new Coordinates(0, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Rebel_2", new Coordinates(1, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Rebel_3", new Coordinates(2, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);

		character = new Character(100, "Rebel_4", new Coordinates(3, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Rebel_5", new Coordinates(4, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Rebel_6", new Coordinates(5, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Rebel_7", new Coordinates(6, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(100, "Rebel_8", new Coordinates(7, 6), armies[1], 10, CharacterType.PAWN, 20);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Qui-Gon Jinn", new Coordinates(7, 7), armies[1], 10, CharacterType.ROOK, 25);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Obi-wan Kenobi", new Coordinates(0, 7), armies[1], 20, CharacterType.ROOK, 25);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Rey", new Coordinates(1, 7), armies[1], 40, CharacterType.ROOK, 40);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(150, "Mace Windu", new Coordinates(6, 7), armies[1], 40, CharacterType.ROOK, 40);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(300, "Chewbaka", new Coordinates(2, 7), armies[1], 0, CharacterType.BISHOP, 30);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(300, "Han Solo", new Coordinates(5, 7), armies[1], 0, CharacterType.BISHOP, 30);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(300, "Luke Skywalker", new Coordinates(3, 7), armies[1], 100, CharacterType.QUEEN, 50);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		character = new Character(200, "Master Yoda", new Coordinates(4, 7), armies[1], 200, CharacterType.KING, 35);
		armies[1].addCharacter(character);
		character.getCell(board).setCharacter(character);
		
		return armies;
	}
}
