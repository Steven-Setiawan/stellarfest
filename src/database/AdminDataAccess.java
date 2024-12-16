package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.EventOrganizer;
import model.Guest;
import model.User;
import model.Vendor;


public class AdminDataAccess {

	private Database db = Database.getInstance();
	
	public AdminDataAccess() {
		
	}
	
	// delete event
	public boolean deleteEvent(int eventId) {
		String query = "DELETE FROM eventheader "
				+ "WHERE EventId = ?";
		PreparedStatement ps = db.preparedStatment(query);
		
		try {
			ps.setInt(1, eventId);
			// jika rows updated > 0 maka berhasi;
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteUser(int userId) {
		String query = "DELETE FROM user "
						+ "WHERE UserId = ?";
		PreparedStatement ps = db.preparedStatment(query);
		
		try {
			ps.setInt(1, userId);
			
			// jika rows updated > 0 maka berhasi;
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	// ambil semua data event detail berdasarkan event id
	public Event viewEventDetails(int eventId) {
		String query = "SELECT EventName, EventDate, EventLocation, EventDescription, EventOrganizerId FROM eventdetails ed"
					+ " JOIN eventheader eh ON ed.EventId = eh.EventId"
					+ " WHERE ed.EventId = ?;";
		PreparedStatement ps = db.preparedStatment(query);
		Event event = null;
		try {
			ps.setInt(1, eventId);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				event = new Event(rs.getString("EventName"),
						rs.getString("EventDate"), rs.getString("EventLocation"), 
						rs.getString("EventDescription"), rs.getInt("EventOrganizerId"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return event;
	}
	
	public User getOrganizerDetail(int orgamnizerId) {
		User user = null;
		String query = "SELECT * FROM user WHERE UserId = ?";
		
		PreparedStatement ps = db.preparedStatment(query);
		
		try {
			ps.setInt(1, orgamnizerId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user = new EventOrganizer(orgamnizerId, rs.getString("Username"), rs.getString("Email"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public List<User> getAllUsers(){
		List<User> userList = new ArrayList<User>();
		String query = "SELECT * FROM user";
		
		ResultSet rs = db.execQuery(query);
		
		try {
			while(rs.next()) {
				User user = new User(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Email"), rs.getInt("RoleId"));
				
				userList.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return userList;
	}
	
	public List<Event> getAllEvents(){
		List<Event> eventList = new ArrayList<Event>();
		String query = "SELECT * FROM eventheader eh "
						+ "JOIN eventdetails ed ON eh.EventId = ed.EventId";
		
		ResultSet rs =  db.execQuery(query);
		
		try {
			while (rs.next()) {
				Event event = new Event(rs.getInt("EventId"), rs.getString("EventName"), 
						rs.getString("EventDate"), rs.getString("EventLocation"));
				
				eventList.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return eventList;
	}
	
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

}
