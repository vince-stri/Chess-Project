package server;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;


public class MainServer {
	
	public static void main(String[] args) {
		try {
			LocateRegistry.createRegistry(1099);
			ServerImpl server = new ServerImpl();
			String url = "rmi://" + InetAddress.getLocalHost().getHostAddress() + "/Chess-Project";
			System.out.println("Registry with : " + url);
			Naming.rebind("Chess-Project",server);
			
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
