package view;

import java.util.ArrayList;
import java.util.List;

import controller.VendorController;
import database.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Event;
import model.User;
import view_controller.ViewController;

public class VendorInvitationView extends BorderPane{

	private VendorNav nav;
	private VBox wrapper;
	private TableView<Event> eventTable;
	private List<Event> events;
	private VendorController controller = new VendorController();
	private User activeUser = Session.getInstance().getLoggedInUser();
	
	private void init() {
		nav = new VendorNav();
		wrapper = new VBox(15);
		eventTable = new TableView<Event>();
		events = new ArrayList<Event>();
	}
	
	private void setLayout() {
		this.wrapper.getChildren().addAll(nav, eventTable);
		this.setTop(wrapper);
	}
	
	private void setTable() {
		TableColumn<Event, String> eventNamecol = new TableColumn<Event, String>("Event Name");
		eventNamecol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
		eventNamecol.setPrefWidth(200);
		
		TableColumn<Event, String> eventDatecol = new TableColumn<Event, String>("Event Date");
		eventDatecol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));
		
		TableColumn<Event, String> eventLocationcol = new TableColumn<Event, String>("Event Location");
		eventLocationcol.setCellValueFactory(new PropertyValueFactory<Event, String>("eventLocation"));
		
		TableColumn<Event, Void> actionCol = new TableColumn<Event, Void>("Action");
		
		actionCol.setCellFactory(param -> new TableCell<Event, Void>(){
			private final Button detailBtn = new Button("Details");
			private final Button accBtn = new Button("Accept");
			{
				detailBtn.setOnAction(event -> {
					Event currEvent = getTableView().getItems().get(getIndex());
					ViewController.getInstance().navigateToEventDetails(currEvent.getEventId());
				});
				
				accBtn.setOnAction(event -> {
					Event currEvent = getTableView().getItems().get(getIndex());
					handleAcceptInvitation(currEvent.getEventId());
				});
			}
			
			protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                    hbox.getChildren().addAll(detailBtn, accBtn);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
		});
		
		this.eventTable.getColumns().addAll(eventNamecol, eventDatecol, eventLocationcol, actionCol);
	}
	
	public void loadData() {
		this.events.clear();
		this.events = controller.getInvitations(activeUser.getEmail());
		ObservableList<Event> eventList = FXCollections.observableArrayList(this.events);
		this.eventTable.setItems(eventList);
	}
	
	private void handleAcceptInvitation(int eventId) {
		boolean temp = controller.acceptInvitation(eventId);
		if(temp) {
			// create success message
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Success");
	        alert.setHeaderText(null);
	        alert.setContentText("Event successfully Accepted!");  // Success message

	        // Show the alert 
	        alert.showAndWait();
	        loadData();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("Operation failed to complete!"); //error message
		}
	}
	
	public VendorInvitationView() {
		init();
		setLayout();
		setTable();
		loadData();
	}

}
