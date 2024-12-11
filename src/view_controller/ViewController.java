package view_controller;

import javafx.stage.Stage;
import view.AppView;
import view.LoginView;
import view.RegisterView;
import view.HomeView;
import controller.UserController;

public class ViewController {
    private Stage stage;
    private AppView appView;
    private static ViewController instance;
    private UserController userController;

    private ViewController() {
        userController = new UserController();
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

    public boolean registerUser(String username, String email, String password, String role) {
        return userController.registerUser(username, email, password, role);
    }
    
    public void navigateToHome() {
		HomeView hv= new HomeView();
		this.appView.setContent(hv);
		this.stage.setTitle("Home Page");
		this.stage.show();
	}
}
