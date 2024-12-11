package database;

import model.User;

public class Session {
    private static Session instance;
    
    private User loggedInUser;
    
    private Session() {}
    
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public boolean isUserLoggedIn() {
        return this.loggedInUser != null;
    }

    public void logout() {
        this.loggedInUser = null;
    }
}
