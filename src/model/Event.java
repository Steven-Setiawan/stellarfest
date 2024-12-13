package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {
	private int eventId;
	private String eventName;
	private String eventDate;
	private String eventLocation;
	private String organizerId;
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

	public String getOrganizerId() {
		return organizerId;
	}

	public String getEventDescription() {
		return eventDescription;
	}
}
