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
	
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException, SQLException {
		// TODO Auto-generated method stub
		journal = new CLI();
		Iserver serverObject;
		try {
			serverObject = (Iserver) Naming.lookup("rmi://localhost:1099/ChessProject");
		}catch(ConnectException ex) {
			journal.displayTextError("Le serveur du jeu ne r�pond pas retentez plus tard");
			return;
		}
		boolean connectionSuccess = false;
		Scanner entry = new Scanner(System.in);
		Client playingClient =new Client(-1, null, null, journal);
		
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
			journal.displayText("Conection");
			journal.displayText("entrez votre pseudo");
			pseudo = entry.nextLine();
			journal.displayText("entrez votre mot de passe");
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
		boolean correctInput,correctOrigin,correctDestination;
		int isGameOver = -1;
		int origin = -1;
		int destination = -1;
		int moveMessage;
		int action = -1;
		gameover:
			while(isGameOver == -1) {
				correctInput = false;
				while(correctInput == false) {
					correctOrigin = false;
					boolean correctAction = false;
					while(!correctAction) {
						try {
							journal.displayText("3 - Quitter\n2 - Jouer un coup\n1 - Sauvegarder la partie\n0 - Envoyer un message");
							action = entry.nextInt();
							if(action >= 0 && action < 4) {
								correctAction = true;
							}
						} catch (InputMismatchException e) {
							journal.displayTextError("Il faut rentrer une valeur numerique");
							entry.nextLine();
						}
						
					}
					if(action == 1) {
						serverObject.save(gameManagerId, playingClient);
						break gameover;						
					} else if(action == 0) {
						journal.displayText("Votre message :");
						entry.nextLine();
						String msg = entry.nextLine();
						serverObject.sendMessage(gameManagerId, playingClient, msg);
						continue gameover;							
					} else if(action == 3) {
						//serverObject.clientQuit(gameManagerId, playingClient);
						break gameover;
					}
					while(correctOrigin == false) {
						try {
							journal.displayText("Selectionnez un personnage � jouer. Tapez la ligne puis la colonne (exemple: 34)");
							origin = entry.nextInt();
							if(!serverObject.minimumPlayersAreConnected(gameManagerId)) {
								journal.displayText("[INFO] Veuillez attendre l'arriv�e de votre adversaire avant de jouer.");
								continue;
							}
							if(serverObject.isGameOver(gameManagerId,playingClient) != -1) {
								break gameover;
							}
							if(origin > -1 && origin < 78) {
								correctOrigin = true;
							}else {
								journal.displayText("Rentrez un nombre entre 00 et 77");
							}
						}catch (InputMismatchException ex){
							journal.displayText("Rentrez un nombre entre 00 et 77");
							entry.nextLine();
						}
						
					}
					correctDestination = false;
					while(correctDestination == false) {
						try {
							journal.displayText("selectionnez une destination pour votre personnage. Tapez la ligne puis la colonne (exemple: 43)");
							destination = entry.nextInt();
							if(serverObject.isGameOver(gameManagerId,playingClient) != -1) {
								break gameover;
							}
							if(destination > -1 && destination < 78) {
								correctDestination = true;
							}else {
								journal.displayText("Saisie de destination non valide. r��ssayez");
							}
						}catch (InputMismatchException ex){
							journal.displayText("Votre saisie est invalide r��ssayez");
							entry.nextLine();
						}
						
					}
					correctInput = serverObject.isAGoodMove(origin,destination,gameManagerId,playingClient);
				}
				moveMessage = serverObject.playMove(origin,destination,gameManagerId,playingClient);
				journal.displayText("Mouvement valid�");
				isGameOver = serverObject.isGameOver(gameManagerId,playingClient);			
			}
		//affichage du message de victoire ou de d�faite
		if(isGameOver == 0) {
			journal.displayText("Vous avez Gagn� !");
		} else {
			journal.displayText("Vous avez Perdu !");
		}
	}
	
}
