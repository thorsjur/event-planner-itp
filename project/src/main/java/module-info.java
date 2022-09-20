module app {
    requires transitive com.fasterxml.jackson.databind;
    requires javafx.controls;
    requires javafx.fxml;

    opens project to javafx.graphics, javafx.fxml, javafx.controls;
}
