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
    private Label oldPasswordLabel, newPasswordLbl;
    private TextField usernameField;
    private TextField emailField;
    private TextField oldPasswordField, newPasswordField;
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
    	// inisialisasi komponen yang akan digunakan
        User loggedInUser = Session.getInstance().getLoggedInUser();

        navContainer = new HBox(10);
        container = new VBox();
        
        usernameLabel = new Label("Username:");
        emailLabel = new Label("Email:");
        oldPasswordLabel = new Label("Old Password:");
        newPasswordLbl = new Label("New Password:");

        usernameField = new TextField(loggedInUser.getUsername());
        emailField = new TextField(loggedInUser.getEmail());
        oldPasswordField = new TextField();
        newPasswordField = new TextField();

        submitButton = new Button("Submit");
        backBtn = new Button("Back");
    }

    private void setEventHandlers() {
    	// setting event handler
        this.submitButton.setOnAction(event -> handleSubmit());
        this.backBtn.setOnAction(event -> {
        	vc.navigateBack();
        });
    }
    
	private void setStyle() {
		// memberikan sedikit style dengan inline-style
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.backBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	}

    private void setLayout() {
    	// memasukkan komponen ke container yang ada, agar dapat ditampilkan
        this.container.getChildren().addAll(
            usernameLabel, usernameField,
            emailLabel, emailField,
            oldPasswordLabel, oldPasswordField,
            newPasswordLbl, newPasswordField,
            submitButton
        );
        
        this.navContainer.getChildren().addAll(backBtn);
        
        this.setTop(navContainer);
        this.setLeft(container);
    }

    // handle submit button
    private void handleSubmit() {
    	// mengambil inputan user
        String newUsername = usernameField.getText().trim();
        String newEmail = emailField.getText().trim();
        String oldPassword = oldPasswordField.getText().trim();
        String newPassword = newPasswordField.getText().trim();

        
        // mulai proses update profile
        if(userController.checkChangeProfileInput(newUsername, newEmail, oldPassword, newPassword)) {
        	if (userController.changeProfile(newEmail, newUsername, oldPassword, newPassword)) {
        		// jika proses sukses
                showAlert(AlertType.INFORMATION, "Profile updated successfully.");
                vc.navigateToProfile();
            } else {
            	// jika gagal
                showAlert(AlertType.ERROR, "Failed to update profile. Please try again.");
            }
        }   
    }

    // fucntion untuk meberikan alert;
    private void showAlert(AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
