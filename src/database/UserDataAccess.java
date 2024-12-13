package database;

import model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDataAccess {

    public boolean registerUser(String username, String email, String password, String role) {
        int roleId = getRoleId(role);

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
                return new User(id, username, email, password, roleId);
            }
        } catch (SQLException e) {
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
