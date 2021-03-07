package server.main;

import java.rmi.RemoteException;
import java.sql.SQLException;

import shared.Client;



public class mainTest {
	public static void main(String[] args) throws RemoteException, SQLException {
		ServerImpl servgame = new ServerImpl();
		//Connection con = servgame.connect_db();
		Client c = new Client(0,"usertest",null);
		servgame.login(c,"passwordtest");
		System.out.println("done");
	}
}
