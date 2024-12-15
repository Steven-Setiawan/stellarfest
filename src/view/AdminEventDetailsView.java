package view;

import java.util.ArrayList;
import java.util.List;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Event;
import model.User;
import view_controller.ViewController;

public class AdminEventDetailsView extends BorderPane{

	private VBox wrapper;
	private GridPane container;
	private Label eventNameLbl, eventDateLbl, eventLocationLbl, eventDescriptionLbl, eventOrganizerLbl;
	private TextArea eventNameField, eventDateField, eventLocationField, eventDescriptionField, eventOrganizerField;
	private HBox navContainer;
	private Button backBtn;
	private TableView<User> userTable;
	private List<User> users;
	private AdminController ac;
	private int eventId;
	
	private void init() {
		// init all components that we gonna use
		container = new GridPane();
		wrapper = new VBox(20);
		container.setVgap(10);
		container.setHgap(10);
		navContainer = new HBox(10);
		userTable = new TableView<User>();
		users = new ArrayList<User>();
		ac = new AdminController();
		
		backBtn = new Button("Back");
		
		eventNameField = new TextArea();
		eventDateField = new TextArea();
		eventLocationField = new TextArea();
		eventDescriptionField = new TextArea();
		eventOrganizerField = new TextArea();
		
		eventNameLbl = new Label("Event Name");
		eventDateLbl = new Label("Event Date");
		eventLocationLbl = new Label("Event Location");
		eventDescriptionLbl = new Label("Description");
		eventOrganizerLbl = new Label("Event Organizer");
		
		// Set TextArea cannot be edit so read-only
		eventNameField.setEditable(false);
		eventDateField.setEditable(false);
		eventLocationField.setEditable(false);
		eventDescriptionField.setEditable(false);
		eventOrganizerField.setEditable(false);
		
	    // Set a preferred width and height for text area
	    eventNameField.setPrefWidth(250);
	    eventDateField.setPrefWidth(250);
	    eventLocationField.setPrefWidth(250);
	    eventDescriptionField.setPrefWidth(350);
	    eventOrganizerField.setPrefWidth(250);
	    
	    eventNameField.setPrefHeight(50);
	    eventDateField.setPrefHeight(50);
	    eventLocationField.setPrefHeight(50);
	    eventDescriptionField.setPrefHeight(150);
	    eventOrganizerField.setPrefHeight(50);
	    
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
		container.add(eventOrganizerLbl, 0, 4);
		container.add(eventOrganizerField, 1, 4);
		
		this.wrapper.getChildren().addAll(navContainer, container, userTable);
		this.setTop(wrapper);
	}
	
	private void setStyle() {
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.backBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
	}
	
	private void setTable() {
		TableColumn<User, String> usernameColumn = new TableColumn<User, String>("Username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
		
		TableColumn<User, String> emailColumn = new TableColumn<User, String>("Email");
		emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
		
		TableColumn<User, String> roleColumn = new TableColumn<User, String>("Role");
		roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("roleName"));
		
		this.userTable.getColumns().addAll(usernameColumn, emailColumn, roleColumn);
	}
	
	private void loadTableData() {
		ObservableList<User> userList;
		this.users.clear();
		this.users.addAll(ac.getGuestByTransactionId(eventId));
		this.users.addAll(ac.getVendorByTransactionId(eventId));
		userList = FXCollections.observableArrayList(this.users);
		this.userTable.setItems(userList);
	}
	
	private void loadEventDesc() {
		Event event = ac.viewEventDetails(eventId);
		User eventOrganizer = ac.getOrganizerDetail(event.getOrganizerId());
		eventNameField.setText(event.getEventName());
		eventDateField.setText(event.getEventDate());
		eventLocationField.setText(event.getEventLocation());
		eventDescriptionField.setText(event.getEventDescription());
		eventOrganizerField.setText(eventOrganizer.getUsername());
	}
	
	public AdminEventDetailsView(int eventId) {
		this.eventId = eventId;
		init();
		setLayout();
		setStyle();
		setTable();
		loadTableData();
		loadEventDesc();
	}

}
