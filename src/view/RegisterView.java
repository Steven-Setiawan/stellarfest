package view;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import controller.UserController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import view_controller.ViewController;

public class RegisterView extends VBox {
    private VBox container;
    private Label lbl, text1;
    private TextField usernameTf;
    private TextField emailTf;
    private PasswordField passwordPf;
    private ComboBox<String> roleComboBox;
    private Button btn;
    private ViewController vc = ViewController.getInstance();
    private UserController uc = new UserController();
    private Hyperlink loginBtn;
    private HBox wrapper;

    private void init() { // inisialisasi komponen yang akan digunakan
        container = new VBox(10);
        loginBtn = new Hyperlink("Login here");
        text1 = new Label("Already have an Account? ");
        wrapper = new HBox(5);
        
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
        this.loginBtn.setOnAction(e -> {
        	vc.navigateToLogin();
        });
    }

    private void setLayout() {
    	// memasukkan komponen ke container yang ada, agar dapat ditampilkan
    	this.wrapper.getChildren().addAll(text1, loginBtn);
        this.container.getChildren().addAll(lbl, usernameTf, emailTf, passwordPf, roleComboBox, wrapper,btn);
        this.getChildren().add(this.container);
    }

    public RegisterView() {
        init();
        setLayout();
    }
    
    private void handleRegister() {
    	// mengambil data dari texfield, inputan user
        String username = usernameTf.getText();
        String email = emailTf.getText();
        String password = passwordPf.getText();
        String role = roleComboBox.getValue();
        
        // mulai tahap regis
        if (uc.checkRegisterInput(username, email, password)) {
        	if (uc.register(username, email, password, role)) {
                showAlert("Registration Successful", "User has been registered successfully.", AlertType.INFORMATION);
                vc.navigateToLogin();
            } else {
                showAlert("Registration Failed", "An error occurred during registration. Please try again.", AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
