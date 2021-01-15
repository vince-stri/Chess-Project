package model;

import java.rmi.Naming;

public class Client {
	
	private int idAccount;
	private String pseudo;
	private String token;
	
	
	protected Client(int idAccount, String pseudo, String token) {
		this.idAccount = idAccount;
		this.pseudo = pseudo;
		this.token = token;
	}
	
}
