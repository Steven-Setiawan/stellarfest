package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import view_controller.ViewController;

public class GuestHomeView extends BorderPane implements EventHandler<ActionEvent>{

	private HBox navContainer;
	private Button invitationBtn, profileBtn, logoutBtn;
	private ViewController vc;
	
	private void init() {
		// inisialisasi komponen yang akan digunakan
		vc = ViewController.getInstance();
		navContainer = new HBox(10);
		invitationBtn = new Button("Invitation");
		profileBtn = new Button("Profile");
		logoutBtn = new Button("Logout");
		
		this.invitationBtn.setOnAction(this);
		this.logoutBtn.setOnAction(this);
		this.profileBtn.setOnAction(this);
	}
	
	private void setLayout() {
		// memasukkan komponen ke container yang ada, agar dapat ditampilkan
		this.navContainer.getChildren().addAll(invitationBtn, profileBtn, logoutBtn);
		this.setTop(navContainer);
		this.navContainer.setAlignment(Pos.TOP_LEFT);
	}
	
	private void setStyle() {
		// menambahkan style
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.invitationBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.profileBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.logoutBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
	}

	// handle nav click
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == this.logoutBtn) {
			vc.navigateToLogin();
		}else if(e.getSource() == this.invitationBtn) {
			vc.navigateToGuestInvitationPage();
		}else if(e.getSource() == this.profileBtn) {
			vc.navigateToProfile();
		}
	}	
	
	public GuestHomeView() {
		init();
		setLayout();
		setStyle();
	}
}
