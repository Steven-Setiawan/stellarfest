package controller;

import java.util.List;

import database.GuestDataAccess;
import model.Event;

public class GuestController {

	private GuestDataAccess da;
	
	public GuestController() {
		da = new GuestDataAccess();
	}
	
	public List<Event> getInvitations(int id, String status){
		return da.getInvitations(id, status);
	}
	
	public boolean acceptInvitation(int eventId) {
		return da.acceptInvitation(eventId);
	}
}
