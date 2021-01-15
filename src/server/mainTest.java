package server;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public class mainTest {
	public static void main(String[] args) throws RemoteException, SQLException {
		Server_implt servgame = new Server_implt();
		//Connection con = servgame.connect_db();
		servgame.login("vinvin", "mdpvinvin");
	}
}
