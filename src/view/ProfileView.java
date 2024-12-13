package view;

import controller.UserController;
import database.Session;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import view_controller.ViewController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class ProfileView extends BorderPane {

	private HBox navContainer;
	private VBox container;
    private Label usernameLabel;
    private Label emailLabel;
    private Label passwordLabel;
    private TextField usernameField;
    private TextField emailField;
    private TextField passwordField;
    private Button submitButton, backBtn;

    private UserController userController = new UserController();
    private ViewController vc = ViewController.getInstance();
    
    public ProfileView() {
        init();
        setEventHandlers();
        setLayout();
        setStyle();
    }

    private void init() {
        User loggedInUser = Session.getInstance().getLoggedInUser();

        navContainer = new HBox(10);
        container = new VBox();
        
        usernameLabel = new Label("Username:");
        emailLabel = new Label("Email:");
        passwordLabel = new Label("Password:");

        usernameField = new TextField(loggedInUser.getUsername());
        emailField = new TextField(loggedInUser.getEmail());
        passwordField = new TextField(loggedInUser.getPassword());

        submitButton = new Button("Submit");
        backBtn = new Button("Back");
    }

    private void setEventHandlers() {
        this.submitButton.setOnAction(event -> handleSubmit());
        this.backBtn.setOnAction(event -> {
        	vc.navigateBack();
        });
    }
    
	private void setStyle() {
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.backBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	}

    private void setLayout() {
        this.container.getChildren().addAll(
            usernameLabel, usernameField,
            emailLabel, emailField,
            passwordLabel, passwordField,
            submitButton
        );
        
        this.navContainer.getChildren().addAll(backBtn);
        
        this.setTop(navContainer);
        this.setLeft(container);
    }

    private void handleSubmit() {
        String newUsername = usernameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String newPassword = passwordField.getText().trim();

        if (newUsername.isEmpty() || newEmail.isEmpty() || newPassword.isEmpty()) {
            showAlert(AlertType.ERROR, "All fields are required.");
            return;
        }

        User loggedInUser = Session.getInstance().getLoggedInUser();
        loggedInUser.setUsername(newUsername);
        loggedInUser.setEmail(newEmail);
        loggedInUser.setPassword(newPassword);

        boolean success = userController.updateUser(loggedInUser);

        if (success) {
            Session.getInstance().setLoggedInUser(loggedInUser);

            showAlert(AlertType.INFORMATION, "Profile updated successfully.");
            if(loggedInUser.getRole()==1) {
            	vc.navigateToGuestHome();
            }else if(loggedInUser.getRole()==2) {
            	vc.navigateToEventOrganizerHome();
            }
        } else {
            showAlert(AlertType.ERROR, "Failed to update profile. Please try again.");
        }
    }

    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
