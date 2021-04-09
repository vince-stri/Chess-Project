package client.view;

import server.model.Coordinates;
import server.model.board.Board;
import server.model.character.Character;

public class CLI extends Journal {
	
	public CLI() {
		
	}
	
	public void displayText(String text) {
		System.out.println(text);
	}
	
	public void displayTextError(String text) {
		System.err.println(text);
	}
	
	public void displayBoard(Board board) {
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
					
				}
			}
			System.out.print("\n		    _________________________________________________________________\n\n");
		}
	}
}
