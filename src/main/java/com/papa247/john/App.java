package com.papa247.john;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import com.papa247.john.UIComponents.SplashScreenController;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static boolean debug = true;

    @Override
    public void start(Stage stage) throws IOException {
        try {            
            System.out.println("Papa is in the house!");
            
            // This doesn't really work (just black when loading), so we don't use it for now.
            System.out.println("[App] Loading splash screen");
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Windows/SplashScreen" + ".fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SplashScreenController splashScreenController = fxmlLoader.getController();
            Scene splashScene = new Scene(root, 640,320);
            Stage splashScreen = new Stage();
            splashScreen.initStyle(StageStyle.UNDECORATED);
            splashScreen.setScene(splashScene);
            splashScreen.setOnShown(e -> splashScreenController.loaded());
            //splashScreen.showAndWait(); // Wait for DataBase to finish
            
            
            System.out.println("Loading main window...");
            scene = new Scene(loadFXML("MainWindow"), 640, 480);
            stage.setOnCloseRequest(e -> {
                System.out.println("[MainWindow] Closing...");
                DataBases.save();
            });
            stage.setTitle("TeamPAPA: Project John");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            System.out.println("We crashed.");
            com.papa247.john.UIComponents.AlertWindows.showException("Program crash", "It appears we have crashed.", "The John program has closed unsuccessfully.", e);
            e.printStackTrace();
            System.exit(1);
        }       
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
        System.out.println("Goodbye :(");
        System.exit(0);
    }

}