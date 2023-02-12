module com.iot.iot {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;
    requires java.logging;


    opens com.iot to javafx.fxml;
    exports com.iot;
    exports com.iot.controllers;
    opens com.iot.controllers to javafx.fxml;
    exports com.iot.HttpClient;
    opens com.iot.HttpClient to javafx.fxml;
}