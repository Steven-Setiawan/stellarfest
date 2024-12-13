package view_controller;

import javafx.scene.layout.Region;
import javafx.stage.Stage;
import view.AdminHomeView;
import view.AppView;
import view.EventOrganizerCreateEventView;
import view.EventOrganizerEventView;
import view.EventOrganizerHomeView;
import view.GuestHomeView;
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
        LoginView lv = new LoginView();
        this.appView.setContent(lv);
        this.stage.setTitle("Login Page");
        this.stage.show();
    }

    public void navigateToRegister() {
        RegisterView rv = new RegisterView();
        this.appView.setContent(rv);
        this.stage.setTitle("Register Page");
        this.stage.show();
    }
    
    public void navigateToHome() {
		HomeView hv= new HomeView();
		this.appView.setContent(hv);
		this.stage.setTitle("Home Page");
		this.stage.show();
	}
    
    public void navigateToGuestHome() {
        GuestHomeView ghv = new GuestHomeView();
        this.appView.setContent(ghv);
        this.stage.setTitle("Guest Home Page");
        this.stage.show();
    }

    public void navigateToAdminHome() {
        AdminHomeView ahv = new AdminHomeView();
        this.appView.setContent(ahv);
        this.stage.setTitle("Admin Home Page");
        this.stage.show();
    }

    public void navigateToEventOrganizerHome() {
        EventOrganizerHomeView eohv = new EventOrganizerHomeView();
        this.appView.setContent(eohv);
        this.stage.setTitle("Event Organizer Home Page");
        this.stage.show();
    }

    public void navigateToVendorHome() {
        VendorHomeView vhv = new VendorHomeView();
        this.appView.setContent(vhv);
        this.stage.setTitle("Vendor Home Page");
        this.stage.show();
    }

    public void navigateToProfile() {
        ProfileView profileView = new ProfileView();
        this.appView.setContent(profileView);
        this.stage.setTitle("Profile View");
        this.stage.show();
    }

    public void navigateToEventOrganizerCreateEvent() {
        EventOrganizerCreateEventView createEventView = new EventOrganizerCreateEventView();
        this.appView.setContent(createEventView);
        this.stage.setTitle("Create Event");
        this.stage.show();
    }

    public void navigateToEventOrganizerEvent() {
        EventOrganizerEventView eventView = new EventOrganizerEventView();
        this.appView.setContent(eventView);
        this.stage.setTitle("Event Organizer Events");
        this.stage.show();
    }
    
    
}
