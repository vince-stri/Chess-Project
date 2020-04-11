package model;

import model.board.Board;
import model.character.Character;
import model.character.CharacterType;

public abstract class ArmyComponents {

	public static Army[] generateChessBoardArmies(Board board) {
		Army armies[] = new Army[2];
		Character character;
		armies[0] = new Army(board, "DarkSight");
		
		character = new Character(100, "Stormtroopeer_1", board.getACell(new Coordinates(0, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_2", board.getACell(new Coordinates(1, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_3", board.getACell(new Coordinates(2, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);

		character = new Character(100, "Stormtroopeer_4", board.getACell(new Coordinates(3, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_5", board.getACell(new Coordinates(4, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_6", board.getACell(new Coordinates(5, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_7", board.getACell(new Coordinates(6, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Stormtroopeer_8", board.getACell(new Coordinates(7, 1)), armies[0], 10, CharacterType.PAWN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Captain Grievious", board.getACell(new Coordinates(0, 0)), armies[0], 10, CharacterType.ROOK);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Count Dooku", board.getACell(new Coordinates(0, 7)), armies[0], 20, CharacterType.ROOK);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Darth Maul", board.getACell(new Coordinates(0, 1)), armies[0], 40, CharacterType.ROOK);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Kylo Ren", board.getACell(new Coordinates(0, 6)), armies[0], 40, CharacterType.ROOK);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(300, "Darth Plagueis", board.getACell(new Coordinates(0, 2)), armies[0], 0, CharacterType.BISHOP);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(300, "Snoke", board.getACell(new Coordinates(0, 5)), armies[0], 0, CharacterType.BISHOP);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(300, "Darth Vader", board.getACell(new Coordinates(0, 3)), armies[0], 100, CharacterType.QUEEN);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(200, "Dark Plagueis", board.getACell(new Coordinates(0, 4)), armies[0], 200, CharacterType.KING);
		armies[0].addCharacter(character);
		character.getCell().setCharacter(character);
		
		/* --- */
		
		armies[1] = new Army(board, "LightSight");
		
		character = new Character(100, "Rebel_1", board.getACell(new Coordinates(0, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Rebel_2", board.getACell(new Coordinates(1, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Rebel_3", board.getACell(new Coordinates(2, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);

		character = new Character(100, "Rebel_4", board.getACell(new Coordinates(3, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Rebel_5", board.getACell(new Coordinates(4, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Rebel_6", board.getACell(new Coordinates(5, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Rebel_7", board.getACell(new Coordinates(6, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(100, "Rebel_8", board.getACell(new Coordinates(7, 6)), armies[1], 10, CharacterType.PAWN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Qui-Gon Jinn", board.getACell(new Coordinates(7, 7)), armies[1], 10, CharacterType.ROOK);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Obi-wan Kenobi", board.getACell(new Coordinates(0, 7)), armies[1], 20, CharacterType.ROOK);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Rey", board.getACell(new Coordinates(1, 7)), armies[1], 40, CharacterType.ROOK);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(150, "Mace Windu", board.getACell(new Coordinates(6, 7)), armies[1], 40, CharacterType.ROOK);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(300, "Chewbaka", board.getACell(new Coordinates(2, 7)), armies[1], 0, CharacterType.BISHOP);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(300, "Han Solo", board.getACell(new Coordinates(5, 7)), armies[1], 0, CharacterType.BISHOP);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(300, "Luke Skywalker", board.getACell(new Coordinates(3, 7)), armies[1], 100, CharacterType.QUEEN);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		character = new Character(200, "Master Yoda", board.getACell(new Coordinates(4, 7)), armies[1], 200, CharacterType.KING);
		armies[1].addCharacter(character);
		character.getCell().setCharacter(character);
		
		return armies;
	}
}
