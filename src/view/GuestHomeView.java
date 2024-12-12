package view;

import java.util.Vector;

import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Event;

public class GuestHomeView extends HBox{

	private VBox leftVbox, rightVbox;
	private TableView<Event> eventTable;
	private Vector<Event> events;
	private Label filterLbl, activeLbl;
	private int id;
	
	
	private void init() {
		events = new Vector<Event>();
		
		leftVbox = new VBox();
		rightVbox = new VBox();
		eventTable = new TableView<Event>();
		filterLbl = new Label();
		activeLbl = new Label();
	}
	
	private void setLayout() {
		this.leftVbox.getChildren().addAll(filterLbl);
		this.rightVbox.getChildren().addAll(activeLbl, eventTable);
		this.getChildren().addAll(leftVbox, rightVbox);
	}
	
	private void setTable() {
		
	}
	
	public GuestHomeView() {
		
	}

}
