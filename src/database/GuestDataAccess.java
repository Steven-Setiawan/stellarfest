package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Event;

public class GuestDataAccess {

	private Database db = Database.getInstance();
	
	// mengambil semua invitation yang ada
	public List<Event> getInvitations(String email, String status){
		String query = "SELECT  i.EventId, EventName, EventDate, EventLocation "
				+ "FROM invitation i JOIN eventheader eh ON i.EventId = eh.EventId "
				+ "JOIN eventdetails ed ON eh.EventId = ed.EventId "
				+ "JOIN user u ON i.UserId = u.UserId "
				+ "WHERE u.Email = ? AND i.Status = ?";
		PreparedStatement ps = db.preparedStatment(query);
		List<Event> invitationList = new ArrayList<Event>();
		
		
		try {
			// set data ke preparedstatment
			ps.setString(1, email);
			ps.setString(2, status);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				Event event = new Event(rs.getInt("EventId"), rs.getString("EventName"),
						rs.getString("EventDate"), rs.getString("EventLocation"));
				
				// add objek event yang ada ke list untuk di return
				invitationList.add(event);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return invitationList;
	}
	
	// update status invitasi berdasarkan event id
	public boolean acceptInvitation(int eventId) {
		String query = "UPDATE invitation "
						+ "SET Status = 'Accepted' "
						+ "WHERE EventId = ? AND UserId = ?";
		PreparedStatement ps = db.preparedStatment(query);
		try {
			// set data ke preparedStatment
			ps.setInt(1, eventId);
			ps.setInt(2, Session.getInstance().getLoggedInUser().getId());
			
			int rowsUpdated = ps.executeUpdate();
			// jika rows updated > 0 maka berhasi;
			return rowsUpdated >0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
