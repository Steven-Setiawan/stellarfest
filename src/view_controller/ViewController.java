package view_controller;

import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.AdminHomeView;
import view.AppView;
import view.EventDetailsPage;
import view.EventOrganizerCreateEventView;
import view.EventOrganizerEventView;
import view.EventOrganizerHomeView;
import view.GuestHomeView;
import view.GuestInvitaionPage;
import view.LoginView;
import view.ProfileView;
import view.RegisterView;
import view.VendorHomeView;
import view.HomeView;

import java.util.Stack;

public class ViewController {
    private Stage stage;
    private AppView appView;
    private Stack<Region> viewStack;
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
    }
    
    public void navigateBack() {
        if (!viewStack.isEmpty()) {
            viewStack.pop();
            appView.setContent(viewStack.peek());
            stage.show();
        }
    }
    
    public void navigateToView(Region view, String title) {
    	this.appView.setContent(view);
    	this.stage.setTitle(title);
    	this.viewStack.add(view);
    	this.stage.show();
    }

    public void navigateToLogin() {
        navigateToView(new LoginView(), "Login Page");
    }

    public void navigateToRegister() {
        navigateToView(new RegisterView(), "Register Page");
    }
    
    public void navigateToGuestHome() {
    	navigateToView(new GuestHomeView(), "Guest Home Page");
    }

    public void navigateToAdminHome() {
        navigateToView(new AdminHomeView(), "Admin Home Page");
    }

    public void navigateToEventOrganizerHome() {
        navigateToView(new EventOrganizerHomeView() , "Event Organizer Home Page");
    }

    public void navigateToVendorHome() {
        navigateToView(new VendorHomeView(), "Vendor Home Page");
    }

    public void navigateToProfile() {
        navigateToView(new ProfileView(), "Profile View");
    }

    public void navigateToEventOrganizerCreateEvent() {
        navigateToView(new EventOrganizerCreateEventView(), "Create Event");
    }

    public void navigateToEventOrganizerEvent() {
        navigateToView(new EventOrganizerEventView(), "Event Organizer Events");
    }
    
    public void navigateToEventDetails(int eventId) {
    	navigateToView(new EventDetailsPage(eventId), "Event Details");
    }
    
    public void navigateToGuestInvitationPage() {
    	navigateToView(new GuestInvitaionPage(), "Guest Invitation List");
    }
}
