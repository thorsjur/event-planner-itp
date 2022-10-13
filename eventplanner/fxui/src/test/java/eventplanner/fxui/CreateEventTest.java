package eventplanner.fxui;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.service.query.NodeQuery;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
public class CreateEventTest extends ApplicationTest {
    
    @BeforeAll
    public static void setupHeadless() {
        App.supportHeadless();
    }

    @Override
    public void start(Stage stage) throws Exception {
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        final Parent parent = fxmlLoader.load();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    @Test
    public void createValidEvent() {
        clickOn("#btnConfirm");
        clickOn("#createEventButton");
        
        clickOn("#startDatePicker").write("10/9/2023");
        clickOn("#startTimeField").write("13:00");
        clickOn("#endDatePicker").write("11/10/2023");
        clickOn("#endTimeField").write("13:00");
        clickOn("#nameField").write("TestName");
        clickOn("#locationField").write("TestLoc");
        clickOn("#descField").write("TestDesc");
        clickOn("#createButton");
    }
}