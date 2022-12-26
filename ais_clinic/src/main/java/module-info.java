module com.example.ais_clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;


    opens com.example.ais_clinic to javafx.fxml;
    exports com.example.ais_clinic;
}