package model;


public class Event {
	private String eventId;
	private String eventName;
	private String eventDate;
	private String eventLocation;
	private String organizerId;
	
	public Event(String eventId, String eventName, String eventDate, String eventLocation, String organizerId) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDate = eventDate;
		this.eventLocation = eventLocation;
		this.organizerId = organizerId;
	}
}
