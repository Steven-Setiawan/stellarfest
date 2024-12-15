package view_controller;

import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.AdminEventDetailsView;
import view.AdminEventView;
import view.AdminHomeView;
import view.AdminManageUsersView;
import view.AppView;
import view.EoEventDetailsView;
import view.EventDetailsPage;
import view.EventOrganizerCreateEventView;
import view.EventOrganizerEventView;
import view.EventOrganizerHomeView;
import view.EventOrganizerInviteParticipantView;
import view.GuestHomeView;
import view.GuestInvitaionPage;
import view.LoginView;
import view.ProfileView;
import view.RegisterView;
import view.VendorEventsView;
import view.VendorHomeView;
import view.VendorInvitationView;
import view.VendorManageProductView;

import java.util.Stack;

public class ViewController {
    private Stage stage;
    private AppView appView;
    private Stack<Region> viewStack;
    private Stack<String> titleStack;
    private static ViewController instance;

    private ViewController() {
    }

    public static ViewController getInstance() {
        if (instance == null) {
            instance = new ViewController();
        }
        return instance;
    }

    public void initialize(Stage stage, AppView appView) {
        this.stage = stage;
        this.appView = appView;
        this.viewStack = new Stack<>();
        this.titleStack = new Stack<>();
    }
    
    public void navigateBack() {
        if (!viewStack.isEmpty()) {
            viewStack.pop();
            titleStack.pop();
            stage.setTitle(titleStack.peek());
            appView.setContent(viewStack.peek());
            stage.show();
        }
    }
    
    public void navigateToView(Region view, String title) {
    	this.appView.setContent(view);
    	this.stage.setTitle(title);
    	this.titleStack.add(title);
    	this.viewStack.add(view);
    	this.stage.show();
    }

    public void navigateToLogin() {
        navigateToView(new LoginView(), "Login Page");
    }

    public void navigateToRegister() {
        navigateToView(new RegisterView(), "Register Page");
    }
    
    public void navigateToProfile() {
        navigateToView(new ProfileView(), "Profile View");
    }
    
    // Guest Navigation
    
    public void navigateToGuestHome() {
    	navigateToView(new GuestHomeView(), "Guest Home Page");
    }
    
    public void navigateToGuestInvitationPage() {
    	navigateToView(new GuestInvitaionPage(), "Guest Invitation List");
    }
    
    // End Guest Navigation
    
    // Admin Navigation

    public void navigateToAdminHome() {
        navigateToView(new AdminHomeView(), "Admin Home Page");
    }
    
    public void navigateToAdminEvent() {
    	navigateToView(new AdminEventView(), "Manage Event");
    }
    
    public void navigateToAdminUsers() {
    	navigateToView(new AdminManageUsersView(), "Manage Users");
    }
    
    public void navigateToAdminEventDetails(int eventId) {
    	navigateToView(new AdminEventDetailsView(eventId), "Event Details");
    }
    
    // End Admin Navigation
    
    // Event Organizer Navigation

    public void navigateToEventOrganizerHome() {
        navigateToView(new EventOrganizerHomeView() , "Event Organizer Home Page");
    }
    
    public void navigateToEventOrganizerCreateEvent() {
        navigateToView(new EventOrganizerCreateEventView(), "Create Event");
    }

    public void navigateToEventOrganizerEvent() {
        navigateToView(new EventOrganizerEventView(), "Event Organizer Events");
    }
    
    public void navigateToEventOrganizerEventDetails(int eventId) {
    	navigateToView(new EoEventDetailsView(eventId), "Event's Details");
    }
    
    public void navigateToEventOrganizerInviteParticipant(int eventId) {
    	navigateToView(new EventOrganizerInviteParticipantView(eventId), "Invite Participant");
    }
    
    // End Event Organizer Navigation

    // Vendor Navigatiom
    
    public void navigateToVendorHome() {
        navigateToView(new VendorHomeView(), "Vendor Home Page");
    }
    
    public void navigateToVendorEvent() {
    	navigateToView(new VendorEventsView(), "Vendor Events");
    }
    
    public void navigateTOVendorInvitation() {
    	navigateToView(new VendorInvitationView(), "Vendor's Invitations");
    }
    
    public void navigateToVendorManageProduct() {
    	navigateToView(new VendorManageProductView(), "Vendor's Products");
    }

    // End Vendor Navigation
    
    public void navigateToEventDetails(int eventId) {
    	navigateToView(new EventDetailsPage(eventId), "Event Details");
    }
    
    public void navigateToHomeBasedOnRole(int roleId) {
    	if (roleId == 0) {
            navigateToGuestHome();
        } else if (roleId == 1) {
            navigateToAdminHome();
        } else if (roleId == 2) {
            navigateToEventOrganizerHome();
        } else if (roleId == 3) {
            navigateToVendorHome();
        }
    }
}
