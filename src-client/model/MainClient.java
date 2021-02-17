package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClient {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		ServerImpl serverObject = (ServerImpl) Naming.lookup("rmi://localhost/Server");
		
		
		ClientImpl client = new ClientImpl();
		Scanner entry = new Scanner(System.in);
		
		boolean isMenuRunning = true
				
		while (isMenuRunning){
			System.out.println("1- Matchmaking aléatoire");
			System.out.println("2- Défier un joueur");
			System.out.println("3- Charger une partie");
			System.out.println("0- Quitter");
			
			try {
				int menuChoice = entry.nextInt();
				entry.nextLine();
				
				switch(menuChoice) {
				case 0:
					isMenuRunning = false;
					break;
					
				case 1:
					goMatchmaking(entry);
					
				case 2:
					defyPlayer(entry);
					
				case 3:
					loadGame(entry);
					
			} catch(InputMismatchException) {
				System.err.println("Erreur dans la saisie clavier");
				entry.nextLine();
			}
			
			
			
		}
	}
		entry.close();

}
