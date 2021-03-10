package server.main;


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
import shared.Client;
import java.io.*;


public class ServerImpl extends UnicastRemoteObject implements Iserver{
	
	private ArrayList<Client> queue = new ArrayList<>();
	private Map<String, GameManager> games = new HashMap<String, GameManager>();
	
	protected ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Get database configuration from external config file
	 * @return conf array
	 */
	public String[] get_conf() {
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
	
	public Connection connect_db() {
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
	
	private String gen_token(Client client) throws SQLException {
		String token = UUID.randomUUID().toString();
		token = token.replace("-", "");
		System.out.println("Your connection token is " + token);
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		client.setToken(token);
		String query = "UPDATE account SET user_token='"+token+"' WHERE ida="+client.getIdAccount()+";";
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
	
	private void del_token(Client client) throws SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "UPDATE account SET user_token='0' WHERE ida="+client.getIdAccount()+";";
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
	
	@Override
	public String login(Client client, String password) throws RemoteException, SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "SELECT ida FROM account WHERE pseudo='" + client.getPseudo() + "' AND password='"+password+"';";
		System.out.println("Query : " + query);
		
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			System.out.println(res.getInt("ida") + " user connected");
			client.setIdAccount(res.getInt("ida"));
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
	public String disconnect(Client client) throws RemoteException, SQLException {
		// TODO Auto-generated method stub
		del_token(client);
		return null;
	}
	
	private String initializeGame(Client j1, Client j2) {
		ClientWrapper[] tab_cw = new ClientWrapper[2];
		tab_cw[0]= new ClientWrapper(j1);
		tab_cw[0]= new ClientWrapper(j2);
		
		GameManager gm = new GameManager(BoardShape.CHESS, "saves/allGame", tab_cw);
		return null;
       // return gm;
	}
	
	private void addToQueue(Client j) {
		queue.add(j);
		if (queue.size()==2) {
			System.out.println(queue.get(0).getPseudo() + " & " + queue.get(1).getPseudo() + " are ready to play...");
			String token = initializeGame(queue.get(0), queue.get(1));
		}
	}
	
	

	@Override
	public String startDuel(Client client) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int checks_user(String pseudo) throws SQLException {
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
		System.out.println(query);
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

	@Override
	public String startMatchMaking(Client client) throws RemoteException {
		// TODO Auto-generated method stub
		//addToQueue(j);
		return null;
	}

	
	public boolean isAGoodMove(int source, int destination, String GMId, Client client) throws RemoteException {
		int srcX = source / 10;
		int srcY = source % 10;
		int destX = destination / 10;
		int destY = destination % 10;
		return games.get(GMId).isAGoodMove(srcX, srcY, destX, destY, new ClientWrapper(client));
	}
	public int playMove(String infos, String GMId, Client client) throws RemoteException {
		return 0;
	}
	public int isGameOver(String GMId, Client client) throws RemoteException {
		return 0;
	}

}