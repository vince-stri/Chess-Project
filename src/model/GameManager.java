package model;

import controller.*;
import model.board.Board;
import model.character.Character;
import model.character.CharacterType;
import model.board.BoardShape;
import model.board.ChessBoard;

public class GameManager {
	
	private int armiesNb;
    private GameState gameState;
    private BoardShape boardShape;
    private Board board;
    private Save save;
    private Army armies[];
    private InputController input = new InputController();
    
    
    public GameManager(BoardShape boardShape) {
    	this.boardShape = boardShape;
    	this.gameState = GameState.RUNNING;
    }

    public void setUpBattle() {
    	switch (boardShape) {
			default:
				board = new ChessBoard();
				armies = new Army[2];
				Character character;
				armies[0] = new Army(board, "DarkSight", 16);
				
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
				
				armies[1] = new Army(board, "LightSight", 16);
				
				character = new Character(100, "Rebel_1", board.getACell(new Coordinates(0, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(100, "Rebel_2", board.getACell(new Coordinates(1, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(100, "Rebel_3", board.getACell(new Coordinates(2, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);

				character = new Character(100, "Rebel_4", board.getACell(new Coordinates(3, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(100, "Rebel_5", board.getACell(new Coordinates(4, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(100, "Rebel_6", board.getACell(new Coordinates(5, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(100, "Rebel_7", board.getACell(new Coordinates(6, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(100, "Rebel_8", board.getACell(new Coordinates(7, 1)), armies[0], 10, CharacterType.PAWN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(150, "Qui-Gon Jinn", board.getACell(new Coordinates(0, 0)), armies[0], 10, CharacterType.ROOK);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(150, "Obi-wan Kenobi", board.getACell(new Coordinates(0, 7)), armies[0], 20, CharacterType.ROOK);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(150, "Rey", board.getACell(new Coordinates(0, 1)), armies[0], 40, CharacterType.ROOK);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(150, "Mace Windu", board.getACell(new Coordinates(0, 6)), armies[0], 40, CharacterType.ROOK);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(300, "Chewbaka", board.getACell(new Coordinates(0, 2)), armies[0], 0, CharacterType.BISHOP);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(300, "Han Solo", board.getACell(new Coordinates(0, 5)), armies[0], 0, CharacterType.BISHOP);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(300, "Luke Skywalker", board.getACell(new Coordinates(0, 3)), armies[0], 100, CharacterType.QUEEN);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
				character = new Character(200, "Master Yoda", board.getACell(new Coordinates(0, 4)), armies[0], 200, CharacterType.KING);
				armies[0].addCharacter(character);
				character.getCell().setCharacter(character);
				
			break;
		}

    }

	public void playARound(Army playingArmy) {
    	Character chara = null;
    	Cell cell = null;
    	do {
    		chara = input.getCharacterToMove(playingArmy);
    		cell = input.getRecquiredCell(board);
    	} while(cell == null);
    	
    	playingArmy.moveCharacter(chara, cell);
    }

    public int changeState() {
    	return 0;
    }

    public void setArmyToPlayer() {
    }
    
    public Army[] getArmies() {
    	return armies;
    }

}
