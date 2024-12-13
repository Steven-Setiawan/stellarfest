package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import controller.EventOrganizerController;
import database.Session;
import model.User;
import view_controller.ViewController;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EventOrganizerCreateEventView extends VBox {

    private Label createEventLabel;
    private TextField eventNameTf, eventLocationTf, eventDescriptionTf;
    private DatePicker eventDateDp;
    private Button submitButton;
    private EventOrganizerController eventOrganizerController;
    private ViewController vc = ViewController.getInstance();

    private TableView<User> userTable;
    private ObservableList<User> usersList;
    private List<User> invitedUsers;

    private HBox navBar;
    private Button homeButton, profileButton, eventButton, logoutButton;

    public EventOrganizerCreateEventView() {
        createEventLabel = new Label("Create Event");
        createEventLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px;");

        eventNameTf = new TextField();
        eventLocationTf = new TextField();
        eventDescriptionTf = new TextField();
        eventDateDp = new DatePicker();
        eventDateDp.setPromptText("Event Date");

        eventNameTf.setPromptText("Event Name");
        eventLocationTf.setPromptText("Event Location");
        eventDescriptionTf.setPromptText("Event Description");

        submitButton = new Button("Create Event");
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");

        eventOrganizerController = new EventOrganizerController();

        invitedUsers = new ArrayList<>();
        usersList = FXCollections.observableArrayList();

        userTable = new TableView<>();
        setUpUserTable();

        setUpNavBar();
        
        submitButton.setOnAction(e -> 
        	handleSubmit());

        this.getChildren().addAll(navBar, createEventLabel, createForm(), userTable, submitButton);
        this.setSpacing(15);
    }
    
    /////////////////////////NavBAr/////////////////////////
    
    private void setUpNavBar() {
        navBar = new HBox(10);
        navBar.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
        homeButton = new Button("Home");
        profileButton = new Button("Profile");
        eventButton = new Button("Event");
        logoutButton = new Button("Logout");

        homeButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        profileButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        eventButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        logoutButton.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        homeButton.setOnAction(e -> navigate("home"));
        profileButton.setOnAction(e -> navigate("profile"));
        eventButton.setOnAction(e -> navigate("event"));
        logoutButton.setOnAction(e -> navigate("logout"));

        navBar.getChildren().addAll(homeButton, profileButton, eventButton, logoutButton);
    }

    private void navigate(String navigationType) {
        switch (navigationType) {
            case "home":
            	vc.navigateToEventOrganizerHome();
                System.out.println("Navigating to Home");
                break;
            case "profile":
            	vc.navigateToProfile();
                System.out.println("Navigating to Profile");
                break;
            case "event":
            	vc.navigateToEventOrganizerEvent();
                System.out.println("Navigating to Event");
                break;
            case "logout":
                vc.navigateToLogin();
                System.out.println("Logging out...");
                break;
            default:
                System.out.println("Unknown navigation type: " + navigationType);
        }
    }
    
    
    /////////////////////////Form/////////////////////////

    private VBox createForm() {
        VBox form = new VBox(10);
        Label eventNameLabel = new Label("Event Name:");
        eventNameLabel.setStyle("-fx-font-size: 14px;");

        Label eventDateLabel = new Label("Event Date:");
        eventDateLabel.setStyle("-fx-font-size: 14px;");

        Label eventLocationLabel = new Label("Event Location:");
        eventLocationLabel.setStyle("-fx-font-size: 14px;");

        Label eventDescriptionLabel = new Label("Event Description:");
        eventDescriptionLabel.setStyle("-fx-font-size: 14px;");

        form.getChildren().addAll(eventNameLabel, eventNameTf, eventDateLabel, eventDateDp, eventLocationLabel, eventLocationTf, eventDescriptionLabel, eventDescriptionTf);
        return form;
    }

    private void setUpUserTable() {
        TableColumn<User, Integer> rowNumberColumn = new TableColumn<>("No.");
        rowNumberColumn.setCellValueFactory(param -> 
            new javafx.beans.value.ObservableValueBase<Integer>() {
                @Override
                public Integer getValue() {
                    return userTable.getItems().indexOf(param.getValue()) + 1;
                }
            });

        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, Void> inviteColumn = new TableColumn<>("Invite");

        inviteColumn.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
                    private final Button inviteButton = new Button("Invite");

                    {
                        inviteButton.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex()); 
                            if (!invitedUsers.contains(user)) {         ///validasi user udah diinvite belom
                                invitedUsers.add(user);
                                System.out.println("User invited: " + user.getUsername());
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {  ///Selama row ada data, tambah tombol invite
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(inviteButton);
                        }
                    }
                };
            }
        });

        userTable.getColumns().addAll(rowNumberColumn, usernameColumn, emailColumn, inviteColumn);
        userTable.setItems(usersList);
        loadUsers();
    }
    
    
    //////Isi data Table//////

    private void loadUsers() {
        List<User> users = eventOrganizerController.getUsersWithRole(0, 3); 
        usersList.clear();
        usersList.addAll(users);
    }

    private void handleSubmit() {
        String eventName = eventNameTf.getText().trim();
        LocalDate eventDate = eventDateDp.getValue();
        String eventLocation = eventLocationTf.getText().trim();
        String eventDescription = eventDescriptionTf.getText().trim();

        if (eventName.isEmpty() || eventDate == null || eventLocation.isEmpty() || eventDescription.isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("All fields are required.");
            alert.show();
            return;
        }

        if (eventDate.isBefore(LocalDate.now())) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("The event date must be in the future.");
            alert.show();
            return;
        }

        User currentUser = Session.getInstance().getLoggedInUser();
        if (currentUser == null) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("You must be logged in to create an event.");
            alert.show();
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedEventDate = eventDate.format(formatter);

        int eventId = eventOrganizerController.createEvent(eventName, formattedEventDate, eventLocation, eventDescription, currentUser.getId());
        if (eventId > 0) {
            for (User invitedUser : invitedUsers) {
                eventOrganizerController.createInvitation(eventId, invitedUser, "Pending");
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Event Created and Invitations Sent!");
            alert.show();
            vc.navigateToEventOrganizerHome();
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setHeaderText("Failed to create event.");
            alert.show();
        }
    }
}
