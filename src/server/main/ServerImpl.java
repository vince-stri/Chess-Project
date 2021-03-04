package server.main;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

import server.model.GameManager;
import server.model.board.BoardShape;
import shared.Client;



public class ServerImpl extends UnicastRemoteObject implements Iserver{
	
	private ArrayList<Client> queue = new ArrayList<>();
	
	
	protected ServerImpl() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}

	public Connection connect_db() {
		Connection con = null;
		try {	
			String server = "jdbc:postgresql://192.168.0.201:5432/chessgame";
			Properties prop = new Properties();
			prop.setProperty("user", "adm-chess");
			prop.setProperty("password", "chessproject");
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
	
	private void initializeGame(Client j1, Client j2) {
		ClientWrapper[] tab_cw = new ClientWrapper[2];
		tab_cw[0]= new ClientWrapper(j1);
		tab_cw[0]= new ClientWrapper(j2);
		
		GameManager gm = new GameManager(BoardShape.CHESS, "saves/allGame", tab_cw);
        gm.startGame();
       // return gm;
	}
	
	private void addToQueue(Client j) {
		queue.add(j);
		if (queue.size()==2) {
			System.out.println(queue.get(0).getPseudo() + " & " + queue.get(1).getPseudo() + " are ready to play...");
			initializeGame(queue.get(0), queue.get(1));
		}
	}
	
	
	

	@Override
	public String startDuel() throws RemoteException {
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
			System.out.println("L'utilisateur " + pseudo +" existe déjà");
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
	public String startMatchMaking() throws RemoteException {
		// TODO Auto-generated method stub
		//addToQueue(j);
		return null;
	}
	

}
