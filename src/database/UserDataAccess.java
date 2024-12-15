package database;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataAccess {
	Database db = Database.getInstance();
	
	public boolean getUserByEmail(String email) {
    	String query = "SELECT * FROM user WHERE Email = ?";
    	PreparedStatement ps = db.preparedStatment(query);
		
    	try {
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }
    
    public boolean getUserByUsername(String username) {
    	String query = "SELECT * FROM user WHERE Username = ?";
    	PreparedStatement ps = db.preparedStatment(query);
		
    	try {
			ps.setString(1, username);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
    }

    public boolean register(String username, String email, String password, int roleId) {

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

    public boolean login(String email, String password) {
        String query = "SELECT * FROM user WHERE email = ? AND password = ?";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String username = rs.getString("Username");
                int id = rs.getInt("UserId");
                int roleId = rs.getInt("RoleId");
                User user = new User(id, username, email, password, roleId);
                Session.getInstance().setLoggedInUser(user);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeProfile(String email, String username, String oldPassword, String newPassword) {
        String updateQuery = "UPDATE user SET Username = ?, Email = ?, Password = ? WHERE UserId = ?";
        int userId = Session.getInstance().getLoggedInUser().getId();
        User user = new User(userId, username, email, newPassword, Session.getInstance().getLoggedInUser().getRole());
        
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(updateQuery)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, newPassword);
            ps.setInt(4, userId);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected > 0) {
            	Session.getInstance().setLoggedInUser(user);
            	return true;
            };

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
