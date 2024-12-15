package view;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AppView {
	
	private BorderPane bp;
	
	private void init() {
		this.bp = new BorderPane();
	}

	public AppView(Stage stage) {
		init();
		
		Scene scene = new Scene(bp, 900, 600);
//		scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
		stage.setScene(scene);
	}
	
	public void setContent(Node container) {
		this.bp.setCenter(container);
	}
}
