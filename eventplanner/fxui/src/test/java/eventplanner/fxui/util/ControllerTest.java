package eventplanner.fxui.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import eventplanner.fxui.App;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
public class ControllerTest {

    private App app = new App();

    @Start
    public void onStart(Stage stage) throws Exception {
        stage.setScene(app.getScene());
        stage.show();
    }

    @Test
    public void test(FxRobot robot) {
        robot.clickOn("#btnCancel");
    }

}
