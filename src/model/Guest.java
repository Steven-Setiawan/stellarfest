package model;

public class Guest extends User{

	public Guest(int id, String username, String email) {
		super(id, username, email);
	}

	public Guest(int id, String username, String email, String password, int role) {
		super(id, username, email, password, role);
	}

}
