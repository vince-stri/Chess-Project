package server.main;

import java.util.ArrayList;

import shared.Client;


public class QueueMatchMaking {
	public int max_size;
	private int nbPlayers;
	private ArrayList<Client> Players = new ArrayList<Client>();
	
	public QueueMatchMaking(int max_size) {
		this.max_size = max_size;
		this.nbPlayers = 0;
	}
	
	public synchronized void push(Client player) {
		while (nbPlayers >= max_size) {
			System.out.println("The maximum number of players has been reached");
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Players.add(player);
		nbPlayers++;
	}
	
}
