/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 29, 2020
*/

package com.papa247.john.UIComponents;

import java.io.IOException;
import org.kordamp.ikonli.javafx.FontIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import com.jfoenix.controls.JFXTooltip;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
import com.papa247.john.Support.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ListingController {

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
    private Label lblDescription;
    
    @FXML
    private Label lblRating;

    @FXML
    private AnchorPane apButtons;

    @FXML
    private HBox hboxButtons;

    @FXML
    private JFXRippler btnEdit;

    @FXML
    private JFXRippler btnSignLease;

    @FXML
    private JFXRippler btnReviews;

    @FXML
    private JFXToggleNode btnFavorite;

    @FXML
    private AnchorPane apListingBottom;

    @FXML
    private AnchorPane apDescription;

    @FXML
    private AnchorPane apInfo;

    @FXML
    private VBox vboxInfo;

    @FXML
    private Label lblApartmentNumber;

    @FXML
    private Label lblBedrooms;

    @FXML
    private Label lblMonthlyPrice;

    @FXML
    private Label lblLeaseLength;

    @FXML
    private Label lblAddress;

    @FXML
    private JFXButton btnViewParent;

    @FXML
    private AnchorPane apIcons;

    @FXML
    private HBox hboxIcons;

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
    private Label lblApartmentNumber1;

    @FXML
    private JFXTextField txtApartmentNumber;

    @FXML
    private Label lblBedrooms1;

    @FXML
    private JFXTextField txtBedrooms;

    @FXML
    private Label lblMonthlyPrice1;

    @FXML
    private JFXTextField txtMonthlyPrice;

    @FXML
    private Label lblLeaseLength1;

    @FXML
    private JFXTextField txtLeaseLength;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private AnchorPane apIcons1;

    @FXML
    private HBox hboxIcons1;

    @FXML
    private JFXButton btnEditAmminities;

    private Listing listing;
    private Amminities[] _amminities;
    
    
    public boolean setUp(Listing listing) {
        this.listing = listing;
        if (!listing.isEmpty())
            return false;
        
        refreshData();
        
        
        return true;
    }
    
    public void refreshData() {
        lblTitle.setText(listing.title);
        txtTitle.setText(listing.title);
        lblDescription.setText(listing.description);
        txtDescription.setText(listing.description);
        
        lblRating.setText(listing.parent.getRating() + "/5 Stars");
        
        if (listing.parent.hasManager(Session.user)) {
            // They can edit
            btnEdit.setVisible(true);
            btnEdit.setEnabled(true);
            
            // Can't favorite, or sign lease
            btnSignLease.setVisible(false);
            btnSignLease.setEnabled(false);
            btnFavorite.setVisible(false);
            btnFavorite.setDisable(true);
        }
        if (Session.user!=null)
            if (Session.user.accountType==AccountType.PROPERTYMANAGER || Session.user.accountType == AccountType.REALTOR) {
                // It'd be odd... they should just make a new account
                btnSignLease.setVisible(false);
                btnSignLease.setEnabled(false);
            }
        
        
        lblApartmentNumber.setText("Apartment number: " + listing.apartmentNumber);
        txtApartmentNumber.setText(listing.apartmentNumber);
        
        lblBedrooms.setText("Bedrooms: " + listing.bedrooms.length);
        txtBedrooms.setText(String.valueOf(listing.bedrooms.length));
        
        lblMonthlyPrice.setText("Monthly price: " + listing.monthlyPrice);
        txtMonthlyPrice.setText(String.valueOf(listing.bedrooms.length));
        
        lblLeaseLength.setText("Lease length: " + listing.lease.rentLength);
        txtLeaseLength.setText(String.valueOf(listing.lease.rentLength));
        
        lblAddress.setText("Address: " + listing.parent.name);
        
        _amminities = listing.amminities.clone();
        for (Amminities a : _amminities) {
            String icon = a.toIcon();
            Label lbl = new Label();
            StackPane sp = new StackPane();
            JFXTooltip tt = new JFXTooltip();
            tt.setText("Has: " + a.toString());
            
            if (icon.equals("na"))
                lbl.setText(a.toString());
            else {
                FontIcon fi = new FontIcon();
                fi.setIconLiteral(a.toIcon());
                fi.setIconSize(16);
                lbl.setGraphic(fi);
                lbl.setTooltip(tt);
            }
            sp.getChildren().add(lbl);
            hboxIcons.getChildren().add(sp);
            hboxIcons1.getChildren().add(sp);
        }
    }
    
    @FXML
    void btnEdit(MouseEvent event) {
        apView.setVisible(false);
        apView.setDisable(true);
        apEdit.setVisible(true);
        apEdit.setDisable(false);
    }

    @FXML
    void btnEditAmminities(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("SetAmminities.fxml"));
            Parent root = (Parent)loader.load();
            SetAmminitiesController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Amminities for: " + listing.title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.setOnShown(e -> {
                controller.setUp(_amminities);
            });
            stage.setOnCloseRequest(e -> {
                _amminities = controller.getChecked();
                refreshData();
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnFavorite(ActionEvent event) {
        if (btnFavorite.isSelected())
            Session.user.favoriteListing(listing);
        else
            Session.user.unfavoriteListing(listing);
    }

    @FXML
    void btnReviews(MouseEvent event) {
        viewParent();
    }
    @FXML
    void btnViewParent(ActionEvent event) {
        viewParent();
    }
    
    private void viewParent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Address.fxml"));
            Parent root = (Parent)loader.load();
            AddressController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle(listing.parent.name);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.setOnShown(e -> {
                controller.setUp(listing.parent);
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    @FXML
    void btnSave(ActionEvent event) {
        listing.setApartmentNumber(txtApartmentNumber.getText());
        listing.setTitle(txtTitle.getText());
        listing.setDescription(txtDescription.getText());
        listing.setMonthlyPrice(Double.parseDouble(txtMonthlyPrice.getText()));
        
        int bRooms = Integer.parseInt(txtBedrooms.getText());
        listing.bedrooms = new Room[bRooms]; // might work, could fail .. :)
        listing.rooms = new Room[bRooms];
        listing.lease.rentLength = Double.parseDouble(txtLeaseLength.getText());
        
        listing.amminities = _amminities.clone();
    }

    @FXML
    void btnSignLease(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Windows/Lease.fxml"));
            Parent root = (Parent)loader.load();
            LeaseController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Lease for: " + listing.title);
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.centerOnScreen();
            stage.setOnShown(e -> {
                controller.setIsSigning(true);
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    

    @FXML
    void checkTextAttributes(KeyEvent event) {
        //idk
    }
    
    @FXML
    void validateNumber(KeyEvent event) {
        if (event.getCharacter().matches("\\D")) {
            event.consume(); // Consume non digits
        }
    }

}
