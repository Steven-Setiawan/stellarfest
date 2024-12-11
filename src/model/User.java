package model;

public class User {

	private int id;
	private String username;
	private String email;
	private String password;
	private int role;
	
	public User(int id, String username, String email, String password, int role) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public int getRole() {
		return role;
	}
}
