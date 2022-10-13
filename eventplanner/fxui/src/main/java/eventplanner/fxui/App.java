package eventplanner.fxui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Sets stage and launches app.
 */
public class App extends Application {

    private Scene scene;

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
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("LoginScreen.fxml"));
        Parent parent = fxmlLoader.load();
        this.scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public Scene getScene() {
        return this.scene;
    }

    public static void main(String[] args) {
        launch();
    }
}
