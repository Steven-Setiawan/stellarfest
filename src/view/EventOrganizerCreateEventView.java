package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
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
    private EventOrganizerController ec;
    private ViewController vc = ViewController.getInstance();
    private TableView<User> userTable;
    private List<User> invitedUsers, usersList;
    private EONavbar navBar;


    public EventOrganizerCreateEventView() {
    	init();
    	setLayout();
    	setStyle();
    	setTable();
    	loadUsers();
    }
    
    private void init() {
    	this.navBar = new EONavbar();
        createEventLabel = new Label("Create Event");

        eventNameTf = new TextField();
        eventLocationTf = new TextField();
        eventDescriptionTf = new TextField();
        eventDateDp = new DatePicker();
        eventDateDp.setPromptText("Event Date");

        eventNameTf.setPromptText("Event Name");	
        eventLocationTf.setPromptText("Event Location");
        eventDescriptionTf.setPromptText("Event Description");

        submitButton = new Button("Create Event");

        ec = new EventOrganizerController();
        
        userTable = new TableView<User>();
        invitedUsers = new ArrayList<User>();
        usersList = new ArrayList<User>();
        
        submitButton.setOnAction(e -> 
        	handleSubmit());
    }
    
    private void setLayout() {
        this.getChildren().addAll(navBar, createEventLabel, createForm(), userTable, submitButton);
        this.setSpacing(15);
    }
    
    private void setStyle() {
        submitButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px;");
        createEventLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-padding: 10px;");
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

    private void setTable() {
        TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> roleColumn = new TableColumn<>("Role");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("roleName"));
        
        TableColumn<User, Void> inviteColumn = new TableColumn<>("Invite");

        inviteColumn.setCellFactory(new Callback<TableColumn<User, Void>, TableCell<User, Void>>() {
            @Override
            public TableCell<User, Void> call(TableColumn<User, Void> param) {
                return new TableCell<User, Void>() {
                	private final CheckBox inviteCheckBox = new CheckBox();

                    {
                        inviteCheckBox.setOnAction(event -> {
                            User user = getTableView().getItems().get(getIndex());
                            if (inviteCheckBox.isSelected()) {
                                if (!invitedUsers.contains(user)) {
                                	invitedUsers.add(user);
                                }
                            } else {
                                invitedUsers.remove(user);
                            }
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            User user = getTableView().getItems().get(getIndex());
                            inviteCheckBox.setSelected(invitedUsers.contains(user));
                            setGraphic(inviteCheckBox);
                        }
                    }
                	
                };
            }
        });

        userTable.getColumns().addAll(usernameColumn, emailColumn, roleColumn, inviteColumn);
        
    }
    
    
    //////Isi data Table//////

    private void loadUsers() {
        List<User> users = ec.getUsersWithRole(0, 3); 
        usersList.clear();
        usersList.addAll(users);
        ObservableList<User> listUser = FXCollections.observableArrayList(this.usersList);
        this.userTable.setItems(listUser);
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

        int eventId = ec.createEvent(eventName, formattedEventDate, eventLocation, eventDescription, currentUser.getId());
        if (eventId > 0) {
            for (User invitedUser : invitedUsers) {
                ec.createInvitation(eventId, invitedUser, "Pending");
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
