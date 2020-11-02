/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.UIComponents;

import java.io.IOException;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.SearchData;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Support.CallBack;
import com.papa247.john.Support.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class ListingHostController {

    @FXML
    private ScrollPane spSearch;

    @FXML
    private ScrollPane spListings;

    @FXML
    private VBox vboxListings;
    
    private Listing[] listings;
    
    
    @FXML
    void newAddress(ActionEvent event) {
        if (Session.user != null)
            if (Session.user.accountType != AccountType.STUDENT)
                DataBases.newAddress();
            else
                AlertWindows.showMessage("Notice", "Cannot create listings as anon.", "You must login as a manager before you can create a listing!");
        else
            AlertWindows.showMessage("Notice", "Cannot create addresses as anon.", "You must login as a manager before you can create an address!");
    }

    @FXML
    void newListing(ActionEvent event) {
        if (Session.user != null)
            if (Session.user.accountType != AccountType.STUDENT)
                DataBases.newListing();
            else
                AlertWindows.showMessage("Notice", "Cannot create listings as anon.", "You must login as a manager before you can create a listing!");
        else
            AlertWindows.showMessage("Notice", "Cannot create listings as anon.", "You must login as a manager before you can create a listing!");
    }
    
    @FXML
    void refreshData(ActionEvent event) {
        refresh();
    }
    
    private void refresh() {
        vboxListings.getChildren().clear(); // Reset
        try {
            for (Listing listing : listings) {
                // Create a new 'listing' gui object
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                ListingController controller = loader.getController();
                controller.setup(listing);
                vboxListings.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void initialize() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SearchPannel.fxml"));
            AnchorPane searchPane = (AnchorPane)loader.load();
            SearchPannelController controller = loader.getController();
            //controller.setUpdateMethod();
            Session.searchListings = new CallBack() {
                public void run(Object searchData) {
                    displayListings(DataBases.getListings((SearchData) searchData));
                }
            };
            Session.displayListings = new CallBack() {
                public void run(Object listings) {
                    displayListings((Listing[]) listings);
                }
            };
            controller.setListeners();
            spSearch.setContent(searchPane);
        } catch(IOException e) {
            System.out.println("Failed to load search pane.");
            e.printStackTrace();
        }
    }
    
    /**
     * Set what listings are displayed
     */
    public void displayListings(Listing[] listings) {
        this.listings = listings;
        refresh();
    }
    
}
