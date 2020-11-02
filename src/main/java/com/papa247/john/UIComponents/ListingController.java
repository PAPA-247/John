/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 29, 2020
*/

package com.papa247.john.UIComponents;

import java.io.IOException;
import java.util.Optional;
import org.kordamp.ikonli.javafx.FontIcon;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;
import com.jfoenix.controls.JFXTooltip;
import com.papa247.john.App;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
import com.papa247.john.Support.Session;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ListingController {
    
    // TODO [#22]: Duplicate button
    // Have a button that allows a listing to be duplicated.

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
    private JFXButton btnEdit;

    @FXML
    private JFXButton btnViewLease;

    @FXML
    private JFXButton btnReviews;

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
    private Label lblRooms;

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
    private Label lblRooms1;
    
    @FXML
    private JFXTextField txtRooms;

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
    private Amminities[] _amminities = new Amminities[0];
    
    
    public boolean setup(Listing listing) {
        this.listing = listing;
        _amminities = listing.amminities.clone();
        
        refreshData();
        
        setListener(txtBedrooms);
        setListener(txtRooms);
        setListener(txtLeaseLength);
        setListener(txtMonthlyPrice);
        
        lblRooms1.setText("Bathrooms: ");
        
        return true;
    }
    
    public void refreshData() {
        if (listing==null)
            return;
        if (listing!=null) {
            lblTitle.setText(listing.title);
            txtTitle.setText(listing.title);
            lblDescription.setText(listing.description);
            txtDescription.setText(listing.description);
            if (listing.parent==null)
                listing.delete(); // Get him outta here.
//            lblRating.setText(listing.parent.getRating() + "/5 Stars");
        }
        
        if (Session.isLoggedIn()) {
            if (listing.parent.hasManager(Session.user)) {
                // They can edit
                btnEdit.setVisible(true);
                btnEdit.setDisable(false);
                btnViewLease.setVisible(true);
                btnViewLease.setDisable(false);
                btnFavorite.setVisible(false);
                btnFavorite.setDisable(true);
            }
            if (Session.user.accountType==AccountType.PROPERTYMANAGER || Session.user.accountType == AccountType.REALTOR) {
                // It'd be odd... they should just make a new account
                btnViewLease.setVisible(false);
                btnViewLease.setDisable(true);
            }
        } else {
            btnEdit.setVisible(false);
            btnEdit.setDisable(true);
            btnFavorite.setVisible(false);
            btnFavorite.setDisable(true);
        }
        
        
        lblApartmentNumber.setText("Apartment number: " + listing.apartmentNumber);
        txtApartmentNumber.setText(listing.apartmentNumber);
        
        lblBedrooms.setText("Bedrooms: " + listing.bedrooms.length);
        txtBedrooms.setText(String.valueOf(listing.bedrooms.length));
        
        lblRooms.setText("Bathrooms: " + listing.rooms.length);
        txtRooms.setText(String.valueOf(listing.rooms.length));
        
        lblMonthlyPrice.setText("Monthly price: " + listing.monthlyPrice);
        txtMonthlyPrice.setText(String.valueOf(listing.monthlyPrice));
        
        lblLeaseLength.setText("Lease length: " + listing.lease.rentLength);
        txtLeaseLength.setText(String.valueOf(listing.lease.rentLength));
        if (listing.parent!=null)
            lblAddress.setText("Address: " + listing.parent.name);
        
        refreshAminities();
    }
    
    public void setListener(JFXTextField txt) { // Set to digits only. Replaces none digits with "".
        txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,  String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
    }
    
    private StackPane getIcon(Amminities a) {
        Label lbl = new Label();
        StackPane sp = new StackPane();
        JFXTooltip tt = new JFXTooltip();
        tt.setText("Has: " + a.toString());
        
        if (a.toIcon().equals("na"))
            lbl.setText(a.toString());
        else {
            try {
                FontIcon fi = new FontIcon();
                fi.setIconLiteral(a.toIcon());
                fi.setIconSize(16);
                lbl.setGraphic(fi);
                lbl.setTooltip(tt);
            } catch (Exception e) {
                lbl.setText(a.toString());
            }
        }
        sp.getChildren().add(lbl);
        return sp;
    }
    
    private void refreshAminities() {
        hboxIcons.getChildren().clear();
        hboxIcons1.getChildren().clear();
        for (Amminities a : _amminities) {
            hboxIcons.getChildren().add(getIcon(a));
            hboxIcons1.getChildren().add(getIcon(a));
        }
    }
    
    @FXML
    void btnEdit(ActionEvent event) {
        setEditing(true);
    }

    @FXML
    void btnEditAmminities(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("Windows/SetAminities.fxml"));
            Parent root = (Parent)loader.load();
            SetAminitiesController controller = loader.getController();
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
                refreshAminities();
            });
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void btnFavorite(ActionEvent event) {
        if (Session.isLoggedIn())
            if (btnFavorite.isSelected())
                Session.user.favoriteListing(listing);
            else
                Session.user.unfavoriteListing(listing);
        else
            AlertWindows.showMessage("Can't favorite", "Can't favorite that listing.", "Please login before favoriting listings.");
    }

    @FXML
    void btnReviews(ActionEvent event) {
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
//            stage.initStyle(StageStyle.UTILITY);
            stage.initModality(Modality.WINDOW_MODAL);
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
        if (Session.isLoggedIn()) {
            if (!listing.parent.hasManager(Session.user))
                return;
        }
        else
            return;
        
        listing.setApartmentNumber(txtApartmentNumber.getText());
        listing.setTitle(txtTitle.getText());
        listing.setDescription(txtDescription.getText());
        listing.setMonthlyPrice(Double.parseDouble(txtMonthlyPrice.getText()));
        
        int bRooms = Integer.parseInt(txtBedrooms.getText());
        int rRooms = Integer.parseInt(txtRooms.getText());
        listing.bedrooms = new Room[bRooms]; // might work, could fail .. :)
        listing.rooms = new Room[rRooms]; // Nah it works
        listing.lease.rentLength = Double.parseDouble(txtLeaseLength.getText());
        
        if (_amminities.length>0)
            listing.amminities = _amminities.clone();
        
        setEditing(false);
        DataBases.saveListings();
        refreshData();
    }
    
    @FXML
    void btnDelete(ActionEvent event) {
        if (listing.parent.hasManager(Session.user)) {
            Optional<ButtonType> btn = AlertWindows.showPrompt("Delete listing", "Are you sure you want to delete this listing?",
                    "Click \"Yes\" to delete address \"" + listing.title + "\".");
            if (btn.get() == ButtonType.YES) {
                listing.delete(); // k.
            }
        }
    }

    @FXML
    void btnViewLease(ActionEvent event) {
        listing.viewLease();
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

    public Listing getListing() {
        return listing;
    }

    public void setEditing(boolean edit) {
        if (Session.isLoggedIn())
            if (listing.parent.hasManager(Session.user)) {
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
    }

}
