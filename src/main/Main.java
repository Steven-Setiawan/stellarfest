package main;

import javafx.application.Application;
import javafx.stage.Stage;
import view.AppView;
import view_controller.ViewController;

public class Main extends Application{
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// set up view pertama kali untuk di tampilkan
		AppView appView = new AppView(primaryStage);
		ViewController viewController = ViewController.getInstance();
		viewController.initialize(primaryStage, appView);
		viewController.navigateToLogin();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
