package view;

import database.Session;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.User;
import view_controller.ViewController;

public class EventOrganizerHomeView extends VBox {
    
    private ViewController vc = ViewController.getInstance();
    private Text welcomeText;
    private Button editProfileBtn;
    private Button createEventBtn;
    private Button showEventBtn;
    
    public EventOrganizerHomeView() {
        init();
        setEvent();
        setLayout();
    }
    
    private void init() {
    	// inisialisasi komponen yang akan digunakan
        welcomeText = new Text();
        editProfileBtn = new Button("Edit Profile");
        createEventBtn = new Button("Create Event");
        showEventBtn = new Button("Show Event");
        
        User loggedInUser = Session.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            welcomeText.setText("Welcome to Stellarfest, " + loggedInUser.getUsername());
        }
    }
    
    // set event handler dari button yang ada
    private void setEvent() {
        editProfileBtn.setOnAction(e -> vc.navigateToProfile());
        createEventBtn.setOnAction(e -> vc.navigateToEventOrganizerCreateEvent());
        showEventBtn.setOnAction(e -> vc.navigateToEventOrganizerEvent());
    }
    
    private void setLayout() {
    	// memasukkan komponen ke container yang ada, agar dapat ditampilkan
        this.getChildren().addAll(welcomeText, editProfileBtn, createEventBtn, showEventBtn);
    }
}
