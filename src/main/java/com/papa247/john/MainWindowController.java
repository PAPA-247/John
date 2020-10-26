package com.papa247.john;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;
import com.jfoenix.skins.JFXPopupSkin;
import com.papa247.john.Support.Session;
import com.papa247.john.User.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class MainWindowController implements Initializable {

    @FXML
    private AnchorPane main;

    @FXML
    private AnchorPane ContentHost;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private AnchorPane apTopBar;

    @FXML
    private JFXHamburger hamburger;
    
    @FXML
    private Label lblUser;
    
    @FXML
    private Circle imgAvatar;
    
    private JFXPopup accountPopup;

    private Stage getNewWindow(String FXML, int width, int height) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXML + ".fxml"));
            Parent root = (Parent)loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, width, height));
            return stage;
        } catch (IOException e) {
            e.printStackTrace();
            return new Stage();
        }
    }
    
    private void openNewWindow(String FXML, String title) {
        Stage stage = getNewWindow(FXML, 450, 450);
        stage.setTitle(title);
        stage.show();
    }
    
    private void setupAccountMenu(boolean loggedIn) {
        String sufix = (loggedIn)? "In" : "Out";
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UIComponents/AccountMenuLogged" + sufix + ".fxml"));
        loader.setController(new InputController());
        try {
            accountPopup = new JFXPopup(loader.load());
        } catch (IOException e1) {
            System.out.println("Failed to set account menu. Did you break something?");
        }
        imgAvatar.addEventFilter(MouseEvent.MOUSE_CLICKED, (e) -> {// Avatar clicked, open menu
                accountPopup.show(imgAvatar.getParent(),
                        PopupVPosition.TOP,
                        PopupHPosition.RIGHT,
                        -25,
                        25);
        });
        accountPopup.setSkin(new JFXPopupSkin(accountPopup)); // ..???
        accountPopup.setAutoFix(true);
        accountPopup.setAutoHide(true);
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource("SideMenu.fxml"));
            
            drawer.setSidePane(box);
            
            // This gets nasty...
            for (Node node : box.getChildren()) { // For every object in our AnchorPane (which is just a button and a vbox)
                if (node.getId() != null) { // Check if the accessible text is empty...
                    if (node.getId().equals("vbox")) { // If it is not and equals 'vbox' we know this is our vbox object
                        // VBox
                        for (Node node2 : ((Pane) node).getChildren()) { // Round two, check the vbox's children
                            if (node2.getId()!= null) { // Accessible text there...
                                switch(node2.getId()) {
                                    case "btnDebug": // This 'node2' is the debug button 
                                        node2.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                                            openNewWindow("UIComponents/DebugWindow","Debug");
                                        });
                                        break;
                                }
                            }
                        }
                    } else if (node.getId().equals("btnExit")) {
                        node.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
                            System.out.println("Exiting...");
                            Session.logout();
                            DataBases.save();
                            System.exit(0);
                        });
                    }
                }
            }
                        
            hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                System.out.println("[MainWindow] Drawer opened");
                if (drawer.isOpened() || drawer.isOpening())
                    drawer.close();
                else
                    drawer.open();
                
            });
            
            if (App.debug) {
                System.out.println("[MainWindow] Debug mode active! Filling in blank data");
                // Set Avatar (testing)
                Image im = new Image(getClass().getResource("Images/Billy.jpg").toString(), false);
                imgAvatar.setFill(new ImagePattern(im));
            } else {
                lblUser.setText("Welcome!");
                Image stockImage = new Image(getClass().getResource("Images/BlankAvatar.jpg").toString(), false);
                imgAvatar.setFill(new ImagePattern(stockImage));
            }
            
            
            // Account Menu
            setupAccountMenu(false);
            
            DataBases.load();
            
        } catch (IOException e1) {
            System.out.println("Failed to load drawer data! >> Nothing is going to work!! <<");
            e1.printStackTrace();
        }
        
    }
    
    public final class InputController {
        @FXML
        private JFXListView<?> AccountMenu;

        // close application
        @FXML
        private void submit() {
            int index = AccountMenu.getSelectionModel().getSelectedIndex();
            if (Session.user!=null) {
                if (index == 0) { // My Account
                    System.out.println("[Account Menu] Opening account settings");
                    // openNewWindow();
                    
                } else if (index == 1) { // My Favorites
                    System.out.println("[Account Menu] Switching to favorites view");
                    // showFavorites(); ??
                    
                } else if (index == 2) {
                    System.out.println("[Account Menu] Logging out");
                    Session.logout();
                    // Clear user data
                    lblUser.setText("Welcome!");
                    Image stockImage = new Image(getClass().getResource("Images/BlankAvatar.jpg").toString(), false);
                    imgAvatar.setFill(new ImagePattern(stockImage));
                    
                    // Change the menu
                    accountPopup.hide();
                    setupAccountMenu(false);
                }
            } else {
                if (index == 0) {
                    System.out.println("[Account Menu] Logging in");
                    // Login
                    
                    Stage stage = getNewWindow("UIComponents/LoginWindow", 450, 515);
                    stage.setTitle("Login");
                    stage.setResizable(false);
                    stage.showAndWait();
                    
                    if (Session.user==null) {
                        System.out.println("[Account Menu] User not assigned in session. Did login get canceled?");
                        return;
                    }
                    // Refresh profile photo
                    if (Session.user.profilePhotoURL == null || Session.user.profilePhotoURL.equals("")) {
                        Image stockImage = new Image(getClass().getResource("Images/BlankAvatar.jpg").toString(), false);
                        imgAvatar.setFill(new ImagePattern(stockImage));
                    } else {
                        Image im = new Image(Session.user.profilePhotoURL, false);
                        imgAvatar.setFill(new ImagePattern(im));
                    }
                    // Account label
                    lblUser.setText("Welcome, " + Session.user.firstName + "!");
                    
                    // Change the menu
                    setupAccountMenu(true);
                }
            }
        }
    }
}