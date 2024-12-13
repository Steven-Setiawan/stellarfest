package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {

    private int eventId;
    private StringProperty eventName;
    private StringProperty eventDate;
    private StringProperty eventLocation;
    private StringProperty eventDescription;

    public Event(int eventId, String eventName, String eventDate, String eventLocation, String eventDescription) {
        this.eventId = eventId;
        this.eventName = new SimpleStringProperty(eventName);
        this.eventDate = new SimpleStringProperty(eventDate);
        this.eventLocation = new SimpleStringProperty(eventLocation);
        this.eventDescription = new SimpleStringProperty(eventDescription);
    }

    public int getEventId() {
        return eventId;
    }

    public StringProperty eventNameProperty() {
        return eventName;
    }

    public String getEventName() {
        return eventName.get();
    }

    public void setEventName(String eventName) {
        this.eventName.set(eventName);
    }

    public StringProperty eventDateProperty() {
        return eventDate;
    }

    public String getEventDate() {
        return eventDate.get();
    }

    public StringProperty eventLocationProperty() {
        return eventLocation;
    }

    public String getEventLocation() {
        return eventLocation.get();
    }

    public StringProperty eventDescriptionProperty() {
        return eventDescription;
    }

    public String getEventDescription() {
        return eventDescription.get();
    }
}
