package eventplanner.fxui;

import eventplanner.core.Event;
import javafx.geometry.Pos;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * TODO Javadoc.
 */
public class EventCell extends ListCell<Event> {

    private Text date;
    private Text name;
    private Text type;
    private Text location;
    private HBox content;

    /**
     * TODO Javadoc.
     */
    public EventCell() {
        super();
        date = new Text();
        name = new Text();
        type = new Text();
        location = new Text();

        VBox leftContent = new VBox(name, date);
        VBox rightContent = new VBox(type, location);
        rightContent.setAlignment(Pos.CENTER_RIGHT);

        content = new HBox(leftContent, rightContent);

        HBox.setHgrow(leftContent, Priority.ALWAYS);
        HBox.setHgrow(rightContent, Priority.ALWAYS);

    }

    @Override
    protected void updateItem(Event event, boolean empty) {
        super.updateItem(event, empty);
        if (event != null && !empty) {
            date.setText(event.getStartDate().toString());
            name.setText(event.getName());
            type.setText(event.getType().toString());
            location.setText(event.getLocation());

            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}