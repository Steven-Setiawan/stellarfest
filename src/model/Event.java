package model;

public class Event {
	private int eventId;
	private String eventName;
	private String eventDate;
	private String eventLocation;
	private int organizerId;
	private String eventDescription;
	
	public Event(int eventId, String eventName, String eventDate, String eventLocation) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
	}
	
	public Event(int eventId, String eventName, String eventDate, String eventLocation, String eventDescription) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
		this.eventDescription = eventDescription;
	}
	
	public Event(String eventName, String eventDate, String eventLocation, String eventDescription, int organizerId) {
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
		this.eventDescription = eventDescription;
		this.organizerId = organizerId;
	}

	public int getEventId() {
		return eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public String getEventDate() {
		return eventDate;
	}

	public String getEventLocation() {
		return eventLocation;
	}

	public int getOrganizerId() {
		return organizerId;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public void setEventLocation(String eventLocation) {
		this.eventLocation = eventLocation;
	}

	public void setOrganizerId(int organizerId) {
		this.organizerId = organizerId;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	
	
}
