package client.model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import server.main.Iserver;
import server.main.ServerImpl;
import shared.Client;


public class MainClient {

	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, SQLException {
		
		// TODO Auto-generated method stub
		Iserver serverObject = (Iserver) Naming.lookup("rmi://localhost:1099/ChessProject");
		boolean connectionSucces = false;
		Scanner entry = new Scanner(System.in);
		Client playingClient =new Client(-1, null, null);
		
		while(connectionSucces == false) {
			System.out.println("Bienvenue sur battle chess royale"); //la vanne du battle royale
			connectionSucces = connectOrRegister(entry, serverObject, playingClient);
		}
		
		/*Menu principal*/
		boolean isMenuRunning = true;
				
		while (isMenuRunning){
			System.out.println("1- Matchmaking aleatoire");
			System.out.println("2- Defier un joueur");
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
					goMatchmaking(entry,serverObject,playingClient);
					break;
					
				case 2:
					defyPlayer(entry,serverObject,playingClient);
					break;
				case 3:
					loadGame(entry,serverObject,playingClient);
					break;
				}
					
			} catch(InputMismatchException e) {
				System.err.println("Erreur dans la saisie clavier");
				entry.nextLine();
			}
			
			
			
		}
		entry.close();

	}
	//connection or login screen 
	private static boolean connectOrRegister(Scanner entry, Iserver serverObject,Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isMenuRunning = true;
		int connectionResult = 1;//retry
		int registerResult = 1;//retry
		int menuChoice = -1;
				
		while(isMenuRunning) {
			System.out.println("2- Creer un compte");
			System.out.println("1- Connexion");
			System.out.println("0- Quitter");
			
			try {
				menuChoice = entry.nextInt();
				entry.nextLine();
				if(menuChoice >= 0 && menuChoice <= 2) {
					isMenuRunning = false;
				}
			} catch(InputMismatchException e) {
				System.err.println("Erreur dans la saisie clavier");
			}
		}
		
		switch(menuChoice) {
		case 0:
			return false;
		case 1:
			connectionResult = connect(entry,serverObject,clientToInstanciate);
			break;
		
		case 2:
			registerResult = register(entry,serverObject,clientToInstanciate);
			break;
		}
		if((connectionResult == 0) || (registerResult == 0)) {//Quit
			return false;
		}
		if((connectionResult == 1)) {
			return true;
		}
		return false;//If we reach this point that means the user typed 0
	}

	private static int register(Scanner entry, Iserver serverObject, Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isRegisterMenuRunning = true;
		int registerResult;
		int retryOrQuit;
		String pseudo;
		String password;
		String confirmpassword;
		
		while(isRegisterMenuRunning) {//register Menu loop
			
			System.out.println("Creer un compte");
			System.out.println("Entrez un pseudo");
			pseudo = entry.nextLine();
			if(serverObject.checks_user(pseudo) != 0) {//user already exist
				System.out.println("Entrez votre mot de passe");
				password = entry.nextLine();
				System.out.println("Confirmez votre mot de passe");
				confirmpassword = entry.nextLine();
				
				if(password.contentEquals(confirmpassword)) {//password and confimpassword are the same
					registerResult = serverObject.register(pseudo, password);
					if(registerResult == 1) { //DB register went well
						isRegisterMenuRunning = false;
						clientToInstanciate.SetPseudo(pseudo);
						
						
					}else {//an issue occured during DB register
						System.out.println("Un probleme est survenu");
						System.out.println("1- Reessayer");
						System.out.println("0- Quitter");
						retryOrQuit = entry.nextInt();
						entry.nextLine();
						if(retryOrQuit == 0) {
							return 0; //fail
						}
					}
				} else {
					System.out.println("Les mots de passe ne correspondent pas");
				}
			}
			
		}
		System.out.println("Votre compte a ete cree vous pouvez vous connecter");
		return 1; //Success
	}
	
	private static int connect(Scanner entry, Iserver serverObject, Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isConnectionMenuRunning = true;
		String pseudo;
		String password;
		int retryOrQuit;
		
		while(isConnectionMenuRunning) {
			System.out.println("Conection");
			System.out.println("entrez votre pseudo");
			pseudo = entry.nextLine();
			System.out.println("entrez votre mot de passe");
			password = entry.nextLine();
			clientToInstanciate.SetPseudo(pseudo);
			if(serverObject.login(clientToInstanciate, password) == "0") {
				System.out.println("1- Reessayer");
				System.out.println("0- Quitter");
				retryOrQuit = entry.nextInt();
				entry.nextLine();
				if(retryOrQuit == 0) {
					return 0; //fail
				}
			}else {
				isConnectionMenuRunning = false;
			}
		}
		return 1; //Success
		
	}
	private static void loadGame(Scanner entry, Iserver serverObject,Client playingClient) {
		// TODO Auto-generated method stub
		
	}

	private static void defyPlayer(Scanner entry, Iserver serverObject,Client playingClient) throws RemoteException {
		String message;
		
		message = serverObject.startDuel(playingClient);
		System.out.println(message);
	
		
	}

	private static void goMatchmaking(Scanner entry, Iserver serverObject,Client playingClient) throws RemoteException {
			String gameManagerId = serverObject.startMatchMaking(playingClient);
			playAGame(entry, gameManagerId,serverObject,playingClient);
			
	}
	
	private static void playAGame(Scanner entry,String gameManagerId,Iserver serverObject,Client playingClient) throws RemoteException {
		boolean correctInput,correctOrigin,correctDestination;
		int isGameOver;
		int origin = -1;
		int destination = -1;
		int moveMessage;
		isGameOver = -1;
		System.out.println(gameManagerId);
		/*
		while(isGameOver == -1) {
			isGameOver = serverObject.isGameOver(gameManagerId,playingClient);
			correctInput = false; 
			while(correctInput == false) {
				correctOrigin = false;
				while(correctOrigin = false) {
					try {
						System.out.println("selectionnez un personnage à jouer. Tapez la ligne puis la colonne (exemple: 34)");
						origin = entry.nextInt();
						if(origin > -1 && origin < 78) {
							correctOrigin = true;
						}else {
							System.out.println("Saisie d'origine non valide. rééssayez");
						}
					}catch (InputMismatchException ex){
						System.out.println("Votre saisie est invalide rééssayez");
						entry.nextLine();
					}
					
				}
				correctDestination = false;
				while(correctDestination == false) {
					try {
						System.out.println("selectionnez une destination pour votre personnage. Tapez la ligne puis la colonne (exemple: 34)");
						destination = entry.nextInt();
						if(destination > -1 && destination < 78) {
							correctDestination = true;
						}else {
							System.out.println("Saisie de destination non valide. rééssayez");
						}
					}catch (InputMismatchException ex){
						System.out.println("Votre saisie est invalide rééssayez");
						entry.nextLine();
					}
					
				}
				correctInput = serverObject.isAGoodMove(origin,destination,gameManagerId,playingClient);
			}
			moveMessage = serverObject.playMove(origin,destination,gameManagerId,playingClient);
			System.out.println("Mouvement validé");
			
		}
		//affichage du message de victoire ou de défaite
		if(isGameOver == 0) {
			System.out.println("Vous avez Gagné !");
		}else {
			System.out.println("Vous avez Perdu !");
		}*/
	}
	
}
