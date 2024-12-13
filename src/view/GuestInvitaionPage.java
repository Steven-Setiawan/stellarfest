package view;

import java.util.List;
import java.util.Vector;

import controller.GuestController;
import database.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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

public class GuestInvitaionPage extends BorderPane implements EventHandler<ActionEvent>{

	private VBox vBoxContainer;
	private HBox hBoxContainer, navContainer;
	private TableView<Event> eventTable;
	private ComboBox<String> filterOptions;
	private Label filterLbl;
	private Button backBtn, profileBtn, logoutBtn;
	private GuestController gc = new GuestController();
	private User activeUser = Session.getInstance().getLoggedInUser();
	private List<Event> events;
	private ViewController vc;
	
	private void init() {
		vc = ViewController.getInstance();
		
		events = new Vector<Event>();
		
		vBoxContainer = new VBox();
		hBoxContainer = new HBox();
		navContainer = new HBox(10);
		eventTable = new TableView<Event>();
		filterOptions = new ComboBox<String>();
		filterLbl = new Label("Filter: ");
		backBtn = new Button("Back");
		profileBtn = new Button("Profile");
		logoutBtn = new Button("Logout");
		
		filterOptions.getItems().addAll("Pending", "Accepted");
		filterOptions.setOnAction(event -> {
			setTableData(filterOptions.getValue().toString());
		});
		filterOptions.setValue("Pending");
		
		this.backBtn.setOnAction(this);
		this.logoutBtn.setOnAction(this);
		this.profileBtn.setOnAction(this);
	}
	
	private void setLayout() {
		this.vBoxContainer.getChildren().addAll(hBoxContainer ,eventTable);
		this.vBoxContainer.setAlignment(Pos.CENTER);
		
		this.hBoxContainer.getChildren().addAll(filterLbl, filterOptions);
		
		this.navContainer.getChildren().addAll(backBtn, profileBtn, logoutBtn);
		this.navContainer.setAlignment(Pos.TOP_LEFT);
		
		this.setTop(navContainer);
		this.setCenter(vBoxContainer);
	}
	
	private void setStyle() {
		this.navContainer.setStyle("-fx-background-color: #333; -fx-padding: 10px;");
		this.backBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.profileBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
		this.logoutBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");
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
					vc.navigateToEventDetails(currEvent.getEventId());
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
                    if(filterOptions.getValue().equalsIgnoreCase("pending")) {
                    	hbox.getChildren().addAll(detailBtn, accBtn);
                    }else if(filterOptions.getValue().equalsIgnoreCase("Accepted")) {
                    	hbox.getChildren().addAll(detailBtn);
                    }
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
		});
		
		this.eventTable.getColumns().addAll(eventNamecol, eventDatecol, eventLocationcol, actionCol);
	}
	
	private void setTableData(String status) {
		this.events.clear();
		this.events = gc.getInvitations(activeUser.getId(), status);
		ObservableList<Event> inivitationList = FXCollections.observableArrayList(this.events);
		this.eventTable.setItems(inivitationList);
	}
	
	private void handleAcceptInvitation(int eventId) {
		boolean temp = gc.acceptInvitation(eventId);
		if(temp) {
			// create success message
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Success");
	        alert.setHeaderText(null);
	        alert.setContentText("Operation completed successfully!");  // Success message

	        // Show the alert 
	        alert.showAndWait();
	        filterOptions.setValue("Accepted");
	        setTableData(filterOptions.getValue().toString());
		}else {
			Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("Operation failed to complete!"); //error message
		}
	}
	
	@Override
	public void handle(ActionEvent e) {
		if(e.getSource() == this.logoutBtn) {
			vc.navigateToLogin();
		}else if(e.getSource() == this.backBtn) {
			vc.navigateBack();
		}else if(e.getSource() == this.profileBtn) {
			vc.navigateToProfile();
		}
	}

	public GuestInvitaionPage() {
		init();
		setLayout();
		setStyle();
		setTable();
		setTableData(filterOptions.getValue().toString());
	}

}
