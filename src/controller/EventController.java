package controller;

import database.EventDataAccess;
import model.Event;

public class EventController {

	private EventDataAccess ed;
	
	public EventController() {
		ed = new EventDataAccess();
	}
	
	public Event viewEventDetails(int eventId) {
		
		return ed.viewEventDetails(eventId);
	}

}
