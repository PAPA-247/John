/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.UIComponents;


import java.io.IOException;
import com.papa247.john.DataBases;
import com.papa247.john.TestCode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DebugWindowController {
    @FXML
    private Button btnCheckExample;

    @FXML
    void checkUser(ActionEvent event) {
        System.out.println("'Check UserDB' clicked... running code...");
        TestCode.userDB();
    }
    
    @FXML
    void listUsers(ActionEvent event) {
        TestCode.userList();
    }
    
    @FXML
    void loginWindow(ActionEvent event) {
        System.out.println("'Show login window' clicked");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserWindow.fxml"));
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void manageAccount(ActionEvent event) {
        System.out.println("Showing My Account window");
        
    }
    
    @FXML
    void testReviews(ActionEvent event) {
        TestCode.reviews();
    }
    
    @FXML
    void deleteReviews(ActionEvent event) {
        TestCode.deleteReviews();
    }
    
    @FXML
    void listReviews(ActionEvent event) {
        TestCode.listReviews(null);
    }
    
    @FXML
    void save(ActionEvent event) {
        DataBases.save();
    }
    @FXML
    void load(ActionEvent event) {
        DataBases.load();
    }
}

