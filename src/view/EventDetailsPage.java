package view;

import controller.EventController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Event;
import view_controller.ViewController;

public class EventDetailsPage extends BorderPane{
	
	private int eventId;
	private VBox wrapper;
	private EventController ec;
	private GridPane container;
	private Label eventNameLbl, eventDateLbl, eventLocationLbl, eventDescriptionLbl;
	private TextArea eventNameField, eventDateField, eventLocationField, eventDescriptionField;
	private HBox navContainer;
	private Button backBtn;
	
	private void init() {
		// init all components that we gonna use
		container = new GridPane();
		wrapper = new VBox(10);
		container.setVgap(10);
		container.setHgap(10);
		navContainer = new HBox(20);
		
		backBtn = new Button("Back");
		
		eventNameField = new TextArea();
		eventDateField = new TextArea();
		eventLocationField = new TextArea();
		eventDescriptionField = new TextArea();
		
		eventNameLbl = new Label("Event Name");
		eventDateLbl = new Label("Event Date");
		eventLocationLbl = new Label("Event Location");
		eventDescriptionLbl = new Label("Description");
		
		// Set TextArea cannot be edit so read-only
		eventNameField.setEditable(false);
		eventDateField.setEditable(false);
		eventLocationField.setEditable(false);
		eventDescriptionField.setEditable(false);
		
	    // Set a preferred width and height for text area
	    eventNameField.setPrefWidth(250);
	    eventDateField.setPrefWidth(250);
	    eventLocationField.setPrefWidth(250);
	    eventDescriptionField.setPrefWidth(350);
	    
	    eventNameField.setPrefHeight(50);
	    eventDateField.setPrefHeight(50);
	    eventLocationField.setPrefHeight(50);
	    eventDescriptionField.setPrefHeight(150);
	    
	    eventDescriptionField.setWrapText(true);


		backBtn.setOnAction(event -> {
			ViewController.getInstance().navigateBack();
		});
	}
	
	private void setLayout() {
		// put each field to HBox container
		this.navContainer.getChildren().addAll(backBtn);
		this.navContainer.setAlignment(Pos.TOP_LEFT);
		
		// Add components to GridPane (position them in rows and columns)
		container.add(eventNameLbl, 0, 0); // Row 0, Column 0
		container.add(eventNameField, 1, 0); // Row 0, Column 1
		container.add(eventDateLbl, 0, 1); // Row 1, Column 0
		container.add(eventDateField, 1, 1); // Row 1, Column 1
		container.add(eventLocationLbl, 0, 2); // Row 2, Column 0
		container.add(eventLocationField, 1, 2); // Row 2, Column 1
		container.add(eventDescriptionLbl, 0, 3); // Row 3, Column 0
		container.add(eventDescriptionField, 1, 3); // Row 3, Column 1
		
		this.wrapper.getChildren().addAll(navContainer, container);
		this.setTop(wrapper);
	}
	
	private void setStyle() {
		// menambahkan style
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.backBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	}
	
	// load data ke field yang ada mengenai event
	private void loadData() {
		Event event = ec.viewEventDetails(eventId);
		eventNameField.setText(event.getEventName());
		eventDateField.setText(event.getEventDate());
		eventLocationField.setText(event.getEventLocation());
		eventDescriptionField.setText(event.getEventDescription());
	}
	
	public EventDetailsPage(int eventId) {
		this.eventId = eventId;
		ec = new EventController();
		
		init();
		setLayout();
		loadData();
		setStyle();
	}

}
