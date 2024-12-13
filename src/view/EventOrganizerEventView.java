package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import controller.EventOrganizerController;
import database.Session;
import model.Event;
import model.User;
import view_controller.ViewController;

public class EventOrganizerEventView extends VBox {

    private Label showEventLabel;
    private HBox navBar;
    private Button homeButton, profileButton, eventCreateButton, logoutButton;
    private ViewController vc = ViewController.getInstance();

    private TableView<Event> eventTable;
    private ObservableList<Event> eventList;
    private EventOrganizerController eventOrganizerController = new EventOrganizerController();

    public EventOrganizerEventView() {
        showEventLabel = new Label("Show Events");
        showEventLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px;");

        setUpNavBar();
        setUpEventTable();

        this.getChildren().addAll(navBar, showEventLabel, eventTable);
        this.setSpacing(15);

        loadEvents();
    }
    
    /////////////////////////NavBAr/////////////////////////

    private void setUpNavBar() {
        navBar = new HBox(10);
        navBar.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
        
        homeButton = new Button("Home");
        profileButton = new Button("Profile");
        eventCreateButton = new Button("Event");
        logoutButton = new Button("Logout");

        homeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        profileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        eventCreateButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        homeButton.setOnAction(e -> navigate("home"));
        profileButton.setOnAction(e -> navigate("profile"));
        eventCreateButton.setOnAction(e -> navigate("event"));
        logoutButton.setOnAction(e -> navigate("logout"));

        navBar.getChildren().addAll(homeButton, profileButton, eventCreateButton, logoutButton);
    }
    
    private void navigate(String navigationType) {
        switch (navigationType) {
            case "home":
                vc.navigateToEventOrganizerHome();
                break;
            case "profile":
                vc.navigateToProfile();
                break;
            case "Create event":
                vc.navigateToEventOrganizerCreateEvent();
                break;
            case "logout":
                vc.navigateToLogin();
                break;
            default:
                System.out.println("Unknown navigation type: " + navigationType);
        }
    }
    
    
/////////////////////////Table/////////////////////////

    private void setUpEventTable() {
        eventTable = new TableView<>();
        eventList = FXCollections.observableArrayList();

        eventTable.setEditable(true);

        TableColumn<Event, String> eventNameColumn = new TableColumn<>("Event Name");
        eventNameColumn.setCellValueFactory(param -> param.getValue().eventNameProperty());
        
        eventNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        eventNameColumn.setEditable(true); 

        eventNameColumn.setOnEditCommit(event -> {
            Event updatedEvent = event.getRowValue();
            updatedEvent.setEventName(event.getNewValue());
            updateEventNameInDatabase(updatedEvent);
        });

        TableColumn<Event, String> eventDateColumn = new TableColumn<>("Event Date");
        eventDateColumn.setCellValueFactory(param -> param.getValue().eventDateProperty());

        TableColumn<Event, String> eventLocationColumn = new TableColumn<>("Event Location");
        eventLocationColumn.setCellValueFactory(param -> param.getValue().eventLocationProperty());

        TableColumn<Event, String> eventDescriptionColumn = new TableColumn<>("Event Description");
        eventDescriptionColumn.setCellValueFactory(param -> param.getValue().eventDescriptionProperty());

//        TableColumn<Event, Void> updateColumn = new TableColumn<>("Update");
//        updateColumn.setCellFactory(new Callback<TableColumn<Event, Void>, TableCell<Event, Void>>() {
//            @Override
//            public TableCell<Event, Void> call(TableColumn<Event, Void> param) {
//                return new TableCell<Event, Void>() {
//                    private final Button updateButton = new Button("Update");
//
//                    {
//                        updateButton.setOnAction(event -> {
//                            Event eventToUpdate = getTableView().getItems().get(getIndex());
//                            updateEventNameInDatabase(eventToUpdate);
//                        });
//                    }
//
//                    @Override
//                    public void updateItem(Void item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (empty) {
//                            setGraphic(null);
//                        } else {
//                            setGraphic(updateButton);
//                        }
//                    }
//                };
//            }
//        });

//        eventTable.getColumns().addAll(eventNameColumn, eventDateColumn, eventLocationColumn, eventDescriptionColumn, updateColumn);
        eventTable.getColumns().addAll(eventNameColumn, eventDateColumn, eventLocationColumn, eventDescriptionColumn);
        eventTable.setItems(eventList);
    }
    
    /////////////////////////Data Isi ke Table/////////////////////////

    private void loadEvents() {
        User currentUser = Session.getInstance().getLoggedInUser();
        if (currentUser != null) {
            eventList.clear();
            eventList.addAll(eventOrganizerController.getEventsByOrganizerId(currentUser.getId()));
        }
    }
    
    
    /////////////////////////Update/////////////////////////

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
