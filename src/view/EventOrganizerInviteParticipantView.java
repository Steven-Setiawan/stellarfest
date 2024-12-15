package view;

import java.util.ArrayList;
import java.util.List;

import controller.EventOrganizerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.User;
import view_controller.ViewController;

public class EventOrganizerInviteParticipantView extends BorderPane{
	
	private int eventId;
	private HBox tableContainer;
	private TableView<User> vendorTable, guestTable;
	private Label title, guestLbl, vendorLbl;
	private Button addBtn, backbtn;
	private VBox vendorContainer, guestContainer, wrapper;
	private List<User> invitedUsers;
	private EventOrganizerController ec;
	private List<User> guests, vendors;

	private void init() {
		tableContainer = new HBox(60);
		vendorTable = new TableView<User>();
		guestTable = new TableView<User>();
		title = new Label("Add participant");
		guestLbl = new Label("Guest List");
		vendorLbl = new Label("Vendor List");
		backbtn = new Button("Back");
		addBtn = new Button("Send Invitation");
		vendorContainer = new VBox(20);
		guestContainer = new VBox(20);
		wrapper = new VBox(30);
		
		invitedUsers = new ArrayList<User>();
		guests = new ArrayList<User>();
		vendors = new ArrayList<User>();
		
		ec = new EventOrganizerController();
		
		invitedUsers = new ArrayList<User>();
		this.backbtn.setOnAction(e -> {
			ViewController.getInstance().navigateBack();
		});
		
		this.addBtn.setOnAction(e -> {
			handleAdd();
		});
	}
	
	private void setLayout() {
		this.wrapper.getChildren().addAll(backbtn, title, tableContainer, addBtn);
		this.tableContainer.getChildren().addAll(guestContainer, vendorContainer);
		this.guestContainer.getChildren().addAll(guestLbl, guestTable);
		this.vendorContainer.getChildren().addAll(vendorLbl, vendorTable);
		this.setTop(wrapper);
	}
	
	private void setTable() {
		setGuestTable();
		setVendorTable();
		loadGuestData();
		loadVendorData();
	}
	
	private void setGuestTable() {
		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
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

        guestTable.getColumns().addAll(usernameColumn, emailColumn, inviteColumn);
	}
	
	private void setVendorTable() {
		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
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

        vendorTable.getColumns().addAll(usernameColumn, emailColumn, inviteColumn);
	}
	
	private void loadGuestData() {
		guests.clear();
		guests = ec.getGuests(eventId);
		ObservableList<User> guestList = FXCollections.observableArrayList(guests);
		this.guestTable.setItems(guestList);
	}
	
	private void loadVendorData() {
		vendors.clear();
		vendors = ec.getVendors(eventId);
		ObservableList<User> vendorList = FXCollections.observableArrayList(vendors);
		this.vendorTable.setItems(vendorList);
	}
	
	private void handleAdd() {
		for (User invitedUser : invitedUsers) {
            ec.createInvitation(eventId, invitedUser, "Pending");
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("Invitations Sent!");
        alert.showAndWait();
        ViewController.getInstance().navigateToEventOrganizerEvent();
	}
	
	public EventOrganizerInviteParticipantView(int eventId) {
		this.eventId = eventId;
		init();
		setLayout();
		setTable();
	}

}
