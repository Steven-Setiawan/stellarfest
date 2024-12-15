package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Event;
import model.Guest;
import model.User;
import model.Vendor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDataAccess {
	
	private Database db = Database.getInstance();
	
	public List<User> getGuestByTransactionId(int eventId){
		List<User> guestList = new ArrayList<User>();
		String query = "SELECT u.UserId, Username, Email FROM user u "
					+ "JOIN invitation i ON u.UserId = i.UserId "
					+ "WHERE u.RoleId = 0 AND i.EventId = ?";
		
		PreparedStatement ps = db.preparedStatment(query);
		try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new Guest(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"));
				
				guestList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return guestList;
	}
	
	public List<User> getVendorByTransactionId(int eventId){
		List<User> vendorList = new ArrayList<User>();
		
		String query = "SELECT u.UserId, Username, Email FROM user u "
				+ "JOIN invitation i ON u.UserId = i.UserId "
				+ "WHERE u.RoleId = 3 AND i.EventId = ?";
	
		PreparedStatement ps = db.preparedStatment(query);
	
		try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new Vendor(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"));
				
				vendorList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vendorList;
	}
	
	public Event viewEventDetails(int eventId) {
		String query = "SELECT * FROM eventdetails WHERE EventId = ?";
		PreparedStatement ps = db.preparedStatment(query);
		Event event = null;
		try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				event = new Event(rs.getInt("EventId"), rs.getString("EventName"),
						rs.getString("EventDate"), rs.getString("EventLocation"), rs.getString("EventDescription"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return event;
	}

    //////////////////////////////Event Organizer Create Event Start Here//////////////////////////////

    public int createEventHeader(int eventOrganizerId) {
        String query = "INSERT INTO eventheader (EventOrganizerId) VALUES (?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, eventOrganizerId);
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean createEventDetails(int eventId, String eventName, String eventDate, String eventLocation, String eventDescription) {
        String query = "INSERT INTO eventdetails (EventId, EventName, EventDate, EventLocation, EventDescription) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, eventId);
            ps.setString(2, eventName);
            ps.setString(3, eventDate);
            ps.setString(4, eventLocation);
            ps.setString(5, eventDescription);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createInvitation(int eventId, int userId, String status, int roleId) {
        String query = "INSERT INTO invitation (EventId, UserId, Status, InvitationRole) VALUES (?, ?, ?, ?)";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.setString(3, status);
            ps.setInt(4, roleId);

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    
    
    //////Ambil User List Buat Invite//////
    
    public List<User> getGuests(int eventId){
    	List<User> guests = new ArrayList<User>();
    	String query = "SELECT u.UserId, u.Username, u.Email "
    			+ "FROM stellarfest.user u "
    			+ "JOIN stellarfest.role r ON u.RoleId = r.RoleId "
    			+ "WHERE r.RoleId = 0 "
    			+ "AND u.UserId NOT IN "
    			+ "(SELECT i.UserId "
    			+ "FROM stellarfest.invitation i "
    			+ "WHERE i.EventId = ?)";
    	
    	PreparedStatement ps = db.preparedStatment(query);
    	
    	try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"));
				guests.add(user); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return guests;
    }
    
    public List<User> getVendors(int eventId){
    	List<User> vendors = new ArrayList<User>();
    	String query = "SELECT u.UserId, u.Username, u.Email "
		    			+ "FROM stellarfest.user u "
		    			+ "JOIN stellarfest.role r ON u.RoleId = r.RoleId "
		    			+ "WHERE r.RoleId = 3 "
		    			+ "AND u.UserId NOT IN "
		    			+ "(SELECT i.UserId "
		    			+ "FROM stellarfest.invitation i "
		    			+ "WHERE i.EventId = ?)";
    	PreparedStatement ps = db.preparedStatment(query);
    	
    	try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"));
				vendors.add(user); 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return vendors;
    }
    
    public List<User> getUsersWithRoles(int... roles) {   ///Parameter int... diisi 0,3 dari loadUsers di EventOrganizerCreateEventView => Invite cuman bisa Guest & Vendor ////
        List<User> users = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT UserId, Username, Email, RoleId FROM user WHERE RoleId IN (");

        for (int i = 0; i < roles.length; i++) {
            query.append(roles[i]);
            if (i < roles.length - 1) query.append(", ");
        }
        query.append(")");

        try (Connection connection = Database.getInstance().getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query.toString())) {

            while (rs.next()) {
                User user = new User(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"), rs.getInt("RoleId"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    
    //////////////////////////////Event Organizer Event View Start Here//////////////////////////////
    
    public List<Event> getEventsByOrganizerId(int organizerId) {
        List<Event> events = new ArrayList<>();
        String query = "SELECT e.EventId, d.EventName, d.EventDate, d.EventLocation, d.EventDescription " +
                       "FROM eventheader e JOIN eventdetails d ON e.EventId = d.EventId " +
                       "WHERE e.EventOrganizerId = ?";

        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, organizerId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Event event = new Event(rs.getInt("EventId"),
                                            rs.getString("EventName"),
                                            rs.getString("EventDate"),
                                            rs.getString("EventLocation"),
                                            rs.getString("EventDescription"));
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }

    public boolean updateEventDetails(Event event) {
        String query = "UPDATE eventdetails SET EventName = ?, EventDate = ?, EventLocation = ?, EventDescription = ? WHERE EventId = ?";
        try (Connection connection = Database.getInstance().getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventDate());
            ps.setString(3, event.getEventLocation());
            ps.setString(4, event.getEventDescription());
            ps.setInt(5, event.getEventId());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

