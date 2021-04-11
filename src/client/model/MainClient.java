package client.model;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

import client.view.CLI;
import client.view.Journal;
import shared.Client;
import shared.Iserver;


public class MainClient {
	
	static Journal journal;
	static Iserver serverObject;
	static Client playingClient;
	static Scanner entry;
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, SQLException {
		// TODO Auto-generated method stub
		journal = new CLI();
		try {
			serverObject = (Iserver) Naming.lookup("rmi://localhost:1099/ChessProject");
		}catch(ConnectException ex) {
			journal.displayTextError("Le serveur du jeu ne répond pas retentez plus tard");
			return;
		}
		boolean connectionSuccess = false;
		entry = new Scanner(System.in);
		playingClient = new Client(-1, null, null, journal);
		
		/*Menu de connexion*/
		journal.displayText("Bienvenue sur battle chess royale"); 
		connectionSuccess = connectOrRegister(entry, serverObject, playingClient);
		if(connectionSuccess == false) {
			journal.displayText("Au revoir");
			return;
		}
		
		
		/*Menu principal*/
		boolean isMenuRunning = true;
				
		while (isMenuRunning){
			journal.displayText("1- Matchmaking aleatoire");
			journal.displayText("2- Defier un joueur");
			journal.displayText("3- Charger une partie");
			journal.displayText("0- Quitter");
			
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
				journal.displayTextError("Erreur dans la saisie clavier");
				entry.nextLine();
			}
		}
		entry.close();
		journal.displayText("A bientot !");
	}
	//connection or login screen 
	private static boolean connectOrRegister(Scanner entry, Iserver serverObject,Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isMenuRunning = true;
		int connectionResult = 1;//retry
		int registerResult = 1;//retry
		int menuChoice = -1;
				
		while(isMenuRunning) {
			journal.displayText("2- Creer un compte");
			journal.displayText("1- Connexion");
			journal.displayText("0- Quitter");
			
			try {
				menuChoice = entry.nextInt();
				entry.nextLine();
				if(menuChoice >= 0 && menuChoice <= 2) {
					isMenuRunning = false;
				}
			} catch(InputMismatchException e) {
				journal.displayTextError("Erreur dans la saisie clavier");
				entry.nextLine();
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
			
			journal.displayText("Creer un compte");
			journal.displayText("Entrez un pseudo");
			pseudo = entry.nextLine();
			if(serverObject.checks_user(pseudo) != 0) {//user already exist
				journal.displayText("Entrez votre mot de passe");
				password = entry.nextLine();
				journal.displayText("Confirmez votre mot de passe");
				confirmpassword = entry.nextLine();
				
				if(password.contentEquals(confirmpassword)) {//password and confimpassword are the same
					registerResult = serverObject.register(pseudo, password);
					if(registerResult == 1) { //DB register went well
						isRegisterMenuRunning = false;
						clientToInstanciate.SetPseudo(pseudo);
					}else {//an issue occured during DB register
						journal.displayText("Un probleme est survenu");
						journal.displayText("1- Reessayer");
						journal.displayText("0- Quitter");
						retryOrQuit = entry.nextInt();
						entry.nextLine();
						if(retryOrQuit == 0) {
							return 0; //fail
						}
					}
				} else {
					journal.displayText("Les mots de passe ne correspondent pas");
				}
			}
			
		}
		journal.displayText("Votre compte a ete cree vous pouvez vous connecter");
		return 1; //Success
	}
	
	private static int connect(Scanner entry, Iserver serverObject, Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isConnectionMenuRunning = true;
		String pseudo;
		String password;
		int retryOrQuit;
		
		while(isConnectionMenuRunning) {
			journal.displayText("Connexion");
			journal.displayText("Entrez votre pseudo");
			pseudo = entry.nextLine();
			journal.displayText("Entrez votre mot de passe");
			password = entry.nextLine();
			clientToInstanciate.SetPseudo(pseudo);
			if(serverObject.login(clientToInstanciate, password).equals("0")) {
				journal.displayText("1- Reessayer");
				journal.displayText("0- Quitter");
				retryOrQuit = entry.nextInt();
				entry.nextLine();
				if(retryOrQuit == 0) {
					return 0; //fail
				}
			} else {
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
		journal.displayText(message);
	
		
	}

	private static void goMatchmaking(Scanner entry, Iserver serverObject,Client playingClient) throws RemoteException {
			String gameManagerId = serverObject.startMatchMaking(playingClient);
			playAGame(entry, gameManagerId,serverObject,playingClient);
	}			
	
	private static void playAGame(Scanner entry,String gameManagerId,Iserver serverObject,Client playingClient) throws RemoteException {
		int isGameOver = -1;
		int origin = -1;
		int destination = -1;
		while(isGameOver == -1) {
			boolean correctInput = false;
			int action = getAction(entry);
			switch (action) {
			case 0: // send a message
				journal.displayText("Votre message :");
				String msg = entry.nextLine();
				try {
					serverObject.sendMessage(gameManagerId, playingClient, msg, false);
				} catch (NullPointerException e) {
					journal.displayText("Your opponent has quit the game. Try to found another one");
					isGameOver = -2; 
				}
				break;
			case 1: // save the game
				try {
					if(!serverObject.minimumPlayersAreConnected(gameManagerId)) {
						journal.displayText("[INFO] Veuillez attendre l'arrivée de votre adversaire avant de jouer.");
					} else {						
						serverObject.save(gameManagerId, playingClient);
						isGameOver = -2;
					}
				} catch (NullPointerException e) {
					journal.displayText("Your opponent has quit the game. Try to found another one");
					isGameOver = -2;
				}
				break;
			case 2: // play
				boolean moveError = false;
				while( !correctInput) {							
					origin = getMoveOptions("selectionnez une source pour votre personnage. Tapez la ligne puis la colonne (exemple: 43)", gameManagerId);
					if(origin >= 0) {							
						destination = getMoveOptions("selectionnez une destination pour votre personnage. Tapez la ligne puis la colonne (exemple: 43)", gameManagerId);
						if(destination >= 0) {							
							correctInput = serverObject.isAGoodMove(origin,destination,gameManagerId,playingClient);
						} else {
							moveError = true;
						}
					} else {
						moveError = true;
						correctInput = true;
					}
				}
				if( !moveError) {
					try {
						serverObject.playMove(origin,destination,gameManagerId,playingClient);
						journal.displayText("Mouvement validé");
					} catch(NullPointerException e) {
						journal.displayText("Your opponent has quit the game. Try to found another one");
						isGameOver = -2;
					}
				}
				break;
			default: // quit
				serverObject.clientQuit(gameManagerId, playingClient);
				isGameOver = -2;
				break;
			}
			if(isGameOver >= 0) {
				if(serverObject.minimumPlayersAreConnected(gameManagerId)) {
					isGameOver = serverObject.isGameOver(gameManagerId,playingClient);			
				}
			}
		}
		journal.displayText("Fin de la partie");
		if(isGameOver == 0) {
			journal.displayText("Vous avez Gagné !");
		} else if(isGameOver > 0) {
			journal.displayText("Vous avez Perdu !");
		}
	}
	
	private static int getAction(Scanner entry) {
		boolean correctAction = false;
		int action = -1;
		while(!correctAction) {
			try {
				journal.displayText("3 - Quitter\n2 - Jouer un coup\n1 - Sauvegarder la partie\n0 - Envoyer un message");
				action = entry.nextInt();
				if(action >= 0 && action < 4) {
					correctAction = true;
				}
			} catch (InputMismatchException e) {
				journal.displayTextError("Il faut rentrer une valeur numerique");
			}
			entry.nextLine();
		}
		return action;
	}
	
	private static int getMoveOptions(String msg, String GMId) throws RemoteException {
		boolean correctOrigin = false;
		int coordinates = -1;
		while( !correctOrigin) {
			try {
				journal.displayText(msg);
				coordinates = entry.nextInt();
				if(!serverObject.minimumPlayersAreConnected(GMId)) {
					journal.displayText("[INFO] Veuillez attendre l'arrivée de votre adversaire avant de jouer.");
					correctOrigin = true;
					coordinates = -1;
				} else if(serverObject.isGameOver(GMId, playingClient) != -1) {
					coordinates = -2;
				} else if(coordinates > -1 && coordinates < 78) {
					correctOrigin = true;
				} else {
					journal.displayText("Rentrez un nombre entre 00 et 77");
				}
			} catch(InputMismatchException e){
				journal.displayText("Rentrez un nombre entre 00 et 77");
				entry.nextLine();
			} catch(NullPointerException e) {
				journal.displayText("Your opponent has quit the game. Try to found another one");
				coordinates = -3;
				correctOrigin = true;
			}
			entry.nextLine();
		}
		return coordinates;
	}
	
}
