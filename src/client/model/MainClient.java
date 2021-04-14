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
import shared.iClient;

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
			journal.displayTextError("Le serveur du jeu ne répond pas, vérifier qu'il est en cours d'exécution");
			return;
		}
		boolean connectionSuccess = false;
		entry = new Scanner(System.in);
		playingClient = new Client(-1, null, null, journal);
		
		/*Menu de connexion*/
		journal.displayText("/\\/\\ Bienvenue sur le Battle Chess Royale ! /\\/\\"); 
		connectionSuccess = connectOrRegister(entry, serverObject, playingClient);
		if(connectionSuccess == false) {
			journal.displayText("Bye bye, à bientôt!");
			return;
		}
		
		
		/*Menu principal*/
		boolean isMenuRunning = true;
				
		while (isMenuRunning){
			journal.displayText("\n\n\n//_____________BATTLE CHESS ROYALE____________\\\\\n");
			journal.displayText("1- Matchmaking - Joue une partie en ligne");
			journal.displayText("2- Duel - Joue contre un de tes amis");
			journal.displayText("3- Charger une partie - Reprend une partie déjà commencé");
			journal.displayText("0- Quitter");
			journal.displayText("\n\nJoueur : " + playingClient.GetPseudo());
			
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
				journal.clearTerminal();
				journal.displayTextError("Erreur dans la saisie clavier");
				entry.nextLine();
			}
		}
		entry.close();
		journal.displayText("Bye bye, à bientôt!");
	}
	
	

	/**
	 * First display asking if connect or register
	 * Calls connect() or register()
	 * @param entry scanner to catch input
	 * @param serverObject rmiInterface
	 * @param clientToInstanciate
	 * @return false if failed to connect or register or quit, true if succeed
	 * @throws RemoteException
	 * @throws SQLException
	 */
	private static boolean connectOrRegister(Scanner entry, Iserver serverObject,Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isMenuRunning = true;
		int connectionResult = 1;//retry
		int registerResult = 1;//retry
		int menuChoice = -1;
				
		while(isMenuRunning) {//connect menu loop
			
			
			
			journal.displayText("1- Connexion");
			journal.displayText("2- Creer un compte");
			journal.displayText("0- Quitter");
			
			try {
				menuChoice = entry.nextInt();
				entry.nextLine();
				if(menuChoice >= 0 && menuChoice <= 2) {
					isMenuRunning = false;
				}
			} catch(InputMismatchException e) {
				journal.clearTerminal();
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
	/**
	 * Function asking the client to register with username and password calls remote function register()
	 * Instantiate a client object with informations about the player
	 * Log the client if success
	 * @param clientToInstanciate client object filled with player informations
	 * @throws RemoteException raised during connection issue by RMI, SQLException raised calling check_user()
	 * @return 0 if abort try / 1 if register and login success
	 */
	private static int register(Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isRegisterMenuRunning = true;
		int registerResult;
		int retryOrQuit;
		String pseudo;
		String password;
		String confirmpassword;
		
		while(isRegisterMenuRunning) {//register Menu loop
			
			journal.displayText("____________ Créer ton compte ! ____________ ");
			journal.displayText("Pseudo => ");
			pseudo = entry.nextLine();
			
			if(serverObject.checks_user(pseudo) != 0) {//user already exist
				
				journal.displayText("Mot de passe => ");
				password = entry.nextLine();
				journal.displayText("Confirmation du mot de passe => ");
				confirmpassword = entry.nextLine();
				
				if(password.contentEquals(confirmpassword)) {//password and confimpassword are the same
					
					registerResult = serverObject.register(pseudo, password);
					
					if(registerResult == 1) { //DB register went well
						
						isRegisterMenuRunning = false;
						clientToInstanciate.SetPseudo(pseudo);
						
					}else {//an issue occured during DB register
						
						journal.clearTerminal();
						journal.displayText("Un probleme est survenu lors de la création du compte");
						journal.displayText("1- Réessayer");
						journal.displayText("0- Quitter");
						retryOrQuit = entry.nextInt();
						entry.nextLine();
						
						if(retryOrQuit == 0) {
							
							return 0; //fail
							
						}
					}
					
				} else {
					
					journal.displayText("Les mots de passe ne correspondent pas, réessayez");
					
				}
			}
			
		}
		journal.clearTerminal();
		journal.displayText("Votre compte a été créé nous vous avons connecté");
		return 1; //Success
	}
	/**
	 * Function asking the client to connect with username and password calls remote function login()
	 * Instantiate a client object with informations about the player
	 * @param clientToInstanciate client object filled with player informations
	 * @throws RemoteException raised during connection issue by RMI, 
	 * @throws SQLException raised calling check_user()
	 * @return 0 if abort try / 1 if login success
	 */
	private static int connect(Client clientToInstanciate) throws RemoteException, SQLException {
		boolean isConnectionMenuRunning = true;
		String pseudo;
		String password;
		int retryOrQuit;
		
		while(isConnectionMenuRunning) {
			
			journal.displayText("____________ CONNEXION ____________");
			journal.displayText("Pseudo => ");
			pseudo = entry.nextLine();
			journal.displayText("Mot de passe =>");
			password = entry.nextLine();
			clientToInstanciate.SetPseudo(pseudo);
			
				if(serverObject.login(clientToInstanciate, password).equals("0")) { //if server function login failed
					journal.displayText("/!\\ ------ Le login a échoué ------ /!\\");
					journal.displayText("1- Réessayer");
					journal.displayText("0- Quitter");
					retryOrQuit = entry.nextInt();
					entry.nextLine();
				
					if(retryOrQuit == 0) {//user choosed to quit
					
						return 0; //fail
					
					}
				
				} else {
				
					isConnectionMenuRunning = false;//login succeed
				
				}
		}
		return 1; //Success
		
	}
	private static void loadGame() {
		// TODO Auto-generated method stub
		
	}
	/**
	 * Duel Menu. Asks the player if hosting or joining game. calls startDuel or joinDuel in function
	 * 
	 * @throws RemoteException raised during connection issue by RMI
	 * @throws SQLException raised calling check_user()
	 */
	private static void defyPlayer() throws RemoteException, SQLException {
		
		int menuChoice = 0;
		boolean isDefyMenuRunning = true;
		boolean isInvalidPlayer;
		String gameManagerId = null;
		
		while(isDefyMenuRunning) {
			journal.displayText("____________ C'EST L'HEURE DU DU-DUEL ! ____________");
			journal.displayText("Voulez vous héberger une partie ou rejoindre un ami ?");
			journal.displayText("1- Héberger");
			journal.displayText("2- Rejoindre");
			journal.displayText("0- Quitter");
		
			try {
				menuChoice = entry.nextInt();
				entry.nextLine();
				
				if((menuChoice >= 0) && (menuChoice <= 2)) {
					
					isDefyMenuRunning = false;
				}
			}catch (InputMismatchException e){
				entry.nextLine();
				journal.displayTextError("/!\\ ------Erreur dans la saisie clavier ------ /!\\");
				
			}
		
			
		}
		
		switch(menuChoice) {
		case 0:
			return; //player choosed to quit
			
		case 1: //Host
			String opponentPseudo =null;
			journal.displayText("____________ Vous avez choisi d'héberger une partie ____________");
			isInvalidPlayer = true;
			
			while(isInvalidPlayer) {

				journal.displayText("Entrer le pseudo du joueur à inviter");
				journal.displayText("Ou quitter en entrant 0 =>");
				opponentPseudo = entry.nextLine();
				
				if(opponentPseudo == "0") {//User decided to quit
					
					return;
				
				}
				if(serverObject.checks_user(opponentPseudo) == 0) {//enemy user exists
					
					isInvalidPlayer = false;
					
				}else {
					
					journal.displayText("Ce joueur n'existe pas, réessayer");
				}
			}
			gameManagerId = serverObject.startDuel(playingClient,opponentPseudo);
			break;
			
			
		case 2://Challenger			
			gameManagerId = serverObject.joinDuel(playingClient);
			
			journal.displayText("____________ Vous avez choisi de rejoindre une partie ____________");
			
			if(gameManagerId == null) {
				
				journal.displayText("/!\\ ------ Vous n'avez reçue aucune invitation pour une partie ------ /!\\");
				return;
			}
			break;
			
		}
		
		playAGame(gameManagerId);
		
		
	}

	/**
	 * Launch server function startMatchaMing and start the game 
	 * @throws RemoteException
	 */
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
				journal.displayText("Message :");
				String msg = entry.nextLine();
				try {
					serverObject.sendMessage(gameManagerId, playingClient, msg, false);
				} catch (NullPointerException e) {
					journal.displayText("[INFO] : Votre adversaire à quitté la partie...");
					isGameOver = -2; 
				}
				break;
				
			/* Save the game */
			case 1:
				try {
					if(!serverObject.minimumPlayersAreConnected(gameManagerId)) {
						journal.displayText("[INFO] : Veuillez attendre l'arrivée de votre adversaire avant de jouer.");
					} else {						
						serverObject.save(gameManagerId, playingClient);
						isGameOver = -2;
					}
				} catch (NullPointerException e) {
					journal.displayText("[INFO] : Votre adversaire à quitté la partie...");
					isGameOver = -2;
				}
				break;
				
			/* Play a turn */ 
			case 2:
				boolean moveError = false;
				
				/* Loop verifying the validity of the player's input */
				while( !correctInput) {					
					origin = getMoveOptions("Selectionner une source pour votre personnage. Usage : LigneColonne (exemple: 43)", gameManagerId);
					if(origin >= 0) {							
						destination = getMoveOptions("Selectionner une destination pour votre personnage. Usage : LigneColonne (exemple: 43)", gameManagerId);
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
						journal.displayText("[INFO] : Mouvement validé");
						isGameOver = 0;
					} catch(NullPointerException e) {
						journal.displayText("[INFO] : Votre adversaire à quitté la partie...");
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
		try {
			serverObject.clientQuit(gameManagerId, playingClient);
		} catch (NullPointerException e) {
			
		}
		journal.displayText("[INFO] : Fin de la partie");
		if(isGameOver == 0) {
			journal.displayText("Félicitations " + playingClient.GetPseudo() + ", vous avez gagné !");
		} else if(isGameOver > 0) {
			journal.displayText("Dommage, vous avez perdu, vous ferez mieux la prochaine fois !");
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
				journal.displayTextError("/!\\ ------ Il faut rentrer une valeur numerique ------ /!\\");
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
					journal.displayText("/!\\ ------ Rentrer un nombre entre 00 et 77 ------ /!\\");
				}
			} catch(InputMismatchException e){
				journal.displayText("/!\\ ------ Rentrer un nombre entre 00 et 77 ------ /!\\");
				entry.nextLine();
			} catch(NullPointerException e) {
				journal.displayText("[INFO] : Votre adversaire à quitté la partie...");
				coordinates = -3;
				correctOrigin = true;
			}
			entry.nextLine();
		}
		return coordinates;
	}
	
}
