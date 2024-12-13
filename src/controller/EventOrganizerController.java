package controller;

import database.EventDataAccess;
import model.Event;
import model.User;
import java.util.List;

public class EventOrganizerController {

    private EventDataAccess eventDataAccess;

    public EventOrganizerController() {
        eventDataAccess = new EventDataAccess();
    }
    
    //////////////////////////////Event Organizer Create Event Start Here//////////////////////////////

    public int createEvent(String eventName, String eventDate, String eventLocation, String eventDescription, int eventOrganizerId) {
        int eventId = eventDataAccess.createEventHeader(eventOrganizerId);
        if (eventId > 0) {
            boolean eventDetailsCreated = eventDataAccess.createEventDetails(eventId, eventName, eventDate, eventLocation, eventDescription);
            if (eventDetailsCreated) {
                return eventId;
            }
        }
        return -1;
    }

    public boolean createInvitation(int eventId, User user, String status) {
        int roleId = user.getRole();
        return eventDataAccess.createInvitation(eventId, user.getId(), status, roleId);
    }

    //////////////////////////////Event Organizer View Event Start Here//////////////////////////////
    
    public List<User> getUsersWithRole(int... roles) {
        return eventDataAccess.getUsersWithRoles(roles);
    }
    
    public List<Event> getEventsByOrganizerId(int organizerId) {
        return eventDataAccess.getEventsByOrganizerId(organizerId);
    }

    public boolean updateEventDetails(Event event) {
        return eventDataAccess.updateEventDetails(event);
    }
    
}
