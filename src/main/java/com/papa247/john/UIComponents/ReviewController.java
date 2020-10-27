/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 26, 2020
*/

package com.papa247.john.UIComponents;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.papa247.john.DataBases;
import com.papa247.john.MainWindowController;
import com.papa247.john.Support.Review;
import com.papa247.john.Support.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class ReviewController {

    @FXML
    private AnchorPane apView;

    @FXML
    private Label lblTitle;

    @FXML
    private Label lblRating;

    @FXML
    private Label lblContent;

    @FXML
    private Label lblUsername;

    @FXML
    private Circle imgAvatar;

    @FXML
    private JFXButton btnEdit;

    @FXML
    private AnchorPane apEdit;

    @FXML
    private JFXTextField txtTitle;

    @FXML
    private JFXTextField txtRating;

    @FXML
    private JFXTextArea txtContent;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    public Review review;
    
    @FXML
    void deleteReview(ActionEvent event) {
        System.out.println("[ReviewWindow] Deleting review " + review);
        review.delete();
        DataBases.saveUsers();
        Stage stage = (Stage) btnDelete.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editReview(ActionEvent event) {
        if (Session.user==null)
            return;
        
        if (!Session.user.equals(review.author))
            return;
        
        apEdit.setVisible(true);
        apEdit.setDisable(false);
        apView.setVisible(false);
        apView.setDisable(true);
    }

    @FXML
    void saveReview(ActionEvent event) {
        System.out.println("[ReviewWindow] Saving review " + review);
        review.contents = txtContent.getText();
        review.title = txtTitle.getText();
        review.rating = Double.parseDouble(txtRating.getText());
        DataBases.saveUsers();
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }
    
    public void setEditor(boolean value) {
        if (value)
            editReview(new ActionEvent());
        else {
            apEdit.setVisible(false);
            apEdit.setDisable(true);
            apView.setVisible(true);
            apView.setDisable(false);
        }
    }
    
    public void setup(Review review) {
        System.out.println("[ReviewWindow] Setting up review " + review);
        
        this.review = review;
        lblUsername.setText(review.author.username);
        if (review.author.profilePhotoURL == null || review.author.profilePhotoURL.equals("")) {
            Image stockImage = new Image("https://resources.cnewb.co/CSCE247/profile.jpg");
            imgAvatar.setFill(new ImagePattern(stockImage));
        } else {
            Image im = new Image(review.author.profilePhotoURL, false);
            imgAvatar.setFill(new ImagePattern(im));
        }
        
        lblContent.setText(review.contents);
        lblTitle.setText(review.title);
        lblRating.setText(review.rating + "/5 Stars");
        
        txtTitle.setText(review.title);
        txtContent.setText(review.contents);
        txtRating.setText(Double.toString(review.rating));
        if (Session.user!=null) {
            if (!Session.user.equals(review.author)) {
                btnEdit.setVisible(false);
                btnEdit.setDisable(true);
            }
        }
        else {
            btnEdit.setVisible(false);
            btnEdit.setDisable(true);
        }
    }

}
