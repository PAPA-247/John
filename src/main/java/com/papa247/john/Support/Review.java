/**
* PAPA-247: Project JOHN
*
*   The Review class object.
*
* File created by cnewb on Oct 16, 2020
*/

package com.papa247.john.Support;

import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.TargetType;
import com.papa247.john.Listing.Address;
import com.papa247.john.User.User;

public class Review {
    /*
     * Since we can't have two types for one property, set the property to a class that has two more properties for each type :)
     */
    private class Target {
        public User user;
        public Address address;
        
        public Target(User user) {
            this.user = user;
        }
        public Target(Address address) {
            this.address = address;
        }
        public Target() {}
    }
    
    /*
     * The creator of the review
     */
    public User author;
    
    /*
     * Who/What the review is on
     */
    public Target target;
    public TargetType targetType;
    
    /*
     * A simple rating/5
     */
    public double rating;
    
    /*
     * The title of the review
     */
    public String title;
    
    /*
     * The review itself (..?) (Like their description of the place.. etc)
     */
    public String contents;
    
    
    /*
     * "Create" a review (object) from a JSONObject
     */
    public Review(JSONObject reviewObj) {
        author = DataBases.getUser(reviewObj.getString("author"), DataBases.UsernameLookupType.username);
        rating = reviewObj.getDouble("rating");
        title = reviewObj.getString("title");
        contents = reviewObj.getString("contents");
        
        Target t = new Target();
        JSONObject jo = reviewObj.getJSONObject("target");
        t.user = DataBases.getUser(jo.getString("user"), DataBases.UsernameLookupType.username);
        t.address = DataBases.getAddress(jo.getString("street_address"), jo.getString("city"));
        targetType = (t.user==null)? TargetType.User : TargetType.Address;
    }
    
    /*
     * Create a review for a user
     */
    public Review(User author, String title, String contents, User target, double rating) {
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.target = new Target(target);
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
        this.rating = rating;
    }
}
