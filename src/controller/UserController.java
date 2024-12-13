package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import database.Database;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.User;

public class UserController {

    public boolean registerUser(String username, String email, String password, String role) {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role == null) {
            return false;
        }
        
        int roleId =0;
        
        if(role.equalsIgnoreCase("Admin")) {
        	roleId = 1;
        }else if(role.equalsIgnoreCase("Guest")) {
        	roleId = 0;
        }else if(role.equalsIgnoreCase("Vendor")) {
        	roleId = 3;
        }else if(role.equalsIgnoreCase("Event Organizer")) {
        	roleId = 2;
        }

        try (Connection connection = Database.getInstance().getConnection()) {
            String sql = "INSERT INTO user (Username, Email, Password, RoleId) VALUES (?, ?, ?, ?)";
            
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);
                preparedStatement.setInt(4, roleId);

                int rowsAffected = preparedStatement.executeUpdate();
                return rowsAffected > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();  
            return false;
        }
        
    }
    
    public User login(String email, String password) {
		
		if (email.isEmpty() || password.isEmpty()) {
			Alert a = new Alert(AlertType.ERROR);
			a.setHeaderText("Fill all the required Credentials");
			a.show();
			return null;
		}
		
		try {
			String query = "SELECT * FROM user WHERE email = ? AND password = ?";
			PreparedStatement ps = Database.getInstance().preparedStatment(query);
			ps.setString(1, email);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				String username = rs.getString("Username");
				int id = rs.getInt("UserId");
				int roleId = rs.getInt("RoleId");
				
				return new User(id, username, email, password, roleId);
			}else {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Wrong Credentials");
				a.show();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
    
    public boolean updateUser(User user) {
        String updateQuery = "UPDATE user SET Username = ?, Email = ?, Password = ? WHERE UserId = ?";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(updateQuery)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getId());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
}
