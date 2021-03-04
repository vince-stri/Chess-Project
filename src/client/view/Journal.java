package client.view;

import serveur.model.Coordinates;
import serveur.model.board.Board;

public abstract class Journal {
	
	public static void displayText(String text) {
		System.out.println(text);
	}
	
	public static void displayTextError(String text) {
		System.err.println(text);
	}
	
	public static void displayBoard(Board board) {
		int n = board.getWidthsNb();
		for(int i = 0; i < n + 1; i++) {
			if(i == 0) {
				System.out.print("  			");
				for(int j = 0; j < n; j++) {
					System.out.print(j + "	");
				} System.out.print("\n");
			} else {
				System.out.print("		" + (i-1) + "   |   ");
				for(int j = 0; j < n; j++) {
					Character temp = board.getACell(new Coordinates(i-1, j)).getCharacter();
					if(temp != null) {
						System.out.print(temp.getSymbol());
					} else {
						System.out.print(" ");
					}
					System.out.print("   |   ");
					
				} //System.out.print("\n");
			}
			System.out.print("\n		    _________________________________________________________________\n\n");
		}
	}
	
	
}
