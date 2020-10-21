package com.papa247.john;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {

    @FXML
    private Pane mainContent;
    
    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private Label lblMain;

    
    private void openNewWindow(String FXML, String title) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource(FXML + ".fxml"));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource("SideMenu.fxml"));
            
            drawer.setSidePane(box);
            
            // This gets nasty...
            for (Node node : box.getChildren()) { // For every object in our AnchorPane (which is just a button and a vbox)
                if (node.getId() != null) { // Check if the accessible text is empty...
                    System.out.println(node.getId());
                    if (node.getId().equals("vbox")) { // If it is not and equals 'vbox' we know this is our vbox object
                        // VBox
                        System.out.println("VBox got");
                        for (Node node2 : ((Pane) node).getChildren()) { // Round two, check the vbox's children
                            System.out.println("Item");
                            if (node2.getId()!= null) { // Accessible text there...
                                switch(node2.getId()) {
                                    case "btnDebug": // This 'node2' is the debug button 
                                        node2.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                                            openNewWindow("UIComponents/DebugWindow","Debug");
                                        });
                                        break;
                                }
                            }
                        }
                    } else if (node.getId().equals("btnExit")) {
                        node.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                            System.out.println("Exiting...");
                            // Later, save the database
                            System.exit(0);
                        });
                    }
                }
            }
                        
            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                System.out.println("Menu clicked");
                if (drawer.isOpened() || drawer.isOpening())
                    drawer.close();
                else
                    drawer.open();
                
            });
            
            // TODO: Open window AFTER loading main
            // The Debug window opens before the MainWindow has a chance. Needs to be moved into a "isVisible" method (or something similar)
            // (truthfully this is a test of the automatic TODO system)
            
            // Auto open debug window
            if (App.debug)
                openNewWindow("UIComponents/DebugWindow","Debug");
            
            
        } catch (IOException e1) {
            lblMain.setText("FAILED TO LOAD");
        }
        
    }
}