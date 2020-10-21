package com.papa247.john;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static boolean debug = true;

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("Loading main window...");
        scene = new Scene(loadFXML("MainWindow"), 640, 480);
        stage.setTitle("TeamPAPA: Project John");
        stage.setScene(scene);
        stage.show();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        System.out.println("Papa is in the house!");
        
        try {
            launch();
        } catch(Exception e) {
            System.out.println("We crashed.");
            com.papa247.john.UIComponents.AlertWindows.showException("Program crash", "It appears we have crashed.", "The John program has closed unsuccessfully.", e);
            System.exit(1);
        }
        
        System.exit(0);
    }

}