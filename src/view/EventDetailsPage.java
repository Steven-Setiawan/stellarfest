package view;

import controller.EventController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Event;
import view_controller.ViewController;

public class EventDetailsPage extends BorderPane{
	
	private int eventId;
	private EventController ec;
	private VBox container;
	private Label eventNameLbl, eventDateLbl, eventLocationLbl, eventDescriptionLbl;
	private TextArea eventNameField, eventDateField, eventLocationField, eventDescriptionField;
	private HBox hBox1, hBox2, hBox3, hBox4, navContainer;
	private Button backBtn;
	
	private void init() {
		// init all components that we gonna use
		container = new VBox();
		hBox1 = new HBox();
		hBox2 = new HBox();
		hBox3 = new HBox();
		hBox4 = new HBox();
		navContainer = new HBox(10);
		
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
		
		// Set padding for text Area
	    eventNameField.setPadding(new Insets(5));
	    eventDateField.setPadding(new Insets(5));
	    eventLocationField.setPadding(new Insets(5));
	    eventDescriptionField.setPadding(new Insets(5));

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

	    // Set margin for text Area
	    HBox.setMargin(eventNameField, new Insets(10));
	    HBox.setMargin(eventDateField, new Insets(10));
	    HBox.setMargin(eventLocationField, new Insets(10));
	    HBox.setMargin(eventDescriptionField, new Insets(10));
		
		backBtn.setOnAction(event -> {
			ViewController.getInstance().navigateBack();
		});
	}
	
	private void setLayout() {
		// put each field to HBox container
		this.hBox1.getChildren().addAll(eventNameLbl, eventNameField);
		this.hBox2.getChildren().addAll(eventDateLbl, eventDateField);
		this.hBox3.getChildren().addAll(eventLocationLbl, eventLocationField);
		this.hBox4.getChildren().addAll(eventDescriptionLbl, eventDescriptionField);
		this.navContainer.getChildren().addAll(backBtn);
		
		this.hBox1.setAlignment(Pos.CENTER_LEFT);
		this.hBox2.setAlignment(Pos.CENTER_LEFT);
		this.hBox3.setAlignment(Pos.CENTER_LEFT);
		this.hBox4.setAlignment(Pos.CENTER_LEFT);
		this.navContainer.setAlignment(Pos.TOP_LEFT);
		
		// put all the HBox Container into a big container
		this.container.getChildren().addAll(hBox1, hBox2, hBox3, hBox4);
		this.setCenter(container);
		this.setTop(navContainer);
	}
	
	private void setStyle() {
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.backBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	}
	
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
