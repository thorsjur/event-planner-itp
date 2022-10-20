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
 * Class for display of events in gui.
 */
public class EventCell extends ListCell<Event> {

    private Text date;
    private Text name;
    private HBox content;
    private Button eventPageBtn;

    /**
     * Constructor.
     */
    public EventCell() {
        super();
        date = new Text();
        name = new Text();
        eventPageBtn = new Button("Read more");
        

        VBox leftContent = new VBox(name, date);
        VBox rightContent = new VBox(eventPageBtn);
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

            ListView<Event> parentListView = this.getListView();
            User user = (User) parentListView.getUserData();
            eventPageBtn.setOnMouseClicked((e) -> {
                FXMLLoader loader = ControllerUtil.getFXMLLoaderWithEventPageFactory(user, event);
                ControllerUtil.setSceneFromChild(loader, parentListView);
            });
            

            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}