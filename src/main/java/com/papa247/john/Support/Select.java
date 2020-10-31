/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 30, 2020
*/

package com.papa247.john.Support;

import java.io.IOException;
import com.papa247.john.App;
import com.papa247.john.DataBases;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.UIComponents.AlertWindows;
import com.papa247.john.UIComponents.SelectController;
import com.papa247.john.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Select {
    
    public static Address[] getAddress(Address[] addresses, String title, Address[] preSelected) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Windows/Select.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SelectController controller = fxmlLoader.getController();
            Scene reviewScene = new Scene(root, 450,350);
            Stage reviewScreen = new Stage();
            reviewScreen.initModality(Modality.WINDOW_MODAL);
            reviewScreen.setTitle(title);
            reviewScreen.setScene(reviewScene);
            
            reviewScreen.setOnShown(e -> {
                    controller.setSelectionData(addresses);
                    if (preSelected!=null)
                        controller.setPreselectedData(preSelected);
                });
            controller.setSingle(preSelected==null);
            
            reviewScreen.showAndWait();
            
            return controller.getAddresses();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Address[] getAddresses(Address[] addresses, String title) {
        return getAddress(addresses, title, new Address[0]);
    }
    public static Address[] getAddresses(String title) {
        return getAddresses(DataBases.getAddresses(), title);
    }
    public static Address[] getAddresses() {
        return getAddresses("Select multiple addresses...");
    }

    public static Address getAddress(Address[] addresses, String title) {
        Address[] a = getAddress(addresses, title, null);
        if (a.length == 0)
            return null;
        return a[0];
    }
    public static Address getAddress(String title) {
        return getAddress(DataBases.getAddresses(), title);
    }
    public static Address getAddress() {
        return getAddress("Select an address...");
    }
    


    // Listings


    public static Listing[] getListing(Listing[] listings, String title, Listing[] preSelected) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Windows/Select.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SelectController controller = fxmlLoader.getController();
            Scene reviewScene = new Scene(root, 450,350);
            Stage reviewScreen = new Stage();
            reviewScreen.initModality(Modality.WINDOW_MODAL);
            reviewScreen.setTitle(title);
            reviewScreen.setScene(reviewScene);
            
            reviewScreen.setOnShown(e -> {
                controller.setSelectionData(listings);
                if (preSelected!=null)
                    controller.setPreselectedData(preSelected);
            });
            controller.setSingle(preSelected==null);
            
            reviewScreen.showAndWait();

            return controller.getListings();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Listing[] getListings(Listing[] listings, String title) {
        return getListing(listings, title, new Listing[0]);
    }
    public static Listing[] getListings(String title) {
        return getListings(DataBases.getListings(), title);
    }
    public static Listing[] getListings() {
        return getListings("Select multiple listings...");
    }

    public static Listing getListing(Listing[] listings, String title) {
        Listing[] a = getListing(listings, title, null);
        if (a.length == 0)
            return null;
        return a[0];
    }
    public static Listing getListing(String title) {
        return getListing(DataBases.getListings(), title);
    }
    public static Listing getListing() {
        return getListing("Select a listing...");
    }


    // Addresses

    
    public static User[] getUser(User[] users, String title, User[] preSelected) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("Windows/Select.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            SelectController controller = fxmlLoader.getController();
            Scene reviewScene = new Scene(root, 450,350);
            Stage reviewScreen = new Stage();
            reviewScreen.initModality(Modality.WINDOW_MODAL);
            reviewScreen.setTitle(title);
            reviewScreen.setScene(reviewScene);
            
            reviewScreen.setOnShown(e -> {
                controller.setSelectionData(users);
                if (preSelected!=null)
                    controller.setPreselectedData(preSelected);
            });
            controller.setSingle(preSelected==null);
            
            reviewScreen.showAndWait();
            
            return controller.getUsers();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static User[] getUsers(User[] users, String title) {
        return getUser(users, title, new User[0]);
    }
    public static User[] getUsers(String title) {
        return getUsers(DataBases.getUsers(), title);
    }
    public static User[] getUsers() {
        return getUsers("Select multiple users...");
    }

    public static User getUser(User[] users, String title) {
        User[] a = getUser(users, title, null);
        if (a.length == 0)
            return null;
        return a[0];
    }
    public static User getUser(String title) {
        return getUser(DataBases.getUsers(), title);
    }
    public static User getUser() {
        return getUser("Select a user...");
    }
}
