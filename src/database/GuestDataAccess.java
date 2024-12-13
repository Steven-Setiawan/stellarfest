package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;

public class GuestDataAccess {

	private Database db = Database.getInstance();
	
	public List<Event> getInvitations(int id, String status){
		String query = "SELECT  i.EventId, EventName, EventDate, EventLocation "
				+ "FROM invitation i JOIN eventheader eh ON i.EventId = eh.EventId "
				+ "JOIN eventdetails ed ON eh.EventId = ed.EventId "
				+ "WHERE i.UserId = ? AND i.Status = ?";
		PreparedStatement ps = db.preparedStatment(query);
		List<Event> invitationList = new ArrayList<Event>();
		
		
		try {
			ps.setInt(1, id);
			ps.setString(2, status);
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
						+ "WHERE EventId = ?";
		PreparedStatement ps = db.preparedStatment(query);
		try {
			ps.setInt(1, eventId);
			
			int rowsUpdated = ps.executeUpdate();
			return rowsUpdated >0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
