module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    exports com.example.demo.controller to javafx.graphics;
    exports com.example.demo.menus;
}
