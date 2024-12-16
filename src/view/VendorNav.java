package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view_controller.ViewController;

public class VendorNav extends HBox implements EventHandler<ActionEvent>{

	private Button homeBtn, eventBtn, invitationBtn, profileBtn, logoutBtn, productBtn;
	
	private void init() {
		// inisialisasi komponen yang akan digunakan
		homeBtn = new Button("Home");
		eventBtn = new Button("Event");
		invitationBtn = new Button("Invitation");
		profileBtn = new Button("Profile");
		logoutBtn = new Button("Logout");
		productBtn = new Button("Product");
		
		this.homeBtn.setOnAction(this);
		this.eventBtn.setOnAction(this);
		this.invitationBtn.setOnAction(this);
		this.profileBtn.setOnAction(this);
		this.logoutBtn.setOnAction(this);
		this.productBtn.setOnAction(this);
	}
	
	private void setLayout() {
		this.getChildren().addAll(homeBtn, eventBtn, invitationBtn, productBtn, profileBtn, logoutBtn);
		this.setAlignment(Pos.TOP_LEFT);
	}
	
	private void setStyle() {
		this.setSpacing(10);
		this.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.homeBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.eventBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.invitationBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.productBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.profileBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.logoutBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
	}
	
	public VendorNav() {
		init();
		setLayout();
		setStyle();
	}

	// Handle click button dari nav
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == this.homeBtn) {
			ViewController.getInstance().navigateToVendorHome();;
		}else if(e.getSource() == this.eventBtn) {
			ViewController.getInstance().navigateToVendorEvent();;
		}else if(e.getSource() == this.invitationBtn) {
			ViewController.getInstance().navigateTOVendorInvitation();;
		}else if(e.getSource() == this.logoutBtn) {
			ViewController.getInstance().navigateToLogin();
		}else if(e.getSource() == this.profileBtn) {
			ViewController.getInstance().navigateToProfile();
		}else if(e.getSource() == this.productBtn) {
			ViewController.getInstance().navigateToVendorManageProduct();
		}
	}

}
