package eventplanner.fxui;

import eventplanner.core.Event;
import eventplanner.core.User;
import eventplanner.fxui.util.ControllerUtil;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class for displaying custom cells in a listview.
 */
public class EventCell extends ListCell<Event> {

    private Text date;
    private Text name;
    private HBox content;
    private Button eventPageButton;
    private DataAccess dataAccess;

    /**
     * Constructor for creating a new instance of EventCell.
     * 
     * @param dataAccess the data access object to be used for registering or
     *                   deregistering to the event in the cell.
     */
    public EventCell(DataAccess dataAccess) {
        super();
        date = new Text();
        name = new Text();
        this.dataAccess = dataAccess.copy();
        eventPageButton = new Button("Read more");

        VBox leftContent = new VBox(name, date);
        VBox rightContent = new VBox(eventPageButton);
        rightContent.setAlignment(Pos.CENTER_RIGHT);

        content = new HBox(leftContent, rightContent);

        HBox.setHgrow(leftContent, Priority.ALWAYS);
        HBox.setHgrow(rightContent, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(Event event, boolean empty) {
        super.updateItem(event, empty);

        HBox graphic = null;
        if (event != null && !empty) {
            date.setText(event.getStartDate().toString().replace("T", " "));
            name.setText(event.getName());

            ListView<Event> parentListView = this.getListView();
            User user = (User) parentListView.getUserData();
            eventPageButton.setOnMouseClicked(e -> {
                FXMLLoader loader = ControllerUtil.getFXMLLoaderWithEventPageFactory(user, event, dataAccess);
                ControllerUtil.setSceneFromChild(loader, parentListView);
            });

            graphic = content;
        }

        setGraphic(graphic);
    }
}