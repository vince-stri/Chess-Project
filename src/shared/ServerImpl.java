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

import server.model.GameManager;
import server.model.board.BoardShape;
import server.main.ClientWrapper;
import java.io.*;


@SuppressWarnings("serial")
public class ServerImpl extends UnicastRemoteObject implements Iserver {
	
	private ArrayList<GameManager> queueGM = new ArrayList<>();
	private ArrayList<String> queueIdGM = new ArrayList<>();
	private Map<String, GameManager> games = new HashMap<String, GameManager>();
	
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
		String query = "UPDATE account SET user_token='"+token+"' WHERE ida="+client.GetIdAccount()+";";
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
	public String disconnect(iClient client) throws RemoteException, SQLException {
		del_token(client);
		return null;
	}

	
	@Override
	public String startDuel(iClient client) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int checks_user(String pseudo) throws RemoteException, SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "SELECT COUNT(*) AS Total FROM account WHERE pseudo='" + pseudo + "';";
		System.out.println("Query : " + query);
		ResultSet res = stmt.executeQuery(query);
		res.next();
		if (res.getInt("Total")>0) {
			System.out.println("L'utilisateur " + pseudo +" existe d�j�");
			return 0;
		}
		db.close();
		return 1;
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
			GameManager gm = new GameManager(BoardShape.CHESS, "saves/" + gm_id, 2);
			gm.addClient(cwplayer);
			
			// Put the Game manager in standby
			queueGM.add(gm);
			queueIdGM.add(gm_id);
			
			// Add the GM to the list of current games
			games.put(gm_id, gm);
			System.out.println("The newly created GameManager id: " + gm_id);
			gm.setUpBattle();
			cwplayer.displayBoard(gm.getBoard());
			cwplayer.displayText("Vous jouez l'equipe du cote obscure de la force");
			return gm_id;
		} else {
			int idx_GM = queueGM.size()-1;
			GameManager gm = queueGM.get(idx_GM);
			gm.addClient(cwplayer);
			queueGM.remove(idx_GM);
			String gm_id = queueIdGM.remove(idx_GM);
			System.out.println("The newly created GameManager id: " + gm_id);
			gm.setPlayersToArmies();
			cwplayer.displayBoard(gm.getBoard());
			cwplayer.displayText("Vous jouez l'equipe du cote clair de la force");
			return gm_id;
		}
	}
	
	public boolean isItPlayerSTurn(String GMId, iClient client) throws RemoteException, NullPointerException {
		return client.equals(games.get(GMId).getPlayingAmry().getClientWrapper().getClient());
	}
	
	/**
	 * 
	 */
	public boolean isAGoodMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException {
		if(!this.isItPlayerSTurn(GMId, client)) {
			client.PostMsg("It is not your turn to play");
			return false;
		}
		int srcX = source / 10;
		int srcY = source % 10;
		int destX = destination / 10;
		int destY = destination % 10;
		return games.get(GMId).isAGoodMove(srcX, srcY, destX, destY, new ClientWrapper(client));
	}
	
	/**
	 * 
	 * @param source
	 * @param destination
	 * @param GMId
	 * @param client
	 * @return
	 * @throws RemoteException
	 */
	public int playMove(int source, int destination, String GMId, iClient client) throws RemoteException, NullPointerException {
		if(!this.isItPlayerSTurn(GMId, client)) {
			client.PostMsg("It is not your turn to play");
			return -1;
		}
		int srcX = source / 10;
		int srcY = source % 10;
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
	
	/**
	 * 
	 */
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
		sendMessage(GMId, client, "Your opponent decided to save the game. You can ask later for continue it", true);
		GameManager gm = games.get(GMId);
		gm.save();
		clientQuit(GMId, client);
	}
	
	public void sendMessage(String GMId, iClient sender, String msg, boolean systemMsg) throws RemoteException, NullPointerException {
		GameManager gm = games.get(GMId);
		ClientWrapper [] players = gm.getPlayers();
		for(ClientWrapper i : players) { // The loop is used supposing the program can evolve in the future, involving more than 2 players within a game
			if(i != null) {
				if(! i.getClient().equals(sender)) {
					if(systemMsg) {
						i.displayText("System: " + msg);
					} else {
						i.displayText(sender.GetPseudo() + ": " + msg);					
					}
				}				
			}
		}
	}
	
	public void clientQuit(String GMId, iClient client) throws RemoteException, NullPointerException {
		GameManager gm = games.get(GMId);
		sendMessage(GMId, client, "Your opponent quitted the game. You must find another game", true);
		removeGMFromList(GMId);
		
	}
	
	private void removeGMFromList(String GMId) {
		games.remove(GMId);
	}

}