module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    exports com.example.demo.controller to javafx.graphics;
}
