package shared;


import java.rmi.Naming;

public class Client {
	
	private int idAccount;
	private String pseudo;
	private String token;
	
	
	public Client(int idAccount, String pseudo, String token) {
		this.setIdAccount(idAccount);
		this.setPseudo(pseudo);
		this.setToken(token);
	}


	public int getIdAccount() {
		return idAccount;
	}


	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}


	public String getPseudo() {
		return pseudo;
	}


	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	
}
