/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.UIComponents;


import java.io.IOException;
import com.papa247.john.App;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.TestCode;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Support.Session;
import com.papa247.john.User.User;
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
    private Button btnExpressLogin;

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
    void listingWindow(ActionEvent event) {
        System.out.println("'Show blank listing window' clicked");
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("UIComponents/Listing.fxml"));
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setTitle("Blank Listing");
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
    void clearAddresses(ActionEvent event) {        for (Address address : DataBases.getAddresses()) {
            address.delete();
        }

    }
    @FXML
    void clearListings(ActionEvent event) {        for (Listing listing : DataBases.getListings()) {
            listing.delete();
        }
    }
    @FXML
    void clearUsers(ActionEvent event) {        for (User user : DataBases.getUsers()) {
            user.delete();
        }

    }
    @FXML
    void listAddresses(ActionEvent event) {
        System.out.println("========ADDRESSES========");
        for (Address address: DataBases.getAddresses()) {
            System.out.println(address.toString());
        }
    }
    @FXML
    void listListings(ActionEvent event) {
        System.out.println("========LISTINGS========");
        for (Listing listing : DataBases.getListings()) {
            System.out.println("Title: " + listing.toString() + " address: " + listing.parent.name);
        }
    }
    
    @FXML
    void save(ActionEvent event) {
        DataBases.save();
    }
    @FXML
    void load(ActionEvent event) {
        DataBases.load();
    }
    
    @FXML
    void btnExpressLogin(ActionEvent event) {
        Session.userLoginToken = "look,ImloggedinLocally";
        Session.user = DataBases.getUser("billy", UsernameLookupType.username);
    }
    
    @FXML
    void testSelection(ActionEvent event) {
        TestCode.selection();
    }
}

