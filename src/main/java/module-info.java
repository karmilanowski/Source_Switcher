module PZU {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.solix.main;
    opens pl.solix.controllers to javafx.fxml;

}