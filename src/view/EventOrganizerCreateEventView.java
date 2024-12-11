package view;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class EventOrganizerCreateEventView extends VBox {

    private Label createEventLabel;

    public EventOrganizerCreateEventView() {
        createEventLabel = new Label("Create Event");
        this.getChildren().add(createEventLabel);
    }
}
