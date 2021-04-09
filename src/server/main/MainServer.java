package server.main;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import shared.ServerImpl;


public class MainServer {
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			ServerImpl server = new ServerImpl();
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/ChessProject";
			System.out.println("Registry with : " + url);
			Naming.rebind(url, server);
			System.out.println("Server launched");
						
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
		      e.printStackTrace();
	    } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
