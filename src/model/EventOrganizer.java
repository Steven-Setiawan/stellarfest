package model;

public class EventOrganizer extends User{

	public EventOrganizer(int id, String username, String email) {
		super(id, username, email);
	}

	public EventOrganizer(int id, String username, String email, String password, int role) {
		super(id, username, email, password, role);
	}

}
