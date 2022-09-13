module app {
    requires javafx.controls;
    requires javafx.fxml;

    opens project to javafx.graphics, javafx.fxml, javafx.controls;
}
