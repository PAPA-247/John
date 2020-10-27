/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Listing;
import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.Support.Coordinate;
import com.papa247.john.Support.Review;
import com.papa247.john.User.User;

public class Address {
    JSONParser parser = new JSONParser();
    public String name = "Address";
    public String streetAddress;
    public String city;
    public String state;
    public String postalCode;
    public String country;
    public Coordinate longitude;
    public Coordinate latitude;
    public Review[] reviews;
    public Listing[] listings;
    public User[] managers;
    public int id;
    public Coordinate cords;
   
    

    public Address(JSONObject jsonObject) {
        if (jsonObject.has("Name"))
            name = (String)jsonObject.get("Name");
        if (jsonObject.has("streetAddress"))
            streetAddress = (String)jsonObject.get("streetAddress");
        if (jsonObject.has("city"))
            city = (String)jsonObject.get("city");
        if (jsonObject.has("state"))
            state = (String)jsonObject.get("state");
        if (jsonObject.has("postalCode"))
            postalCode = (String)jsonObject.get("postalCode");
        if (jsonObject.has("country"))
            country= (String)jsonObject.get("country");
        if (jsonObject.has("longitude"))
            longitude = (Coordinate)jsonObject.get("longitude"); 
        if (jsonObject.has("Latitude"))
            Latitude = (Coordinate)jsonObject.get("Latitude"); 
       
        if(jsonObject.has("listings"))
           listings = jsonObject.getJSONArray("listings");
       
       if (jsonObject.has("managers"))
           managers = jsonObject.getJSONArray("managers");
    }

    /**
     * This adds a listing to the list of listings
     * Ignores duplicates
     * @param listing   The listing... to add...
     */
    public void addListing(Listing listing) {
        for(int i  = 0; i<listings.length;i++) {
            if(listings[i]==null) {
                listings[i] = listing;
            }
        }
    }
    public boolean removeListing(Listing listing) {
        for(int i  = 0; i<listings.length;i++) {
            if(listings[i]!=null&&listings[i].equals(listing)) {
                listings[i] = null;
                return true;
            }else {
                return false;
            }
        }
    }
    public boolean removeManager(User manager) {
        for(int i = 0;i<managers.length;i++) {
            if(managers[i]!=null&&managers[i].equals(manager)) {
                managers[i] = null;
                return true;
            }else {
                return false;
            }
        }
    }
    public boolean removeReview(Review review) {
        for(int i = 0; i<reviews.length;i++) {
            if(reviews[i]!=null&&revies[i].equals(review)) {
                reviews[i] = null;
                return true;
            }else {
                return false;
            }
        }
           
    }
    public boolean addReview(Review review) {
        for(int i = 0;i<reviews.length;i++) {
            if(reviews[i]==null) {
                reviews[i] = review;
                return true;
            }else {
                return false;
            }
        }
    }
    
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        
        jo.put("name", name);
        jo.put("streetAddress", streetAddress);
        jo.put("city", city);
        jo.put("state", state);
        jo.put("postalCode", postalCode);
        jo.put("country", country);
        jo.put("longitude", longitude);
        jo.put("latitude", latitude);
        
        
        JSONArray ja = new JSONArray();
        for (Listing listing : listings)
            ja.put(listing.id);
        jo.put("listings", ja);
        
        ja = new JSONArray();
        for (Review review : reviews)
            ja.put(reviews.target);
        jo.put("reviews", ja);
        
        ja = new JSONArray();
        for (User users : managers)
            ja.put(managers.userName);
        jo.put("managers", ja);
        
        return jo;    }

    public void addReviewOf(Review review) {
        for(int i  = 0; i<listings.length;i++) {
            if(reviews[i]==null) {
                reviews[i] = review;
            }
        }
    }

}
public class Room {
    public RoomType type;
    public double size;
    public FloorType floor;
    public Extensions[]  extensions= new Extensions[];
    public Appliances[]  appliances= new Appliances[];
    public Fixtures[]  fixtures= new Fixtures[];
    public Furniture[]  furniture= new Furniture[];
    public int windows;
    public String name;
    public Room(JSONObject room) {
        
    }
    public void reset() {
        
    }
    public JSONObject toJSON() {
        
    }
}
