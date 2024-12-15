package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;
import model.VendorProduct;

public class VendorDataAccess {
	
	private Database db = Database.getInstance();
	
	public VendorDataAccess() {
		// TODO Auto-generated constructor stub
	}
	
	public List<VendorProduct> getAllProducts(int vendorId){
		List<VendorProduct> products = new ArrayList<VendorProduct>();
		String query = "SELECT * FROM vendorproduct "
						+ "WHERE VendorId = ?";
		
		PreparedStatement ps = db.preparedStatment(query);
		
		try {
			ps.setInt(1, vendorId);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				VendorProduct product = new VendorProduct(vendorId, rs.getString("ProductName"), rs.getString("ProductDescription"));
			
				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return products;
	}
	
	public boolean manageVendor(String productName, String productDescription) {
		String query = "INSERT INTO vendorproduct VALUES("
						+ "NULL, ?, ?, ?)";
		
		PreparedStatement ps = db.preparedStatment(query);
		
		try {
			ps.setString(1, productName);
			ps.setString(2, productDescription);
			ps.setInt(3, Session.getInstance().getLoggedInUser().getId());
			
			int rowUpdated = ps.executeUpdate();
			return rowUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public List<Event> getAcceptedEvent(String email){
		String query = "SELECT  i.EventId, EventName, EventDate, EventLocation "
				+ "FROM invitation i JOIN eventheader eh ON i.EventId = eh.EventId "
				+ "JOIN eventdetails ed ON eh.EventId = ed.EventId "
				+ "JOIN user u ON i.UserId = u.UserId "
				+ "WHERE u.Email = ? AND i.Status = 'Accepted'";
		PreparedStatement ps = db.preparedStatment(query);
		List<Event> accceptedList = new ArrayList<Event>();
		
		
		try {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Event event = new Event(rs.getInt("EventId"), rs.getString("EventName"),
						rs.getString("EventDate"), rs.getString("EventLocation"));
				
				accceptedList.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return accceptedList;
	}
	
	public List<Event> getInvitations(String email){
		String query = "SELECT  i.EventId, EventName, EventDate, EventLocation "
				+ "FROM invitation i JOIN eventheader eh ON i.EventId = eh.EventId "
				+ "JOIN eventdetails ed ON eh.EventId = ed.EventId "
				+ "JOIN user u ON i.UserId = u.UserId "
				+ "WHERE u.Email = ? AND i.Status = 'Pending'";
		PreparedStatement ps = db.preparedStatment(query);
		List<Event> invitationList = new ArrayList<Event>();
		
		
		try {
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Event event = new Event(rs.getInt("EventId"), rs.getString("EventName"),
						rs.getString("EventDate"), rs.getString("EventLocation"));
				
				invitationList.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitationList;
	}
	
	public boolean acceptInvitation(int eventId) {
		String query = "UPDATE invitation "
						+ "SET Status = 'Accepted' "
						+ "WHERE EventId = ? AND UserId = ?";
		PreparedStatement ps = db.preparedStatment(query);
		try {
			ps.setInt(1, eventId);
			ps.setInt(2, Session.getInstance().getLoggedInUser().getId());
			
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated >0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
