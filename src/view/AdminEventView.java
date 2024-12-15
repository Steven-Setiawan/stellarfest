package view;

import java.util.ArrayList;
import java.util.List;

import controller.AdminController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import view_controller.ViewController;

public class AdminEventView extends BorderPane {

	private AdminController ac;
	private List<Event> events;
	private AdminNav nav;
	private VBox container;
	private TableView<Event> eventTable;
	private Label tableLbl;
	
	
	private void init() {
		ac = new AdminController();
		nav = new AdminNav();
		container = new VBox();
		eventTable = new TableView<Event>();
		events = new ArrayList<Event>();
		tableLbl = new Label("Event List");
	}
	
	private void setLayout() {
		this.container.setSpacing(15);
		this.container.getChildren().addAll(tableLbl, eventTable);
		this.setTop(nav);
		this.setCenter(container);
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
			private final Button detailBtn = new Button("View Details");
			private final Button deleteBtn = new Button("Delete");
			{
				detailBtn.setOnAction(event -> {
					Event currEvent = getTableView().getItems().get(getIndex());
					ViewController.getInstance().navigateToAdminEventDetails(currEvent.getEventId());
				});
				
				deleteBtn.setOnAction(event->{
					Alert alert = new Alert(AlertType.CONFIRMATION);
					alert.setTitle("Confrim???");
					alert.setHeaderText(null);
					alert.setContentText("Confirm to delete");
					
					alert.showAndWait().ifPresent(response -> {
			            if (response == ButtonType.OK) {
							Event currEvent = getTableView().getItems().get(getIndex());
			            	handleDelete(currEvent.getEventId());
			            }
					});
				});
				
			}
			
			protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hbox = new HBox();
                	hbox.getChildren().addAll(detailBtn, deleteBtn);
                    hbox.setSpacing(10);
                    hbox.setAlignment(Pos.CENTER);
                    setGraphic(hbox);
                }
            }
		});
		
		this.eventTable.getColumns().addAll(eventNamecol, eventDatecol, eventLocationcol, actionCol);
	}
	
	private void loadData() {
		events.clear();
		this.events = ac.getAllEvents();
		ObservableList<Event> inivitationList = FXCollections.observableArrayList(this.events);
		this.eventTable.setItems(inivitationList);
	}
	
	private void setStyle() {
		this.tableLbl.setStyle("-fx-font-size: 24px;");
	}
	
	private void handleDelete(int eventId) {
		if(ac.deleteEvent(eventId)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Success");
	        alert.setHeaderText(null);
	        alert.setContentText("User Has Deleted");
			loadData();
			alert.showAndWait();
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Failed");
	        alert.setHeaderText(null);
	        alert.setContentText("delete is fail");
			alert.showAndWait();
		}
	}
	
	public AdminEventView() {
		init();
		setLayout();
		setTable();
		loadData();
		setStyle();
	}



}
