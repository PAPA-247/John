/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.UIComponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.papa247.john.Listing.Lease;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class LeaseController {

    @FXML
    private AnchorPane apView;

    @FXML
    private AnchorPane apSign;

    @FXML
    private AnchorPane apTop;

    @FXML
    private JFXButton btnViewListing;

    @FXML
    private AnchorPane apMid;

    @FXML
    private AnchorPane apBottom;

    @FXML
    private JFXButton btnSign;

    @FXML
    private JFXButton btnExportPDF;

    @FXML
    private JFXTextField txtSignature;

    @FXML
    private AnchorPane apEdit;

    @FXML
    private AnchorPane apTop1;

    @FXML
    private JFXButton btnViewListing1;

    @FXML
    private AnchorPane apMid1;

    @FXML
    private AnchorPane apBottom1;

    @FXML
    private JFXButton btnSign1;

    @FXML
    private JFXButton btnExportPDF1;

    private Lease lease;
    
    @FXML
    void exportPDF(ActionEvent event) {

    }

    @FXML
    void signLease(ActionEvent event) {

    }

    @FXML
    void viewListing(ActionEvent event) {
        
    }

    public void setUp(Lease lease) {
        this.lease = lease;
    }
    
    public void setIsSigning(boolean b) {
        apSign.setVisible(true);
        apSign.setDisable(false);
        
        apView.setDisable(true);
        apView.setVisible(false);
        
        apEdit.setDisable(true);
        apEdit.setVisible(false);
    }

}
