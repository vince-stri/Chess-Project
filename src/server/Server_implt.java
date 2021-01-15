package server;

import java.rmi.RemoteException;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.io.*;
import java.util.Properties;
import java.util.Scanner;
import java.util.UUID;

public class Server_implt implements Iserver{
	
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
	
	private String gen_token(int id) throws SQLException {
		String token = UUID.randomUUID().toString();
		token = token.replace("-", "");
		System.out.println("Your connection token is " + token);
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "UPDATE account SET user_token='"+token+"' WHERE ida="+id+";";
		System.out.println("Query : " + query);
		if (stmt.executeUpdate(query)==1) {
			System.out.println("Token updated in database");
		}
		else { 
			System.out.println("An error has occured updating user's token");
		}
		return token;
	}
	
	private void del_token(int id) throws SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "UPDATE account SET user_token='0' WHERE ida="+id+";";
		System.out.println("Query : " + query);
		if (stmt.executeUpdate(query)==1) {
			System.out.println("Token updated in database");
		}
		else { 
			System.out.println("An error has occured updating user's token");
		}
	}
	
	@Override
	public String login(String login, String password) throws RemoteException, SQLException {
		Connection db = connect_db();
		Statement stmt = db.createStatement();
		String query = "SELECT ida FROM account WHERE pseudo='" + login + "' AND password='"+password+"';";
		System.out.println("Query : " + query);
		
		ResultSet res = stmt.executeQuery(query);
		if (res.next()) {
			System.out.println(res.getInt("ida") + " user connected");
			return gen_token(res.getInt("ida"));
		}
		
		System.out.println("Authentification failed, please try again."); 
		System.out.println("If you are not already registered, you can do it now.");
		return "0";
		
	}
	
	@Override
	public String disconnect() throws RemoteException {
		// TODO Auto-generated method stub
		//del_token(id);
		return null;
	}

	@Override
	public String startMatchMaking() throws RemoteException {
		
		return null;
	}
	

	@Override
	public String startDuel() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String register() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
