package controller;

import database.EventDataAccess;
import model.Event;

public class EventController {
	
	// berisi function untuk menghubungkan view dengan data access

	private EventDataAccess ed;
	
	public EventController() {
		ed = new EventDataAccess();
	}
	
	public Event viewEventDetails(int eventId) {
		
		return ed.viewEventDetails(eventId);
	}

}
