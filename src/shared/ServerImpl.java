package shared;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import server.model.Coordinates;
import server.model.GameManager;
import server.model.board.BoardShape;
import server.main.ClientWrapper;
import java.io.*;

/**
 * Gives remote actions to the clients
 * @version 1.0
 * @author vincent acila
 */
public class ServerImpl extends UnicastRemoteObject implements Iserver {
	
	private ArrayList<GameManager> queueGM = new ArrayList<>();
	private Map<String, GameManager> games = new HashMap<String, GameManager>();
	private Map<String, GameManager> queueDuel = new HashMap<String, GameManager>();
	
	
	public ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get database configuration from external config file
	 * @return conf array
	 */
	private String[] get_conf() {
		BufferedReader file = null;
		String[] conf = new String[5];
	    String line;
		try {
			file = new BufferedReader(new FileReader(".db_connect"));
		}
	    catch(FileNotFoundException exc) {
	    	System.out.println("An error was occured reading the config file");
	    	return null;
	    }
	    try {
	    	// 5 conf variables : url, port, db user, db password, db name
			for (int i = 0; (line = file.readLine()) != null; i++) {
				conf[i] = line;
			}
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Config file may be empty");
			return null;
		}
	    
	    return conf;
	  }
	

	private Connection connect_db() {
		Connection con = null;
		String[] conf;
		if ((conf=get_conf()) == null) {
			return null;
		}
		try {
			
			String server = "jdbc:postgresql://" + conf[0] + ":" + conf[1] + "/" + conf[2];
			Properties prop = new Properties();
			prop.setProperty("user", conf[3]);
			prop.setProperty("password", conf[4]);
			prop.setProperty("ssl", "false");
		    con = DriverManager.getConnection(server, prop);
		} catch (SQLException e) {
			
	        System.out.println("Connection Failed! Check output console");
	        e.printStackTrace();
	        return null;
	    }
		
		if (con != null) {
			System.out.println("Connected to databse chessgame...");
		}
		else{
			System.out.println("Connection failed, try again bro");
		}
		
		return con;
	}
	
	private String gen_token(iClient client) throws SQLException, RemoteException {
		String token = UUID.randomUUID().toString();
		token = token.replace("-", "");
		System.out.println("Your connection token is " + token);
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		client.SetToken(token);
		String query = "UPDATE account SET user_token='"+ token +"' WHERE ida="+client.GetIdAccount()+";";
		System.out.println("Query : " + query);
		if (stmt.executeUpdate(query)==1) {
			System.out.println("Token updated in database");
		}
		else { 
			System.out.println("An error has occured updating user's token");
		}
		stmt.close();
		db.close();
		return token;
	}
	
	
	private void del_token(iClient client) throws SQLException, RemoteException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "UPDATE account SET user_token='0' WHERE ida="+client.GetIdAccount()+";";
		System.out.println("Query : " + query);
		if (stmt.executeUpdate(query)==1) {
			System.out.println("Token updated in database");
		}
		else { 
			System.out.println("An error has occured updating user's token");
		}
		stmt.close();
		db.close();
	}

	public String login(iClient client, String password) throws RemoteException, SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "SELECT ida FROM account WHERE pseudo='" + client.GetPseudo() + "' AND password='"+password+"';";
		System.out.println("Query : " + query);
		
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			System.out.println(res.getInt("ida") + " user connected");
			client.setGMId(load_Idgm(client.GetPseudo()));
			client.SetIdAccount(res.getInt("ida"));
			
			return gen_token(client);
		}
		
		System.out.println("Authentification failed, please try again."); 
		System.out.println("If you are not already registered, you can do it now.");
		res.close();
		stmt.close();
		db.close();
		
		return "0";
		
	}
	
	@Override
	public void disconnect(iClient client) throws RemoteException, SQLException {
		del_token(client);
	}
	
	@Override
	public String joinDuel(iClient player) throws RemoteException {
		ClientWrapper cwplayer = new ClientWrapper(player);
		GameManager gm;
		if ((gm = queueDuel.get(player.GetPseudo())) != null) {
			gm.addClient(cwplayer);
			System.out.println("The newly created GameManager id: " + gm.getIdGM());
			gm.setPlayersToArmies();
			cwplayer.displayBoard(gm.getBoard());
			cwplayer.displayInfo("____________ Vous jouez l'equipe du cote clair de la force ____________");
			queueDuel.remove(player.GetPseudo(), gm);
			return gm.getIdGM();
		}
		return null;
	}
	
	@Override
	public String startDuel(iClient player, String opponentPlayer) throws RemoteException {
		ClientWrapper cwplayer = new ClientWrapper(player);
		// Create an unique ID for the GM
		String gm_id = UUID.randomUUID().toString();
		gm_id = gm_id.replace("-", "");
					
		// Create GameManager and add the player to it
		GameManager gm = new GameManager(BoardShape.CHESS, gm_id, 2);
		gm.addClient(cwplayer);
		gm.setIdGM(gm_id);
		
		// Put the Game manager in standby
		queueDuel.put(opponentPlayer, gm);
		
		// Add the GM to the list of current games
		games.put(gm_id, gm);
		System.out.println("The newly created GameManager id: " + gm_id);
		gm.setUpBattle();
		cwplayer.displayBoard(gm.getBoard());
		cwplayer.displayInfo("____________ Vous jouez l'equipe du cote obscure de la force ____________");
		cwplayer.displayInfo("En attente de " + opponentPlayer + "...");
		return gm_id;
	}

	
	public int checks_user(String pseudo) throws RemoteException, SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "SELECT COUNT(*) AS Total FROM account WHERE pseudo='" + pseudo + "';";
		System.out.println("Query : " + query);
		ResultSet res = stmt.executeQuery(query);
		res.next();
		if (res.getInt("Total")>0) {
			System.out.println("L'utilisateur " + pseudo +" existe déjà");
			return 0;
		}
		db.close();
		return 1;
	}
	
	private String load_Idgm(String pseudo) throws SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "SELECT idgm_save FROM account WHERE pseudo='" + pseudo + "';";
		System.out.println("Query : " + query);
		ResultSet res = stmt.executeQuery(query);
		stmt.close();
		if (res.next()) {
			db.close();
			return res.getString("idgm_save");
		}
		db.close();
		return null;
	}
	
	private void save_Idgm(String pseudo, String idgm) throws SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "UPDATE TABLE account SET idgm_save='"+ idgm +"' WHERE pseudo='" + pseudo + "';";
		if (stmt.executeUpdate(query)==1) {
			System.out.println("IDGM for " + pseudo + "has been saved");
		}
		else {
			System.out.println("An error has occured saving IDGM for " + pseudo);
		}
		stmt.close();
		db.close();
	}
	
	@Override
	public int register(String pseudo, String password) throws RemoteException, SQLException {
		if (checks_user(pseudo)==0) {
			return 0;
		}
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "INSERT INTO account(pseudo,password) VALUES ('"+ pseudo +"', '"+ password +"');";
		if (stmt.executeUpdate(query)==1) {
			System.out.println("User has been created");
		}
		else {
			System.out.println("An error has occured creating user");
		}
		stmt.close();
		db.close();
		return 1;
	}
	
	/**
	 * Initialize a new game if there is no player in standby or add the player to an existing game.
	 * @param player who launched a game
	 * @return GameManagerID (String) that refers to the GM.  
	 */
	@Override
	public String startMatchMaking(iClient player) throws RemoteException {
		//Initialize CLientWrapper tab for the game manager
		ClientWrapper cwplayer = new ClientWrapper(player);
		// If there is already a player in standby
		if (queueGM.isEmpty()) {
			// Create an unique ID for the GM
			String gm_id = UUID.randomUUID().toString();
			gm_id = gm_id.replace("-", "");
						
			// Create GameManager and add the player to it
			GameManager gm = new GameManager(BoardShape.CHESS, gm_id, 2);
			gm.addClient(cwplayer);
			gm.setIdGM(gm_id);
			// Put the Game manager in standby
			queueGM.add(gm);
			
			// Add the GM to the list of current games
			games.put(gm_id, gm);
			System.out.println("The newly created GameManager id: " + gm_id);
			gm.setUpBattle();
			cwplayer.displayBoard(gm.getBoard());
			cwplayer.displayInfo("____________ Vous jouez l'equipe du cote obscur de la force ____________");
			cwplayer.displayInfo("En attente d'un autre joueur...");
			return gm_id;
			
		} else {
			int idx_GM = queueGM.size()-1;
			GameManager gm = queueGM.get(idx_GM);
			gm.addClient(cwplayer);
			queueGM.remove(idx_GM);
			System.out.println("The newly created GameManager id: " + gm.getIdGM());
			gm.setPlayersToArmies();
			cwplayer.displayBoard(gm.getBoard());
			cwplayer.displayInfo("____________ Vous jouez l'equipe du cote clair de la force ____________");
			return gm.getIdGM();
		}
	}
	
	public boolean isItPlayerSTurn(String GMId, iClient client) throws RemoteException, NullPointerException {
		return client.equals(games.get(GMId).getPlayingAmry().getClientWrapper().getClient());
	}
	
	public int isAGoodMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException {
		if(!this.isItPlayerSTurn(GMId, client)) {
			client.PostInfo("/!\\ ------ Ce n'est pas à ton tour de jouer ------ /!\\");
			return -1;
		}
		int srcX = source / 10; // get ordinate
		int srcY = source % 10; // get abscissa
		int destX = destination / 10;
		int destY = destination % 10;
		if(games.get(GMId).isAGoodMove(srcX, srcY, destX, destY, new ClientWrapper(client))) {
			return 0;
		} else {
			return 1;
		}
	}

	public int playMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException {
		if(!this.isItPlayerSTurn(GMId, client)) {
			client.PostInfo("/!\\ ------ Ce n'est pas à ton tour de jouer ------ /!\\");
			return -1;
		}
		
		int srcX = source / 10; // get ordinate
		int srcY = source % 10; // get abscissa
		int destX = destination / 10;
		int destY = destination % 10;
		
		GameManager gm = games.get(GMId); 
		int ret = gm.playMove(srcX, srcY, destX, destY, new ClientWrapper(client));
		ClientWrapper [] players = gm.getPlayers();
		for(ClientWrapper i : players) {
			i.displayBoard(gm.getBoard());
		}
		return ret;
	}
	
	public int isGameOver(String GMId, iClient client) throws RemoteException, NullPointerException {
		if(games.get(GMId).isWinner(new ClientWrapper(client))) {
			return 0;
		} else if(games.get(GMId).isLoser(new ClientWrapper(client))) {
			return 1;
		} else {
			return -1;
		}
	}

	public boolean minimumPlayersAreConnected(String GMId) throws RemoteException, NullPointerException {
		GameManager gm = games.get(GMId);
		return gm.isMinimumClientsConnected();
	}
	
	public void save(String GMId, iClient client) throws RemoteException, NullPointerException {
		sendMessage(GMId, client, "[INFO] : Votre adversaire a décidé de sauvegarder la partie", true);
		GameManager gm = games.get(GMId);
		ClientWrapper [] clients = gm.getPlayers();
		for(ClientWrapper i : clients) {
			i.getClient().setGMId(GMId);
			try {
				save_Idgm(i.getClient().GetPseudo(), GMId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		gm.save();
		clientQuit(GMId, client);
	}
	
	public String load(iClient client) throws RemoteException {
		String GMId = client.getGMId();
		if(isExists(GMId)) {
			GameManager gm = new GameManager(BoardShape.CHESS, GMId, 2);
			ClientWrapper cwplayer = new ClientWrapper(client); 
			gm.setIdGM(GMId);
			gm.load();
			gm.addClient(cwplayer);
			gm.setPlayingArmy();
			
			String [] pseudos = gm.getPseudos();
			if(client.GetPseudo() == pseudos[0]) {
				queueDuel.put(pseudos[1], gm);
			} else {
				queueDuel.put(pseudos[0], gm);
			}
			
			games.put(GMId, gm);
			System.out.println("The newly created GameManager id: " + GMId);
			cwplayer.displayBoard(gm.getBoard());
			cwplayer.displayInfo("Vous jouez l'equipe du cote obscure de la force");
			return gm.getIdGM();
		} else {
			client.PostInfo("La partie que vous essayez de charger n'existe pas");
			client.setGMId(null);
			return null;
		}
	}
	
	/**
	 * Tests if a specified game has been saved or not 
	 * @param GMId the GMId identifying the game
	 * @return true or false
	 */
	private boolean isExists(String GMId) {
		File f = new File("saves/" + GMId);
		return f.exists() && f.isFile();
	}
	
	public void sendMessage(String GMId, iClient sender, String msg, boolean systemMsg) throws RemoteException, NullPointerException {
		GameManager gm = games.get(GMId);
		ClientWrapper [] players = gm.getPlayers();
		for(ClientWrapper i : players) { // The loop is used supposing the program can evolve in the future, involving more than 2 players within a game
			if(i != null) {
				if(! i.getClient().equals(sender)) {
					if(systemMsg) {
						i.displayInfo(msg);
					} else {
						i.displayText(sender.GetPseudo() + ": " + msg);					
					}
				}				
			}
		}
	}
	
	public void clientQuit(String GMId, iClient client) throws RemoteException, NullPointerException {
		games.get(GMId);
		sendMessage(GMId, client, "[INFO] : Votre adversaire a quitté la partie", true);
		removeGMFromList(GMId);
		try {
			disconnect(client);
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Removes the given GameManager from the list of active's ones 
	 * @param GMId the string identifying the GameManager
	 */
	private void removeGMFromList(String GMId) {
		games.remove(GMId);
	}

}
