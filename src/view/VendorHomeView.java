package view;

import javafx.scene.layout.BorderPane;

public class VendorHomeView extends BorderPane{

	private VendorNav nav;
	
	private void init() {
		nav = new VendorNav();
	}
	
	private void setLayout() {
		this.setTop(nav);
	}
	
	public VendorHomeView() {
		init();
		setLayout();
	}
	
}
