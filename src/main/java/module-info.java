module com.example.interfacemeta {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.interfacemeta to javafx.fxml;
    exports com.example.interfacemeta;
}