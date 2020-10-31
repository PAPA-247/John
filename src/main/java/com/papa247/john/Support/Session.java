/**
* PAPA-247: Project JOHN
*
*   Session: keeps various of application state information (mainly username state)
*
* File created by cnewb on Oct 21, 2020
*/

package com.papa247.john.Support;

import java.io.IOException;
import com.papa247.john.App;
import com.papa247.john.UIComponents.UserWindowController;
import com.papa247.john.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Session {
    public static User user;
    public static String userLoginToken; // RocketFuel (may be used later for remote API calls and when WE'RE not authenticating users ourselves)
    
    public static CallBack updateListings;
    
    
    public Session() {}
    
    public static boolean login(User user, char[] password) {
        // For now we're not authenticating with a remote api, so we have the final say in whether they are 'logged in' or not.
        // You should NEVER make a login system CLIENT SIDE, UNLESS you can prove the code is safe/secure... yada,yada
        
        if (user == null || password == null) {
            login();
        }
        
        if (user.isPassword(password)) {
            Session.user = user;
            userLoginToken = "SonyPS4LevelRandomString";
            return true;
        }
        return false;
    }
    public static boolean login() {
        // Open window.
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Windows/UserWindow.fxml"));
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(new Scene(root, 450, 500));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.APPLICATION_MODAL); // 'popup' style, can't use other windows
            stage.centerOnScreen();
            stage.showAndWait();
            return (Session.user!=null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public static boolean logout() {
        user = null;
        userLoginToken = null;
        return true;
    }
    
    /**
     * Opens the MyAccount window for the current account logged in.
     */
    public static void openMyAccount() {
        if (user==null || userLoginToken==null)
            return;
        
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Windows/UserWindow.fxml"));
            Parent root = (Parent)loader.load();
            UserWindowController control = loader.getController();
            
            Stage stage = new Stage();
            stage.setTitle("My Account");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.sizeToScene();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.centerOnScreen();
            
            control.myAccount();
            
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isLoggedIn() {
        if (user!=null && userLoginToken!=null)
            return (userLoginToken != "");
        return false;
    }
}
