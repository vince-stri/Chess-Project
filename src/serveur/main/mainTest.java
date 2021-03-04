package serveur.main;

import java.rmi.RemoteException;
import java.sql.SQLException;



public class mainTest {
	public static void main(String[] args) throws RemoteException, SQLException {
		ServerImpl servgame = new ServerImpl();
		//Connection con = servgame.connect_db();
		servgame.register("usertest","passwordtest");
	}
}
