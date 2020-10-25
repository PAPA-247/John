/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 21, 2020
*/

package com.papa247.john.UIComponents;

import java.net.URL;
import java.util.ResourceBundle;
import org.json.JSONObject;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTextField;
import com.papa247.john.App;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Support.Exceptions;
import com.papa247.john.Support.Session;
import com.papa247.john.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginWindowController {

    @FXML
    private AnchorPane apLogin;

    @FXML
    private JFXTextField txtUsername;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    private Label lblError;

    @FXML
    private JFXButton btnLogin;

    @FXML
    private JFXButton btnRegister;

    @FXML
    private AnchorPane apRegister;

    @FXML
    private JFXTextField txtUsernameR;

    @FXML
    private JFXPasswordField txtPasswordR;

    @FXML
    private JFXTextField txtFirstName;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private ToggleGroup accountType;
    
    @FXML
    private JFXRadioButton radStudent;

    @FXML
    private JFXRadioButton radOwner;

    @FXML
    private JFXRadioButton radRealtor;

    @FXML
    private JFXTextField txtStudentID;

    @FXML
    private Label lblError1;

    @FXML
    private JFXButton btnRegisterFinal;
    
    /**
     * Called when the login button is clicked
     * @param event (The event details)
     */
    @FXML
    void loginUser(ActionEvent event) {
        /*
         * First thing: is this user real?
         * Second thing: login the user (if failed, tell them the password is wrong)
         */
        
        // Yes I know, you shouldn't do two queries for the same thing, but too bad
        try {
            DataBases.getUser(txtUsername.getText(), UsernameLookupType.username); // Does this succesfully pull a user account?
        } catch(Exceptions.NoSuchUserFound e) {
            // Not a real user.
            if (App.debug)
                lblError.setText("No such user.");
            else
                lblError.setText("Incorrect username/password combination!");
            
            System.out.println("Failed to find user: " + txtUsername.getText());
            
            return;
        }
        
        if (Session.login(DataBases.getUser(txtUsername.getText(), UsernameLookupType.username), txtPassword.getText().toCharArray())) {
            txtPasswordR.setText("No.");
            txtPassword.setText("No.");
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            stage.close();
        } else {
            lblError.setText("Incorrect username/password combination!");
            txtPassword.setText("");
        }
        
    }

    /**
     * Called when the register button is clicked
     * @param event (The event details)
     */
    @FXML
    void registerUser(ActionEvent event) {
        apLogin.setVisible(false);
        apLogin.setDisable(true);
        apRegister.setVisible(true);
        apRegister.setDisable(false);        
    }


    /**
     * Called when the last register button is clicked (register the user)
     * @param event
     */
    @FXML
    void registerUserFinal(ActionEvent event) {
        try {
            if (DataBases.getUser(txtUsernameR.getText(), UsernameLookupType.username)!=null) {
                lblError1.setText("Sorry, that user already exists.");
                return;
            } 
        } catch(Exceptions.NoSuchUserFound e) {}
        
        if (txtPasswordR.getText().length()<4) {
            lblError1.setText("Password too short.");
            return;
        }      
        
        AccountType aT;
        
        JSONObject jo = new JSONObject();
        System.out.println("Registering a new user: " + txtUsernameR.getText());
        jo.put("username", txtUsernameR.getText());
        jo.put("firstName", txtFirstName.getText());
        jo.put("lastName", txtLastName.getText());
        jo.put("emailAddress", txtEmail.getText());
        
        JFXRadioButton selected = (JFXRadioButton) accountType.getSelectedToggle();
        if (selected.getText().equals(radRealtor.getText())) {
            aT = AccountType.STUDENT;
        } else if (selected.getText().equals(radOwner.getText())) {
            aT = AccountType.PROPERTYMANAGER;
        } else {
            aT = AccountType.STUDENT;
        }
        
        jo.put("accountType", aT.toNum());
        if (aT==AccountType.STUDENT)
            jo.put("studentID", txtStudentID.getText());
        
        jo.put("token", ""); // To pass 'valid' check
        
        User user = new User(jo);
        user.setPassword(txtPasswordR.getText().toCharArray());
        
        DataBases.addUser(user);
        System.out.println("User added! Saving: " + user.username);
        DataBases.save();
        
        Session.login(user, txtPasswordR.getText().toCharArray());
        txtPasswordR.setText("No.");
        txtPassword.setText("No.");
        // Close window
        Stage stage = (Stage) btnLogin.getScene().getWindow();
        stage.close();
    }

    
    @FXML
    void txtUsernameNext(ActionEvent event) {
        txtPassword.requestFocus(); // Move down
    }
    @FXML
    void txtPasswordNext(ActionEvent event) {
        loginUser(null);
    }
    
    // Register fields (enter pressed)
    @FXML
    void txtUsernameRNext(ActionEvent event) {
        txtPasswordR.requestFocus();
    }
    @FXML
    void txtPasswordRNext(ActionEvent event) {
        txtFirstName.requestFocus();
    }
    @FXML
    void txtFirstNameNext(ActionEvent event) {
        txtLastName.requestFocus();
    }

    @FXML
    void txtLastNameNext(ActionEvent event) {
        txtEmail.requestFocus();
    }
    
    @FXML
    void radCheck(ActionEvent event) {
        JFXRadioButton selected = (JFXRadioButton) accountType.getSelectedToggle();
        if (selected == null)
            return;
        
        if (selected.getText().equals(radStudent.getText())) {
            txtStudentID.setVisible(true);
            txtStudentID.setDisable(false);
        } else {
            txtStudentID.setVisible(false);
            txtStudentID.setDisable(true);
        }
    }
    @FXML
    void radCheckKey(KeyEvent event) {
        radCheck(new ActionEvent());
    }
    @FXML
    void txtEmailNext(ActionEvent event) {
        btnRegisterFinal.requestFocus();
    }
    @FXML
    void txtStudentIDNext(ActionEvent event) {
        registerUserFinal(null);
    }

    

}
