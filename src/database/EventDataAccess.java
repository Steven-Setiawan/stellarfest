package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Event;

public class EventDataAccess {
	
	private Database db = Database.getInstance();
	
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

}
