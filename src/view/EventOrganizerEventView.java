package view;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class EventOrganizerEventView extends VBox {

    private Label showEventLabel;

    public EventOrganizerEventView() {
        showEventLabel = new Label("Show Event");
        this.getChildren().add(showEventLabel);
    }
}
