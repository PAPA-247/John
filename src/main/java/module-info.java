module com.papa247.john {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    requires com.jfoenix;
    requires javafx.graphics;
    requires javafx.base;
    requires org.kordamp.iconli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material;

    opens com.papa247.john to javafx.fxml;
    exports com.papa247.john;
    
    opens com.papa247.john.UIComponents to javafx.fxml;
    exports com.papa247.john.UIComponents;
}