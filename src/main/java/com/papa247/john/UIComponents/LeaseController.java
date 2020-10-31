/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.UIComponents;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Listing.Lease;
import com.papa247.john.Listing.Signer;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Select;
import com.papa247.john.Support.Session;
import com.papa247.john.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class LeaseController {

    @FXML
    private AnchorPane apSign;

    @FXML
    private AnchorPane apTop;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXButton btnSaveEdit;

    @FXML
    private AnchorPane apMid;

    @FXML
    private TextArea txtContents;

    @FXML
    private AnchorPane apBottom;

    @FXML
    private JFXButton btnSign;

    @FXML
    private JFXButton btnExportPDF;

    @FXML
    private JFXTextField txtSignature;

    @FXML
    private AnchorPane apUsers;

    @FXML
    private JFXButton btnEditUsers;
    
    @FXML
    private Label lblUsers;

    @FXML
    void btnEditUsers(ActionEvent event) {
        User[] users = new User[lease.signers.length];
        for (int i = 0; i<lease.signers.length; i++) {
            users[i] = lease.signers[i].user;
        }
        
        User[] selectedUsers = Select.getUser(DataBases.getUsers(), "Selects user for lease agreement.", users);
        
        
        for (Signer user : lease.signers) {
            boolean found = false;
            for (User usr : selectedUsers) {
                if (usr.equals(user.user)) {
                    selectedUsers = ArrayUtils.remove(selectedUsers, new User[selectedUsers.length-1], usr); // Already there!
                    found = true;
                    break;
                }
            }
            if (!found) { // Signer not in 'selected users'
                // Remove
                lease.signers = ArrayUtils.remove(lease.signers, new Signer[lease.signers.length-1], user);
            }
        }
        
        for (User user : selectedUsers) {
            lease.signers = ArrayUtils.add(lease.signers, new Signer[lease.signers.length+1], new Signer(user));
        }
        
        updateView();
    }

    @FXML
    void btnSaveEdit(ActionEvent event) {
        if (Session.isLoggedIn())
            if (!lease.listing.parent.hasManager(Session.user))
                return;
        
        if (editing) {
            // Save
            lease.title = txtTitle.getText();
            lease.contents = txtContents.getText();
            btnSaveEdit.setText("Edit");
        } else
            btnSaveEdit.setText("Save");
        
        editing = !editing;
        String users = "Users on Lease: ";
        for (Signer signer: lease.signers) {
            users += " • " + signer.user + ((signer.signed)? "(signed)" : "(not signed)");
        }
        lblUsers.setText(users);
        
        updateView();
    }

    @FXML
    void exportPDF(ActionEvent event) {
        // TODO [$5f9dd55be65e40000707176d]: Export lease agreement as a PDF (or text file, doesn't matter)
    }

    @FXML
    void signLease(ActionEvent event) {
        if (Session.isLoggedIn()) {
            String[] split = txtSignature.getText().split(" ");
            if (Session.user.firstName.equalsIgnoreCase(split[0]) && Session.user.lastName.equalsIgnoreCase(split[1]))
                lease.sign(Session.user);
            else
                AlertWindows.showAlert("Failed to sign", "Signatures consist of your first and last name.", "Please be sure to sign the lease like so: \""
                        + Session.user.firstName.toLowerCase() + " " + Session.user.lastName.toLowerCase() + "\"!");
        }
        else
            AlertWindows.showAlert("Can not sign", "You can not sign leases without an account!", "Please make an account from the main window to sign a lease.");
        updateView();
    }

    private Lease lease;
    private boolean editing;
    private User[] users;

    public void setUp(Lease lease) {
        this.lease = lease;
        
        txtTitle.setText(lease.title);
        
        
        String users = "Users on Lease: ";
        for (Signer signer: lease.signers) {
            users += " • " + signer.user + ((signer.signed)? "(signed)" : "(not signed)");
        }
        lblUsers.setText(users);
        
        updateView();
    }
    
    private void updateView() {
        if (Session.isLoggedIn() && editing)
            if (!lease.listing.parent.hasManager(Session.user))
                editing = false; // Can't edit...
        
        System.out.println("Edit mode?" + editing);
        
        txtTitle.setEditable(editing);
        
        txtContents.setEditable(editing);
        if (lease!=null) {
            String contents = lease.contents;
            String usrs = "";
            for (Signer signer : lease.signers) {
                usrs += " " + signer.user.username;
            }
            
            if (!editing) {
                contents = contents.replace("%DATE%", LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                contents = contents.replace("%TENTANTS%", usrs);
                contents = contents.replace("%LANDLOARD%", lease.listing.parent.managers[0].username); // The first manager
                contents = contents.replace("%ADDRESS%", lease.listing.getStreetAddress());
                contents = contents.replace("%PAYMENT_ADDRESS%", lease.listing.getStreetAddress());
                contents = contents.replace("%MONTHLY_PRICE%", Double.toString(lease.listing.monthlyPrice));
                contents = contents.replace("%BEDROOM_COUNT%", Double.toString(lease.listing.bedrooms.length));
                contents = contents.replace("%BATHROOM_COUNT%", Double.toString(lease.listing.rooms.length - lease.listing.bedrooms.length)); // Not right.
                // TODO [$5f9dd55be65e40000707176e]: Start and end dates for lease
                contents = contents.replace("%START_DATE%", LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                contents = contents.replace("%END_DATE%", LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")));
                // TODO [$5f9dd55be65e40000707176f]: Damage cost
            }
            
            txtContents.setText(contents);
        }
        
        
        txtSignature.setDisable(editing);
        btnSign.setDisable(editing);
        btnEditUsers.setDisable(false);
        if (Session.isLoggedIn())
            txtSignature.setEditable(Session.user.accountType==AccountType.STUDENT);
        else
            txtSignature.setEditable(false);
        if (editing)
            btnSaveEdit.setText("Save");
        else
            btnSaveEdit.setText("Edit");
        
        btnSaveEdit.setVisible(false);
        btnSaveEdit.setDisable(true);
        if (Session.isLoggedIn()) {
            if (lease!=null)
                if (lease.listing.parent.hasManager(Session.user)) {
                    btnSaveEdit.setVisible(true);
                    btnSaveEdit.setDisable(false);
                }
        }
    }
    
    public void setEditing(boolean e) {
        editing = e;
        updateView();
    }

    
    public Lease getLease() {
        return lease;
    }
    
}
