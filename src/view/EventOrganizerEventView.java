package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import controller.EventOrganizerController;
import database.Session;
import model.Event;
import model.User;
import view_controller.ViewController;

public class EventOrganizerEventView extends VBox {

    private Label showEventLabel;
    private EONavbar navBar;
    private ViewController vc = ViewController.getInstance();

    private TableView<Event> eventTable;
    private ObservableList<Event> eventList;
    private EventOrganizerController eventOrganizerController = new EventOrganizerController();

    public EventOrganizerEventView() {
    	this.navBar = new EONavbar();
        showEventLabel = new Label("Show Events");
        showEventLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px;");
        setUpEventTable();

        this.getChildren().addAll(navBar, showEventLabel, eventTable);
        this.setSpacing(15);

        loadEvents();
    }

    /////////////////////////Table/////////////////////////

    private void setUpEventTable() {
        eventTable = new TableView<>();
        eventList = FXCollections.observableArrayList();

        eventTable.setEditable(true);

        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventName"));
        
        eventNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        eventNameColumn.setEditable(true); 

        eventNameColumn.setOnEditCommit(event -> {
            Event updatedEvent = event.getRowValue();
            updatedEvent.setEventName(event.getNewValue());
            updateEventNameInDatabase(updatedEvent);
        });

        TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventDate"));

        TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
        eventLocationColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("eventLocation"));
        
        TableColumn<Event, Void> actionCol = new TableColumn<Event, Void>("Action");
    	
		actionCol.setCellFactory(param -> new TableCell<Event, Void>(){
			private final Button detailBtn = new Button("View Details");
			private final Button inviteBtn = new Button("Invite Participant");
			{
				detailBtn.setOnAction(event -> {
					Event currEvent = getTableView().getItems().get(getIndex());
					ViewController.getInstance().navigateToEventOrganizerEventDetails(currEvent.getEventId());
				});
				
				inviteBtn.setOnAction(event -> {
					Event currEvent = getTableView().getItems().get(getIndex());
					ViewController.getInstance().navigateToEventOrganizerInviteParticipant(currEvent.getEventId());
				});
			}
			
			protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                	hbox.getChildren().addAll(detailBtn, inviteBtn);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
		});

        eventTable.getColumns().addAll(eventNameColumn, eventDateColumn, eventLocationColumn, actionCol);
        
    }
    
    /////////////////////////Data Isi ke Table/////////////////////////

    private void loadEvents() {
        User currentUser = Session.getInstance().getLoggedInUser();
        if (currentUser != null) {
            eventList.clear();
            eventList.addAll(eventOrganizerController.getEventsByOrganizerId(currentUser.getId()));
            eventTable.setItems(eventList);
        }
    }
    
    private void updateEventNameInDatabase(Event event) {
        boolean updated = eventOrganizerController.updateEventDetails(event);
        if (updated) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Event updated successfully.");
            alert.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Failed to update event.");
            alert.show();
        }
    }
}
