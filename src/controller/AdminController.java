package controller;

import java.util.List;

import database.AdminDataAccess;
import model.Event;
import model.User;

public class AdminController {

	// berisi function untuk menghubungkan view dengan data access
	
	private AdminDataAccess ad;
	
	public AdminController() {
		ad = new AdminDataAccess();
	}
	
	public boolean deleteEvent(int eventId) {
		return ad.deleteEvent(eventId);
	}
	
	public boolean deleteUser(int userId) {
		return ad.deleteUser(userId);
	}
	
	public Event viewEventDetails(int eventId) {
		return ad.viewEventDetails(eventId);
	}
	
	public User getOrganizerDetail(int orgamnizerId) {
		return ad.getOrganizerDetail(orgamnizerId);
	}
	
	public List<User> getAllUsers(){
		return ad.getAllUsers();
	}
	
	public List<Event> getAllEvents(){
		return ad.getAllEvents();
	}
	
	public List<User> getGuestByTransactionId(int eventId){
		return ad.getGuestByTransactionId(eventId);
	}
	
	public List<User> getVendorByTransactionId(int eventId){
		return ad.getVendorByTransactionId(eventId);
	}
}
