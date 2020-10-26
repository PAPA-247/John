/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 25, 2020
*/

package com.papa247.john.UIComponents;

import java.net.URL;
import java.util.ResourceBundle;
import com.papa247.john.DataBases;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SplashScreenController {
    
    @FXML
    private Label lblVersion;

    @FXML
    private Label lblStatus;
    
    public void loaded() {
        Stage stage = (Stage) lblStatus.getScene().getWindow();
        stage.getScene().getRoot().layout();
        
        
        // Load here. (We're visible now)
            
        lblStatus.setText("Setting up data bases");
        DataBases.load(lblStatus);
            
        // All done
            
        try {
            Thread.sleep(500);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        stage.close();
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        lblVersion.setText("Version " + getClass().getPackage().getImplementationVersion().toString());
    }
}
