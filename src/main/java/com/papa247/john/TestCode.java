/**
* PAPA-247: Project JOHN
*
*   Store test methods here
*
*/

package com.papa247.john;

import java.io.IOException;
import java.util.Scanner;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
//import com.papa247.john.*;
import com.papa247.john.Support.*;
import com.papa247.john.UIComponents.ReviewController;
import com.papa247.john.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestCode {
    
    private static Scanner in = new Scanner(System.in);
    
//  System.out.print("...wait...");
//  try {
//      Thread.sleep(2000);
//  } catch (InterruptedException e) {} // okay
//  
    
    public static void userDB() {
        System.out.println("Loading users data base...");
        DataBases.loadUsers();
        
        System.out.println("Listing users...");
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
        
        System.out.println("Creating a user (usrname: billy)");
        User user = new User();
        user.username = "billy";
        user.firstName = "Billy";
        user.middleName = "";
        user.lastName = "Thomas";
        user.phoneNumber = "+15386661888";
        user.emailAddress = "billyt@team.papa";
        user.accountType = AccountType.STUDENT;
        user.studentID = "W0080082";
        
        user.addReview(new Review(user, "Billy is amazing", "I absolutley love this guy! He's amazing!", user, 5));
        
        DataBases.addUser(user);
        
        
        System.out.println("User created, what should the password be?");
        
        user.setPassword(in.nextLine().toCharArray());
        
        
        System.out.println("Password set (users should be saved...)");
        
        System.out.println("Listing users...");
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
        
        User checkUser = DataBases.getUser("billy", UsernameLookupType.username);
        
        System.out.println("Checking password... Enter the password for Billy: ");
        System.out.println("Password correct: " + checkUser.isPassword(in.nextLine().toCharArray()));
        
        
        
        System.out.println("Removing billy...");
        DataBases.removeUser(DataBases.getUser(user.emailAddress, UsernameLookupType.email_address));
        
        System.out.println("Listing users...");
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
        
        System.out.println("User testing completed.");
    }
    
    public static void userList() {
        System.out.println("Listing users...");
        DataBases.loadUsers();
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
    }
    
    public static void reviews() {
        //System.out.println("Be sure to login as billy first, otherwise you cannot edit the review.");
        User ogSession = Session.user;
        
        User user;
        try {
            user = DataBases.getUser("billy", UsernameLookupType.username);
        } catch (Exceptions.NoSuchUserFound e) {
            System.out.println("User billy not registered! Cannot test reviews. Make sure you register an account under \"billy\"!");
            return;
        }
        
        Session.user = user; // logged in now :^)
        System.out.println("Reviews of billy: ");
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
        
        System.out.println("Creating review object. (We're pointing it to Billy.");
        Review ourReview = new Review(user, user);
        
        System.out.println("Our review (title): " + ourReview.title);
        System.out.println("Our review (contents): " + ourReview.contents);
        
        DataBases.save();
        
        System.out.println("Opening review viewer");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UIComponents/Review.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ReviewController controller = fxmlLoader.getController();
            Scene reviewScene = new Scene(root, 640,320);
            Stage reviewScreen = new Stage();
            reviewScreen.initStyle(StageStyle.UTILITY);
//            reviewScreen.initModality(Modality.WINDOW_MODAL);
            reviewScreen.setScene(reviewScene);
            reviewScreen.setOnShown(e -> controller.setup(ourReview));
            reviewScreen.showAndWait();
        } catch (IOException e) {
            System.out.println("Or not...\n" + e.getMessage());
        }
        
        System.out.println("Reviews of billy: ");
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
        
//        if (ourReview!=null) {
//            System.out.println("Deleting the review...");
//            ourReview.delete();
//        }
        
        Session.user = ogSession; // back to normal
    }
    
    public static void deleteReviews() {
        User user = Session.user;
        
        if (user==null) {
            try {
                user = DataBases.getUser("billy", UsernameLookupType.username);
            } catch (Exceptions.NoSuchUserFound e) {
                System.out.println("No account found, please login first.");
                return;
            }
            System.out.println("Not logged in, using billy.");
        }
        
        System.out.println("Revmoing reviews of " + user.username);
        
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
            review.delete();
        }
        System.out.println("Revmoing reviews by " + user.username);
        for (Review review : user.getReviews()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
            review.delete();
        }
    }
    
    public static void listReviews(User user) {
        if (user == null) {
            user = Session.user;
            
            if (user==null) {
                try {
                    user = DataBases.getUser("billy", UsernameLookupType.username);
                } catch (Exceptions.NoSuchUserFound e) {
                    System.out.println("No account found, please login first.");
                    return;
                }
                System.out.println("Not logged in, using billy.");
            }
        }
        
        System.out.println("==============\n"
                + "Reviews by " + user.username);
        for (Review review : user.getReviews()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
        System.out.println("===============\n"
                + "Reviews of " + user.username);
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
    }

    public static void selection() {
        System.out.println("Testing address selection.");
        Address a = Select.getAddress();
        if (a!=null)
            System.out.println("Selected: " + a.name);
        
        System.out.println("Testing listing selection.");
        Listing b = Select.getListing();
        if (b!=null)
            System.out.println("Selected: " + b.title);
        
        System.out.println("Testing user selection.");
        User c = Select.getUser();
        if (c!=null)
            System.out.println("Selected: " + c.username);
        
        
        System.out.println("Testing address selection.");
        Address[] aa = Select.getAddresses();
        for (Address a1 : aa)
            if (a1!=null)
                System.out.println("Selected: " + a1.name);
        
        System.out.println("Testing listing selection.");
        Listing[] bb = Select.getListings();
        for (Listing b1 : bb)
            if (b1!=null)
                System.out.println("Selected: " + b1.title);
        
        System.out.println("Testing user selection.");
        User[] cc = Select.getUsers();
        for (User c1 : cc)
            if (c1!=null)
                System.out.println("Selected: " + c1.username);
    }
}
