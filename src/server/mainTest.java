package server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

import model.Client;

public class mainTest {
	public static void main(String[] args) throws RemoteException, SQLException {
		ServerImpl servgame = new ServerImpl();
		//Connection con = servgame.connect_db();
		servgame.register("usertest","passwordtest");
	}
}
