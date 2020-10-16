module com.papa247.john {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.jfoenix;
    requires javafx.graphics;
    requires javafx.base;

    opens com.papa247.john to javafx.fxml;
    exports com.papa247.john;
}