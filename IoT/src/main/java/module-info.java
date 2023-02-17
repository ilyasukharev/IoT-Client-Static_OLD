module com.iot.iot {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.iot to javafx.fxml;
    exports com.iot;
    exports com.iot.controllers;
    opens com.iot.controllers to javafx.fxml;
}