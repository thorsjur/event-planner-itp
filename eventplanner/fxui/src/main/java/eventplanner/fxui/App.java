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

