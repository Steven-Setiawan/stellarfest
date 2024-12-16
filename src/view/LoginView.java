package view;


import controller.UserController;
import database.Session;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;
import view_controller.ViewController;

public class LoginView extends VBox {
    private VBox container;
    private Label lbl, text1;
	private TextField emailTf;
    private PasswordField passwordPf;
    private Button loginBtn;
    private ViewController vc = ViewController.getInstance();
    private UserController uc;
    private Hyperlink regisBtn;
    private HBox wrapper;
    
    private void init() {
    	// inisialisasi komponen yang akan digunakan
    	uc = new UserController();
		container = new VBox(10);
		lbl = new Label("Login");
		emailTf = new TextField();
		passwordPf = new PasswordField();
		loginBtn = new Button("Login");
		
        regisBtn = new Hyperlink("Register here");
        text1 = new Label("Does't have an Account? ");
        wrapper = new HBox(5);
		
		this.emailTf.setPromptText("Email");
		this.passwordPf.setPromptText("Password");


    }
    
    // inisialissai event handler yang ada
    private void setEvent() {
    	// handle login btn
		this.loginBtn.setOnAction(e -> {
			handleLogin();
		});
		
		// handle hyperlink to go to register
		this.regisBtn.setOnAction(e -> {
        	vc.navigateToRegister();
        });
	}

    private void setLayout() {
    	// memasukkan komponen ke container yang ada, agar dapat ditampilkan
    	this.wrapper.getChildren().addAll(text1, regisBtn);
        this.container.getChildren().addAll(lbl, emailTf, passwordPf, wrapper,loginBtn);
        this.getChildren().add(this.container);
    }
    
    private void handleLogin() {
		// ambil inputan dari textfield
    	String email = emailTf.getText().trim();
		String password = passwordPf.getText().trim();
		
		// mulai proses login
	    if (uc.checkLoginInput(email, password)) {
	        if(uc.login(email, password)) {
	        	// jika login berhasil
	        	vc.navigateToHomeBasedOnRole(Session.getInstance().getLoggedInUser().getRole());
	        }else {
	        	// login gagal
	        	Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Failed");
		        alert.setHeaderText(null);
		        alert.setContentText("Wrong Credentials");
		        alert.showAndWait();
	        }
	    }
	}

    public LoginView() {
        init();
        setLayout();
		setEvent();
    }
}
