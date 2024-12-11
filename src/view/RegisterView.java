package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import controller.UserController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view_controller.ViewController;

public class RegisterView extends VBox {
    private VBox container;
    private Label lbl;
    private TextField usernameTf;
    private TextField emailTf;
    private PasswordField passwordPf;
    private ComboBox<String> roleComboBox;
    private Button btn;
    private ViewController vc = ViewController.getInstance();
    private UserController uc = new UserController();

    private void init() {
        container = new VBox();

        lbl = new Label("Register");

        usernameTf = new TextField();
        emailTf = new TextField();
        passwordPf = new PasswordField();

        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Vendor", "Guest", "Event Organizer");
        roleComboBox.setValue("Guest");

        btn = new Button("Register");

        this.usernameTf.setPromptText("Username");
        this.emailTf.setPromptText("Email");
        this.passwordPf.setPromptText("Password");

        btn.setOnAction(e -> handleRegister());
    }

    private void setLayout() {
        this.container.getChildren().addAll(lbl, usernameTf, emailTf, passwordPf, roleComboBox, btn);
        this.getChildren().add(this.container);
    }

    public RegisterView() {
        init();
        setLayout();
    }

    private void handleRegister() {
        String username = usernameTf.getText();
        String email = emailTf.getText();
        String password = passwordPf.getText();
        String role = roleComboBox.getValue();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showAlert("Registration Failed", "Please fill in all fields.", AlertType.ERROR);
            return;
        }
        boolean isRegistered = uc.registerUser(username, email, password, role);

        if (isRegistered) {
            showAlert("Registration Successful", "User has been registered successfully.", AlertType.INFORMATION);
            clearFields();
        } else {
            showAlert("Registration Failed", "An error occurred during registration. Please try again.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameTf.clear();
        emailTf.clear();
        passwordPf.clear();
        roleComboBox.setValue("Guest");
    }
}
