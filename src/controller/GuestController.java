package controller;

import java.util.List;

import database.GuestDataAccess;
import model.Event;

public class GuestController {

	// berisi function unutk validasi dan sisanya untuk menghubungkan view dengan data access
	
	private GuestDataAccess da;
	
	public GuestController() {
		da = new GuestDataAccess();
	}
	
	public List<Event> getInvitations(String email, String status){
		return da.getInvitations(email, status);
	}
	
	public boolean acceptInvitation(int eventId) {
		return da.acceptInvitation(eventId);
	}
}
