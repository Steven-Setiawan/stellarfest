package view;

import database.Session;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class AdminHomeView extends BorderPane{
	
	private AdminNav nav;
	private Label welcomeLbl;
	
	
	private void init() {
		nav = new AdminNav();
		welcomeLbl = new Label("Welcome " + Session.getInstance().getLoggedInUser().getUsername());
	}
	
	private void setLayout() {
		this.setTop(nav);
		this.setLeft(welcomeLbl);
	}
	
	private void setStyle() {
		
		this.welcomeLbl.setStyle("-fx-font-size: 24px;");
	}
	
	public AdminHomeView() {
		init();
		setLayout();
		setStyle();
	}


	
}
