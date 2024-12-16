package view;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view_controller.ViewController;

public class EONavbar extends HBox{

	private Button homeButton, profileButton, eventButton, logoutButton;
	
	//SET UP NAVBAR UNTUK seleruh EVENT ORGANIZER VIEW
	
	private void setUpNavBar() {
        homeButton = new Button("Home");
        profileButton = new Button("Profile");
        eventButton = new Button("Event");
        logoutButton = new Button("Logout");
        
        this.setSpacing(10);
		this.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
        homeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        profileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        eventButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        homeButton.setOnAction(e -> ViewController.getInstance().navigateToEventOrganizerHome());
        profileButton.setOnAction(e -> ViewController.getInstance().navigateToProfile());
        eventButton.setOnAction(e -> ViewController.getInstance().navigateToEventOrganizerEvent());
        logoutButton.setOnAction(e -> ViewController.getInstance().navigateToLogin());

        this.getChildren().addAll(homeButton, profileButton, eventButton, logoutButton);
    }
	public EONavbar() {
		setUpNavBar();
	}

}
