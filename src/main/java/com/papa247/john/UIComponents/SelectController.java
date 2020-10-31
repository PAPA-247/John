/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.UIComponents;

import java.util.ArrayList;
import com.jfoenix.controls.JFXChip;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.User.User;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SelectController {

    @FXML
    private Label lblTitle;
    
    @FXML
    private JFXComboBox<String> comboAddresses;
    @FXML
    private JFXComboBox<String> comboUsers;
    @FXML
    private JFXComboBox<String> comboListings;
    
    @FXML
    private JFXChipView<String> chipAddresses;
    @FXML
    private JFXChipView<String> chipUsers;
    @FXML
    private JFXChipView<String> chipListings;
    
    @FXML
    private AnchorPane apData;

    @FXML
    private Label lblName;

    @FXML
    private Label lblDescription;
    
    @FXML
    private AnchorPane apRoot;
    
    @FXML
    private AnchorPane apSelectors;
    
    private boolean single = false;
    private int viewMode = 0;
    
    private ArrayList<Address> addresses = new ArrayList<Address>(0); // The data we've selected.
    private ArrayList<Listing> listings = new ArrayList<Listing>(0);
    private ArrayList<User> users = new ArrayList<User>(0);
    
    private Address[] selectionAddresses; // The data we're selecting from
    private Listing[] selectionListings;
    private User[] selectionUsers;
    
    @FXML
    void initialize() {
        comboAddresses.setOnAction(e -> {
            Address address = getAddress();
            
            if (address==null)
                return;
            
            lblName.setText(address.name);
            lblDescription.setText(address.description);
            if (!single)
                chipAddresses.getChips().add(address.name);
            addresses.add(address); // forgot this ... :face_palm:
        });
        chipAddresses.getChips().addListener((ListChangeListener.Change<? extends String> c) -> {
            c.next();
            c.getAddedSubList().forEach(e-> {
                for (Address address : selectionAddresses) {
                    if (address.name.equalsIgnoreCase(e.toString()))
                        addresses.add(address);
                }
            }); 
        });
        
        
        comboListings.setOnAction(e -> {
            Listing listing = getListing();
            
            if (listing==null)
                return;
            
            lblName.setText(listing.title);
            lblDescription.setText(listing.description);
            if (!single)
                chipListings.getChips().add(listing.title);
            listings.add(listing);
        });
        chipListings.getChips().addListener((ListChangeListener.Change<? extends String> c) -> {
            c.next();
            c.getAddedSubList().forEach(e-> {
                for (Listing listing : selectionListings) {
                    if (listing.title.equalsIgnoreCase(e.toString()))
                        listings.add(listing);
                }
            }); 
        });
        
        
        comboUsers.setOnAction(e -> {
            User user = getUser();
            
            if (user==null)
                return;
            
            lblName.setText(user.username);
            lblDescription.setText("First name: " + user.firstName
                    + "\nLast name: " + user.lastName);
            if (!single)
                chipUsers.getChips().add(user.username);
            users.add(user);
        });
        // Nasty! When a user is added as a chip, add that to the users list.
        chipUsers.getChips().addListener((ListChangeListener.Change<? extends String> c) -> { // On change...
            c.next(); // You have to do this before inspecting
            c.getAddedSubList().forEach(e-> { // For each added item...
                for (User user : selectionUsers) { // For each user... (potentially very, very slow)
                    if (user.username.equalsIgnoreCase(e.toString())) // If the added item is the user...
                        users.add(user); // Add
                }
            }); 
        });
    }
    
        
    public Address getAddress() {
        Address address = null;
        String value = comboAddresses.getValue();
        for (Address a : selectionAddresses) {
            if (a.name.equals(value))
                address = a;
        }
        return address;
    }
    public Listing getListing() {
        Listing listing = null;
        String value = comboListings.getValue();
        for (Listing a : selectionListings) {
            if (a.title.equals(value))
                listing = a;
        }
        return listing;
    }
    public User getUser() {
        User user = null;
        String value = comboUsers.getValue();
        for (User a : selectionUsers) {
            if (a.username.equals(value))
                user = a;
        }
        return user;
    }
    // Multiple
    public Address[] getAddresses() {
        if (addresses.isEmpty()) {
            Address[] cBox = new Address[1];
            cBox[0] = getAddress(); // Should never actually be called.. but
            return cBox;
        }
        
        Address[] a = new Address[addresses.size()];
        Object[] b = addresses.toArray();
        for (int i = 0; i<b.length; i++) {
            a[i] = (Address) b[i];
        }
        
        return a;
    }
    public Listing[] getListings() {
        if (listings.isEmpty()) {
            Listing[] cBox = new Listing[1];
            cBox[0] = getListing();
            return cBox;
        }
        
        Listing[] a = new Listing[listings.size()];
        Object[] b = listings.toArray();
        for (int i = 0; i<b.length; i++) {
            a[i] = (Listing) b[i];
        }
        return a;
    }
    public User[] getUsers() {
        if (users.isEmpty()) {
            User[] cBox = new User[1];
            cBox[0] = getUser();
            return cBox;
        }
        
        User[] a = new User[users.size()];
        Object[] b = users.toArray();
        for (int i = 0; i<b.length; i++) {
            a[i] = (User) b[i];
        }
        return a;
    }
    
    
    
    
    private void updateView() {
        if (single) {
            apData.setVisible(true);
            apData.setDisable(false);
            apRoot.setTopAnchor(apData, 95.0);
        } else {
            apRoot.setTopAnchor(apData, 215.0);
        }
        
        comboUsers.setVisible(false);
        comboUsers.setDisable(true);
        comboAddresses.setVisible(false);
        comboAddresses.setDisable(true);
        comboListings.setVisible(false);
        comboListings.setDisable(true);
        
        chipUsers.setVisible(false);
        chipUsers.setDisable(true);
        chipAddresses.setVisible(false);
        chipAddresses.setDisable(true);
        chipListings.setVisible(false);
        chipListings.setDisable(true);
        
        
        if (viewMode==0) { // User
            comboUsers.setVisible(true);
            comboUsers.setDisable(false);
            if (single)
                lblTitle.setText("Select a user.");
            else {
                lblTitle.setText("Select multiple users.");
                chipUsers.setVisible(true);
                chipUsers.setDisable(false);
            }
        } else if (viewMode == 1) { // Listing
            comboListings.setVisible(true);
            comboListings.setDisable(false);
            if (single)
                lblTitle.setText("Select a listing");
            else {
                lblTitle.setText("Select multiple listings.");
                chipListings.setVisible(true);
                chipListings.setDisable(false);
            }
        } else { // Address
            comboAddresses.setVisible(true);
            comboAddresses.setDisable(false);
            if (single)
                lblTitle.setText("Select an address");
            else {
                lblTitle.setText("Select multiple addresses.");
                chipAddresses.setVisible(true);
                chipAddresses.setDisable(false);
            }
        }
    }
    
    private void setSelection(int view) {
        viewMode = view;
        updateView();
    }
    
    
    public void setSelectionData(User[] users) {
        String[] a = new String[users.length];
        for (int i = 0; i<users.length; i++) {
            a[i] = users[i].username;
            comboUsers.getItems().add(users[i].username);
        }
        
        viewMode = 0;
        
        chipUsers.getSuggestions().addAll(a);
        
        selectionUsers = users;
        
        updateView();
    }
    public void setPreselectedData(User[] users) {
        for (User a : users) {
            //this.users.add(a); // Done by the chip
            chipUsers.getChips().add(a.toString());
        }
    }
    //------------------------------
    public void setSelectionData(Listing[] listings) {
        String[] a = new String[listings.length];
        for (int i = 0; i<listings.length; i++) {
            a[i] = listings[i].title;
            comboListings.getItems().add(listings[i].title);
        }
        
        viewMode = 1;
        
        chipListings.getSuggestions().addAll(a);
        
        selectionListings = listings;
        
        updateView();
    }
    public void setPreselectedData(Listing[] listings) {
        for (Listing a : listings) {
            //this.listings.add(a); // Done by the chip
            chipUsers.getChips().add(a.toString());
        }
    }
    //------------------------------
    public void setSelectionData(Address[] addresses) {
        String[] a = new String[addresses.length];
        for (int i = 0; i<addresses.length; i++) {
            a[i] = addresses[i].name;
            comboAddresses.getItems().add(addresses[i].name);
        }
        
        viewMode = 2;
        
        chipAddresses.getSuggestions().addAll(a);
        
        selectionAddresses = addresses;
        
        updateView();
    }
    public void setPreselectedData(Address[] addresses) {
        for (Address a : addresses) {
            this.addresses.add(a);
            chipAddresses.getChips().add(a.toString());
        }
    }
    
    
    @FXML
    void btnSelect(ActionEvent event) {
        ((Stage) lblTitle.getScene().getWindow()).close(); // Just close
    }
    
    public void setSingle(boolean single) {
        this.single = single;
    }
    
    public void setSelectingUser() {
        setSelection(0);
    }
    public void setSelectingListing() {
        setSelection(1);
    }
    public void setSelectingAddress() {
        setSelection(2);
    }
}