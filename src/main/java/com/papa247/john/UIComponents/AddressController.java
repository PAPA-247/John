/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.UIComponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Listing.Address;
import com.papa247.john.Support.Session;
import com.papa247.john.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AddressController {

    @FXML
    private AnchorPane apView;

    @FXML
    private AnchorPane apImages;

    @FXML
    private AnchorPane apListingData;

    @FXML
    private AnchorPane apTitleRating;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblRating;

    @FXML
    private Label lblDescription;
    
    @FXML
    private AnchorPane apButtons;

    @FXML
    private HBox hboxButtons;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnViewListings;

    @FXML
    private JFXButton btnLeaveReview;

    @FXML
    private AnchorPane apListingBottom;

    @FXML
    private AnchorPane apDescription;

    @FXML
    private AnchorPane apInfo;

    @FXML
    private VBox vboxInfo;

    @FXML
    private Label lblListingCount;

    @FXML
    private Label lblManagers;

    @FXML
    private AnchorPane apIcons;

    @FXML
    private HBox hboxIcons;

    @FXML
    private ScrollPane spReviews;

    @FXML
    private Label lblNoReviews;

    @FXML
    private VBox vboxReviews;

    @FXML
    private AnchorPane apEdit;

    @FXML
    private AnchorPane apImages1;

    @FXML
    private AnchorPane apListingData1;

    @FXML
    private AnchorPane apTitleRating1;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private AnchorPane apButtons1;

    @FXML
    private HBox hboxButtons1;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private AnchorPane apListingBottom1;

    @FXML
    private AnchorPane apDescription1;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private AnchorPane apInfo1;

    @FXML
    private VBox vboxInfo1;

    @FXML
    private Label lblListingCount1;

    @FXML
    private Label lblManagers1;

    @FXML
    private JFXTextField txtStreetAddress;

    @FXML
    private JFXTextField txtCity;

    @FXML
    private JFXTextField txtState;

    @FXML
    private JFXTextField txtPostalCode;

    private Address address;
    
    @FXML
    void btnDelete(ActionEvent event) {
        if (address.hasManager(Session.user))
            if (AlertWindows.showPrompt("Delete address", "Are you sure you want to delete this address? This *WILL* delete all listings under it!",
                    "Click \"Yes\" to delete address \"" + address.name + "\" and all " + address.listings.length + " of its listings.").get() == ButtonType.YES) {
                DataBases.removeAddress(address); // k.
            }
    }

    @FXML
    void btnEdit(ActionEvent event) {
        apView.setDisable(true);
        apEdit.setDisable(false);
        
        apView.setVisible(false);
        apEdit.setVisible(true);
    }

    @FXML
    void btnLeaveReview(ActionEvent event) {
        DataBases.newReview(address);
    }

    @FXML
    void btnSave(ActionEvent event) {
        // grab all the data.
        // save
        address.name = txtTitle.getText();
        address.description = txtDescription.getText();
        address.streetAddress = txtStreetAddress.getText();
        address.city = txtCity.getText();
        address.state = txtState.getText();
        address.postalCode = txtPostalCode.getText();
        
        DataBases.save();
    }

    @FXML
    void btnViewListings(ActionEvent event) {
        Session.displayListings.run(address.listings);
    }

    public Address getAddress() {
        return address;
    }
    
    public void setEditing(boolean edit) {
        if (edit) {
            apView.setDisable(true);
            apEdit.setDisable(false);
            
            apView.setVisible(false);
            apEdit.setVisible(true);
        } else {
            apView.setDisable(false);
            apEdit.setDisable(true);
            
            apView.setVisible(true);
            apEdit.setVisible(false);
        }
    }
    
    public void setUp(Address address) {
        this.address = address;
        lblTitle.setText(address.name);
        lblRating.setText(address.getRating() + "/5 Stars");
        lblDescription.setText("Located at " + address.streetAddress + ", " + address.city
                + ", " + address.state + " " + address.postalCode + "\n"
                + address.description);
        lblListingCount.setText("Number of listings: " + address.listings.length);
        lblListingCount1.setText("Number of listings: " + address.listings.length);
        
        String man = "Managers: ";
        for (User manager : address.managers) {
            man += "\n" + manager.username;
        }
        lblManagers.setText(man);
        lblManagers1.setText(man);
        
        txtTitle.setText(address.name);
        txtDescription.setText(address.description);
        txtStreetAddress.setText(address.streetAddress);
        txtCity.setText(address.city);
        txtState.setText(address.state);
        txtPostalCode.setText(address.postalCode);
        
        btnEdit.setDisable(true);
        btnEdit.setVisible(false);
        btnLeaveReview.setDisable(true);
        btnLeaveReview.setVisible(false);
        
        // Can edit?
        if (Session.isLoggedIn())
            if (address.hasManager(Session.user)) {
                btnEdit.setDisable(false);
                btnEdit.setVisible(true);
            }
            else if (Session.user.accountType == AccountType.STUDENT || Session.user.accountType == AccountType.ADMINISTRATOR) {
                btnLeaveReview.setDisable(false);
                btnLeaveReview.setVisible(true);
            }
    }

}
