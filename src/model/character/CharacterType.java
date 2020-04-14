package model.character;

/**
 * Describes the different types of characters existing
 */
public enum CharacterType {
	/**
	 * A Pawn that can move forward and on the diagonal if there is a enemy
	 */
	PAWN,
	
	/**
	 * A Knight which has a regular pattern
	 */
	KNIGHT,
	
	/**
	 * A Bishop that can only move diagonally
	 */
	BISHOP,
	
	/**
	 * A Rook that can only move on lines
	 */
	ROOK,
	
	/**
	 * A Queen which combines bishop and rook movements
	 */
	QUEEN,
	
	/**
	 * A king that can move everywhere around it
	 */
	KING;
}
