module eventplanner.fxui {
    requires com.fasterxml.jackson.databind;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;

    requires java.net.http;

    requires eventplanner.core;

    opens eventplanner.fxui to javafx.graphics, javafx.fxml;

}
