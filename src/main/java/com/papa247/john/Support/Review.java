/**
* PAPA-247: Project JOHN
*
*   The Review class object.
*
* File created by cnewb on Oct 16, 2020
*/

package com.papa247.john.Support;

import java.io.IOException;
import org.json.JSONObject;
import com.papa247.john.App;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Enumerators.TargetType;
import com.papa247.john.Listing.Address;
import com.papa247.john.UIComponents.ReviewController;
import com.papa247.john.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
//import javafx.stage.StageStyle;

public class Review {
    /*
     * Since we can't have two types for one property, set the property to a class that has two more properties for each type :)
     */
    public class Target {
        public User user;
        public Address address;
        
        public Target(User user) {
            this.user = user;
        }
        public Target(Address address) {
            this.address = address;
        }
        public Target() {}
        
        @Override
        public boolean equals(Object target) {
            Target tar = (Target) target;
            if (tar.user!=null) {
                if (this.user!=null)
                    return (tar.user.equals(this.user));
                else
                    return false;
            } else {
                if (this.address!=null)
                    return (tar.address.equals(this.address));
                else
                    return false;
            }
        }
    }
    
    public User author;
    public Target target;
    public TargetType targetType;
    public double rating = 0;
    public String title = "";
    public String contents = "";
    
    
    /*
     * "Create" a review (object) from a JSONObject
     */
    public Review(JSONObject jo) {
        if (!jo.has("author") || !jo.has("rating") || !jo.has("title") || !jo.has("targetType") || !jo.has("target"))
            throw new Exceptions.InvalidJSON("Invalid JSON data passed.");
        
        author = DataBases.getUser(jo.getString("author"), DataBases.UsernameLookupType.username);
        rating = jo.getDouble("rating");
        title = jo.getString("title");
        contents = jo.getString("contents");
        
        targetType = TargetType.fromNum(jo.getInt("targetType"));
        
        if (targetType == TargetType.User)
            try {
                target = new Target(DataBases.getUser(jo.getString("target"), DataBases.UsernameLookupType.username));
            } catch(Exceptions.NoSuchUserFound e) {
                
            }
        else
            try {
                target = new Target(DataBases.getAddress(jo.getInt("target")));
            } catch(Exceptions.NoSuchAddressFound e) {
                
            }
    }
    
    /*
     * Create a review for a user
     */
    public Review(User author, String title, String contents, User target, double rating) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.target = new Target(target);
        this.targetType = TargetType.User;
        this.rating = rating;
    }
    /*
     * Create a review for an address
     */
    public Review(User author, String title, String contents, Address target, double rating) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.target = new Target(target);
        this.targetType = TargetType.Address;
        this.rating = rating;
    }

    public Review() { // Generate blank target
    }
    
    /**
     * Create a new review of an address. Opens the review 'prompt'
     * @param author
     * @param address
     */
    public Review(User author, Address address) {
        targetType = TargetType.Address;
        if (author.accountType != AccountType.PROPERTYMANAGER && author.accountType != AccountType.REALTOR)
            leaveReview(author, new Target(address));
    }
    /**
     * Create a new review of a user (owner). Opens the review 'prompt'
     * @param author
     * @param user
     */
    public Review(User author, User user) {
        targetType = TargetType.User;
        if (user.accountType != AccountType.STUDENT)
            leaveReview(author, new Target(user));
    }
    
    

    public void delete() {
        // TODO [$5f97a42d6e0b1b0008c0cad4]: Notify listing about this
        // A potential idea is to RE-RENDER *ALL* reviews for everything. We should maybe have a method on addresses to remove reviews... wait
        // BUT YES. TO-DO, add a .removeReview() method to addresses that removes a review (this object) from its list of reviews (that's it the target of)
        author.removeReview(this);
        //target.address.removeReview(this);
        //target.user.removeReview(this);
    }
    
    
    private Review leaveReview(User author, Target target) {
        System.out.println("[Review] Loading review editor");
        
        this.author = author;
        this.target = target;
        String name;
        if (targetType == TargetType.User)
            name = target.user.username;
        else
            name = target.address.name;
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UIComponents/Review.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ReviewController controller = fxmlLoader.getController();
            Scene reviewScene = new Scene(root, 640,320);
            Stage reviewScreen = new Stage();
            reviewScreen.setResizable(false);
            reviewScreen.setTitle("Review of " + name);
//            reviewScreen.initStyle(StageStyle.UTILITY);
            reviewScreen.initModality(Modality.WINDOW_MODAL);
            reviewScreen.setScene(reviewScene);
            reviewScreen.setOnShown(e -> {
                    controller.setup(this);
                    controller.setEditor(true);
                });
            reviewScreen.showAndWait();
        } catch(IOException e) {
            
        }
        
        author.addReview(this);
        if (this.targetType == TargetType.User)
            this.target.user.addReviewOf(this);
        else
            this.target.address.addReviewOf(this);
        
        return this;
    }
    
    
    public JSONObject toJSON() {
        if (author == null)
            return new JSONObject();
        
        JSONObject jo = new JSONObject();
        jo.put("author", author.username);
        if (targetType == TargetType.User)
            jo.put("target", target.user.username);
        else
            jo.put("target", target.address.id);
        
        jo.put("targetType", targetType.toNum());
        jo.put("rating", rating);
        jo.put("title", title);
        jo.put("contents", contents);
        
        return jo;
    }
    
    
    @Override
    public boolean equals(Object review) {
        Review rvw = (Review) review;
        
        if (rvw.author==null || rvw.title == null || rvw.contents == null || rvw.target == null)
            return false;
        
        if (rvw.author.equals(this.author)
                && rvw.title.equals(this.title)
                && rvw.contents.equals(this.contents)
                && rvw.target.equals(this.target)) {
            return true;
        }
        return false;
    }
}
