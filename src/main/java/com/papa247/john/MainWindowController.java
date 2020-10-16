package com.papa247.john;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MainWindowController implements Initializable {

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;
    
    @FXML
    private Label lblMain;

    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("SideMenu.fxml"));
            drawer.setSidePane(box);
            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                System.out.println("Menu clicked");
                if (drawer.isOpened())
                    drawer.close();
                else
                    drawer.open();
                
            });
        } catch (IOException e1) {
            lblMain.setText("FAILED TO LOAD");
        }
        
    }
}