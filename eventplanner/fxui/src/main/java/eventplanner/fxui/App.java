package eventplanner.fxui;

import java.io.IOException;

import eventplanner.dataaccess.DataAccess;
import eventplanner.dataaccess.LocalDataAccess;
import eventplanner.dataaccess.RemoteDataAccess;
import eventplanner.fxui.util.ControllerUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sets stage and launches application.
 */
public class App extends Application {

    private Scene scene;

    /**
     * Method to be used before gui tests, to ensure they function as expected.
     */
    public static void supportHeadless() {
        if (Boolean.getBoolean("headless")) {
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        DataAccess dataAccess;
        try {
            RemoteDataAccess.assertConnection();
            dataAccess = new RemoteDataAccess();
        } catch (Exception e) {
            dataAccess = new LocalDataAccess();
        }

        FXMLLoader fxmlLoader = ControllerUtil.getFXMLLoaderWithFactory("LoginScreen.fxml", LoginController.class, null,
                dataAccess);
        Parent parent = fxmlLoader.load();
        this.scene = new Scene(parent);

        stage.setResizable(false);
        String title = dataAccess.isRemote() ? "Event Planner - Connected" : "Event Planner - Local data access.";
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch();
    }
}
