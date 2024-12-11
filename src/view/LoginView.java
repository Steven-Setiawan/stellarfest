package view;


import controller.UserController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import model.User;
import view_controller.ViewController;

public class LoginView extends VBox {
    private VBox container;
    private Label lbl;
	private TextField emailTf;
    private PasswordField passwordPf;
    private Button loginBtn;
    private Button registerBtn;
    private ViewController vc = ViewController.getInstance();
    private UserController uc;
    
    private void init() {
    	uc = new UserController();
		container = new VBox();
		lbl = new Label("Login");
		emailTf = new TextField();
		passwordPf = new PasswordField();
		loginBtn = new Button("Login");
		registerBtn = new Button("Register");
		
		this.emailTf.setPromptText("Email");
		this.passwordPf.setPromptText("Password");

    }
    
    private void setEvent() {
		this.loginBtn.setOnAction(e -> {
			handleLogin();
		});
		
		this.registerBtn.setOnAction(event -> {
            vc.navigateToRegister(); 
        });
	}

    private void setLayout() {
        this.container.getChildren().addAll(lbl, emailTf, passwordPf, loginBtn, registerBtn);
        this.getChildren().add(this.container);
    }
    
    private void handleLogin() {
		String email = emailTf.getText().toString();
		String password = passwordPf.getText().toString();
		
		User user = uc.login(email, password);
		if(user != null) {
			vc.navigateToHome();
		}
	}

    public LoginView() {
        init();
        setLayout();
		setEvent();
    }
}
