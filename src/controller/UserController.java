package controller;

import model.User;
import database.UserDataAccess;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view_controller.ViewController;
import database.Session;

public class UserController {

    private UserDataAccess userDataAccess;

    public UserController() {
        userDataAccess = new UserDataAccess();
    }
    
    //////////////////////////////Register User Start Here//////////////////////////////

    public boolean registerUser(String username, String email, String password, String role) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            return false;
        }

        return userDataAccess.registerUser(username, email, password, role);
    }

    //////////////////////////////Login User Start Here//////////////////////////////
    
    public User login(String email, String password) {
		
		if (email.isEmpty() || password.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Fill all the required Credentials");
			a.show();
			return null;
		}
		
		User user = userDataAccess.login(email, password);

        if (user == null) {
            Alert a = new Alert(AlertType.ERROR);
            a.setHeaderText("Wrong Credentials");
            a.show();
        }
        return user;
	}
    
    //////////////////////////////Update Profile User Start Here//////////////////////////////

    public boolean updateUser(User user) {
        return userDataAccess.updateUser(user);
    }
}
