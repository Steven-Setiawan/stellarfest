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
    
    
    public boolean getUserByEmail(String email) {
    	return userDataAccess.getUserByEmail(email);
    }
    
    public boolean getUserByUsername(String username) {
    	return userDataAccess.getUserByUsername(username);
    }
    
    public boolean checkRegisterInput(String username, String email, String password) {
    	if(username.isEmpty() || email.isEmpty() || password.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("All Field cannot Blank");
	        alert.showAndWait();
	        return false;
    	}else {
    		// Check Email is unique or not
        	if(userDataAccess.getUserByEmail(email)) {
        		//if not unique
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Email must be unique");
    	        alert.showAndWait();
    	        return false;
        	}
        	// Check Username is unique or not
        	if(userDataAccess.getUserByUsername(username)) {
        		//if not unique
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Username must be unique");
    	        alert.showAndWait();
    	        return false;
        	}
        	// check new password length 
        	if(password.length() < 5) {
        		//if not more than or equal 5
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Password must at least 5 characters");
    	        alert.showAndWait();
    	        return false;
        	}
    	}
    	
    	return true;
    }
    //////////////////////////////Register User Start Here//////////////////////////////

    public boolean register(String username, String email, String password, String role) {
        return userDataAccess.register(username, email, password, getRoleId(role));
    }

    //////////////////////////////Login User Start Here//////////////////////////////
    
    public boolean checkLoginInput(String email, String password) {
    	if (email.isEmpty() || password.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Fill all the required Credentials");
			a.show();
			return false;
		}
    	
    	return true;
    }
    
    public boolean login(String email, String password) {
        return userDataAccess.login(email, password);
	}
    
    //////////////////////////////Update Profile User Start Here//////////////////////////////

    public boolean changeProfile(String email, String username, String oldPassword, String newPassword) {
        return userDataAccess.changeProfile(email, username, oldPassword, newPassword);
    }
    
    public boolean checkChangeProfileInput(String username, String email, String oldPassword, String newPassword) {
    	// Check empty field
    	if(username.isEmpty() || email.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("All Field cannot Blank");
	        alert.showAndWait();
	        return false;
    	}else {
    		// Check Email is unique or not
        	if(userDataAccess.getUserByEmail(email)) {
        		//if not unique
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Email must be unique");
    	        alert.showAndWait();
    	        return false;
        	}
        	// Check Username is unique or not
        	if(userDataAccess.getUserByUsername(username)) {
        		//if not unique
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("Username must be unique");
    	        alert.showAndWait();
    	        return false;
        	}
        	// check old password must same with curr password
        	if(Session.getInstance().getLoggedInUser().getPassword() != oldPassword) {
        		//if not the same
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("old Password is incorrect");
    	        alert.showAndWait();
    	        return false;
        	}
        	// check new password length 
        	if(newPassword.length() < 5) {
        		//if not more than or equal 5
        		Alert alert = new Alert(AlertType.WARNING);
    			alert.setTitle("Failed");
    	        alert.setHeaderText(null);
    	        alert.setContentText("New Password must at least 5 characters");
    	        alert.showAndWait();
    	        return false;
        	}
    	}
    	return true;
    }
    
    private int getRoleId(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                return 1;
            case "guest":
                return 0;
            case "vendor":
                return 3;
            case "event organizer":
                return 2;
            default:
                return 0;
        }
    }
}
