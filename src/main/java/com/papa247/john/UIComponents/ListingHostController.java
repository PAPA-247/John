/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.UIComponents;

import java.io.IOException;
import com.papa247.john.Listing.Listing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    
    /**
     * Set what listings are displayed
     */
    public void displayListings(Listing[] listings) {
        vboxListings.getChildren().clear(); // Reset
        try {
            for (Listing listing : listings) {
                // Create a new 'listing' gui object
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Listing.fxml"));
                AnchorPane root = (AnchorPane)loader.load();
                ListingController controller = loader.getController();
                controller.setUp(listing);
                vboxListings.getChildren().add(root);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
