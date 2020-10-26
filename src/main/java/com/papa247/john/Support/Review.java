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
    }
    
    public User author;
    public Target target;
    public TargetType targetType;
    public double rating;
    public String title;
    public String contents;
    
    
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
}
