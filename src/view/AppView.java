package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppView {
	
	// Inisiasi View pertama kali untuk menampung view yang lain
	
	private BorderPane bp;
	
	private void init() {
		this.bp = new BorderPane();
	}

	public AppView(Stage stage) {
		init();
		
		Scene scene = new Scene(bp, 900, 600);
		stage.setScene(scene);
	}
	
	public void setContent(Node container) {
		this.bp.setCenter(container);
	}
}
