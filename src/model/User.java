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

    public User(int id, String username, String email, int role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.password = "";
    }

    public User(int id, String username, String email) {
		this.id = id;
		this.username = username;
		this.email = email;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole() {
        return role;
    }
    
    public String getRoleName() {
    	if(this.role == 0) {
    		return "Guest";
    	}else if(this.role == 1) {
    		return "Admin";
    	}else if(this.role == 2) {
    		return "Event Organizer";
    	}
    	return "Vendor";
    }

    public void setRole(int role) {
        this.role = role;
    }
}
