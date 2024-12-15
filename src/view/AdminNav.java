package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view_controller.ViewController;

public class AdminNav extends HBox implements EventHandler<ActionEvent>{
	
	private Button homeBtn, eventBtn, usersBtn, profileBtn, logoutBtn;
	
	private void init() {
		homeBtn = new Button("Home");
		eventBtn = new Button("Event");
		usersBtn = new Button("Users");
		profileBtn = new Button("Profile");
		logoutBtn = new Button("Logout");
		
		this.homeBtn.setOnAction(this);
		this.eventBtn.setOnAction(this);
		this.usersBtn.setOnAction(this);
		this.profileBtn.setOnAction(this);
		this.logoutBtn.setOnAction(this);
	}
	
	private void setLayout() {
		this.getChildren().addAll(homeBtn, eventBtn, usersBtn, profileBtn, logoutBtn);
		this.setAlignment(Pos.TOP_LEFT);
	}
	
	private void setStyle() {
		this.setSpacing(10);
		this.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.homeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.eventBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.usersBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.profileBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.logoutBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
	}
	
	public AdminNav() {
		init();
		setLayout();
		setStyle();
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == this.homeBtn) {
			ViewController.getInstance().navigateToAdminHome();
		}else if(e.getSource() == this.eventBtn) {
			ViewController.getInstance().navigateToAdminEvent();
		}else if(e.getSource() == this.usersBtn) {
			ViewController.getInstance().navigateToAdminUsers();
		}else if(e.getSource() == this.logoutBtn) {
			ViewController.getInstance().navigateToLogin();
		}else if(e.getSource() == this.profileBtn) {
			ViewController.getInstance().navigateToProfile();
		}
		
	}

}
