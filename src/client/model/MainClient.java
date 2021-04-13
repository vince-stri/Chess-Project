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

/**
 * The client launcher
 * @version 1.0
 * @author enzo moretto
 */
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
					goMatchmaking();
					break;
					
				case 2:
					defyPlayer();
					break;
				case 3:
					loadGame();
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
			connectionResult = connect(clientToInstanciate);
			break;
		
		case 2:
			registerResult = register(clientToInstanciate);
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

	private static int register(Client clientToInstanciate) throws RemoteException, SQLException {
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
	
	private static int connect(Client clientToInstanciate) throws RemoteException, SQLException {
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
	private static void loadGame() {
		// TODO Auto-generated method stub
		
	}

	private static void defyPlayer() throws RemoteException {
		String message;	
		message = serverObject.startDuel(playingClient);
		journal.displayText(message);
	
		
	}

	private static void goMatchmaking() throws RemoteException {
			String gameManagerId = serverObject.startMatchMaking(playingClient);
			playAGame(gameManagerId);
	}			
	
	/**
	 * Major loop of gameplay. Asks player actions to perform while fighting for the victory. 
	 * @param GMId the string identifying the GameManager
	 * @throws RemoteException raised during connection issue by RMI
	 */
	private static void playAGame(String gameManagerId) throws RemoteException {
		int isGameOver = -1;
		int origin = -1;
		int destination = -1;
		
		/* Game loop */
		while(isGameOver == -1) {
			boolean correctInput = false;
			int action = getAction();
			switch (action) {
			
			/* Send a message */
			case 0:
				journal.displayText("Votre message :");
				String msg = entry.nextLine();
				try {
					serverObject.sendMessage(gameManagerId, playingClient, msg, false);
				} catch (NullPointerException e) {
					journal.displayText("Your opponent has quit the game. Try to found another one");
					isGameOver = -2; 
				}
				break;
				
			/* Save the game */
			case 1:
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
				
			/* Play a turn */ 
			case 2:
				boolean moveError = false;
				
				/* Loop verifying the validity of the player's input */
				while( !correctInput) {					
					origin = getMoveOptions("selectionnez une source pour votre personnage. Tapez la ligne puis la colonne (exemple: 43)", gameManagerId);
					if(origin >= 0) {							
						destination = getMoveOptions("selectionnez une destination pour votre personnage. Tapez la ligne puis la colonne (exemple: 43)", gameManagerId);
						if(destination >= 0) {							
							switch (serverObject.isAGoodMove(origin,destination,gameManagerId,playingClient)) {
							case 0:
								correctInput = true; 
								break;
							case 1:
								correctInput = false;
								break;
							default:
								correctInput = true;
								moveError = true;
								break;
							}
									
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
				
			/* Quit the game */
			default:
				serverObject.clientQuit(gameManagerId, playingClient);
				isGameOver = -2;
				break;
			}
			
			/* Tests if the game is over */
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
	
	/**
	 * Gets the action to perform from the player
	 * @return a code representing the action wanted by the player
	 */
	private static int getAction() {
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
	
	/**
	 * Gets coordinates from the player
	 * @param msg the message to display to the player
	 * @param GMId the string identifying the GameManager
	 * @return the chosen coordinates or an information code.
	 * 		-1 : It doesn't belong to the player to play
	 * 		-2 : The player cannot play because the game is over
	 * 		-3 : The opponent left the game so the player cannot play
	 * 		x : the coordinates selected by the player
	 * @throws RemoteException raised during connection issue by RMI
	 */
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
