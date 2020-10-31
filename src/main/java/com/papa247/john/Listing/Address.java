/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Listing;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.SearchData;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Enumerators.TargetType;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Coordinate;
import com.papa247.john.Support.Review;
import com.papa247.john.Support.StringUtils;
import com.papa247.john.User.User;

public class Address {
    public int id = -1;                      // Used to reference this address when an object is not desirable
    public String name;                 // Name of the place.
    public String description = "";          // A description of the place
    public String streetAddress = "";   // ..
    public String city = "";            // ..
    public String state = "";           // ..
    public String postalCode = "";      // ..
    public String country = "";         // ..
    
    // TODO [$5f9dd55be65e40000707176b]: Management company address (payment address)
    // Referenced in the lease agreement template, we need to be able to specify a payment/management company address
    
    public Coordinate longitude = new Coordinate();    // The longitude coordinate of this address
    public Coordinate latitude = new Coordinate();     // The latitude coordinate of this address
    
    public Review[] reviewsOf = new Review[0];      // Reviews of this address
    public Listing[] listings = new Listing[0];      // Our listings for this address
    public User[] managers = new User[0];         // Who manages this address
    
    
   
    

    public Address(JSONObject jsonObject) {
        if (jsonObject.has("name")) // Do not capitalize stuff, I spent 1 hour just to realize this was being found :(
            name = jsonObject.getString("name");
        if (jsonObject.has("streetAddress"))
            streetAddress = jsonObject.getString("streetAddress");
        if (jsonObject.has("city"))
            city = jsonObject.getString("city");
        if (jsonObject.has("state"))
            state = jsonObject.getString("state");
        if (jsonObject.has("postalCode"))
            postalCode = jsonObject.getString("postalCode");
        if (jsonObject.has("country"))
            country= jsonObject.getString("country");
        if (jsonObject.has("longitude"))
            longitude = new Coordinate(jsonObject.getJSONObject("longitude"));
        if (jsonObject.has("Latitude"))
            latitude = new Coordinate(jsonObject.getJSONObject("Latitude")); 
        
        if (jsonObject.has("id"))
            id = jsonObject.getInt("id");
       
        listings = new Listing[0];
        if(jsonObject.has("listings"))
           jsonObject.getJSONArray("listings").forEach(e -> {
               Listing listing = new Listing((JSONObject) e, this);
               listing.parent = this;
               listings = ArrayUtils.add(listings, new Listing[listings.length+1], listing);
               DataBases.addListing(listing);
           });
       
       managers = new User[0];
       if (jsonObject.has("managers"))
           jsonObject.getJSONArray("managers").forEach(e -> {
              User user = DataBases.getUser((String) e, UsernameLookupType.username);
              managers = ArrayUtils.add(managers, new User[managers.length+1], user);
           });
    }

    
    public Address() {
    }


    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        
        jo.put("name", name);
        jo.put("streetAddress", streetAddress);
        jo.put("city", city);
        jo.put("state", state);
        jo.put("postalCode", postalCode);
        jo.put("country", country);
        jo.put("longitude", longitude.toJSON());
        jo.put("latitude", latitude.toJSON());
        jo.put("id", id);
        
        
        JSONArray ja = new JSONArray();
        for (Listing listing : listings)
            ja.put(listing.toJSON());
        jo.put("listings", ja);
        
        
        ja = new JSONArray();
        for (User user : managers)
            ja.put(user.username);
        jo.put("managers", ja);
        
        return jo;
    }
    
    /**
     * Calculates how far away an address is from another address (useful for something I'm sure)
     * @param address
     * @return distance (mi)
     */
    public double distanceFrom(Address address) {
        // TODO [#11]: Address Google Maps API (or some other maps API) to calculate distance
        // Details here: https://developers.google.com/maps/documentation/directions/start
        // Probably like this actually: https://maps.googleapis.com/maps/api/directions/json?origin=LAT,LONG&destination=LAT,LONG&key=YOUR_API_KEY
        
        
        return 0;
    }
    
    /**
     * Check if a user is a manager of this address or not
     * @param user
     * @return
     */
    public boolean hasManager(User user) {
        for (User usr : managers) {
            if (usr.equals(user))
                return true;
        }
        return false;
    }
    
    /**
     * This adds a listing to the list of listings
     * Ignores duplicates
     * @param listing   The listing... to add...
     */
    public boolean addListing(Listing listing) {
        if (listing.equals(new Listing()))
            return true; // Blank listing
        
        listings = ArrayUtils.add(listings, new Listing[listings.length+1], listing);
        DataBases.updateListings();
        return DataBases.saveAddresses();
    }
    
    /**
     * This removes a listing from the list of listings...
     * @param listing
     * @return
     */
    public boolean removeListing(Listing listing) {
        if (listings.length==0)
            return true;
        
        listings = ArrayUtils.remove(listings, new Listing[listings.length-1], listing);
        DataBases.updateListings();
        return DataBases.saveAddresses();
    }
    
    public void removeManager(User manager) {
        if (managers.length==0)
            return;
        managers = ArrayUtils.remove(managers, new User[managers.length-1], manager);
    }
    public void addManager(User manager) {
        managers = ArrayUtils.add(managers, new User[managers.length+1], manager);
    }
    
    public boolean removeReviewOf(Review review) {
        if (reviewsOf.length == 0)
            return true;
        reviewsOf = ArrayUtils.remove(reviewsOf, new Review[reviewsOf.length-1], review);
        return true;
           
    }
    
    public boolean addReviewOf(Review review) {
        reviewsOf = ArrayUtils.add(reviewsOf, new Review[reviewsOf.length+1], review);
        return true;
    }
    
    
    public Lease[] getLeases(boolean signed) {
        Lease[] leases = new Lease[0];
        for (Lease lease : leases) {
            if (lease.getSigned() && signed) {
                leases = ArrayUtils.add(leases, new Lease[leases.length+1], lease);
            } else if (!signed) {
                leases = ArrayUtils.add(leases, new Lease[leases.length+1], lease);
            }
        }
        return leases;
    }
    
    public double getRating() {
        if (reviewsOf.length==0)
            return 5;
        
        double rating = reviewsOf[0].rating; // A running average
        if (reviewsOf.length>1) {
            for (int i=1; i<reviewsOf.length; i++) {
                if (reviewsOf[i].targetType == TargetType.Address) 
                    if (reviewsOf[i].target.address.equals(this))
                        rating = (rating + reviewsOf[i].rating)/2; // Add rating to avg
            }
            
            return rating;
        } else {
            return reviewsOf[0].rating;
        }
    }
    
    public Listing[] getListings(SearchData searchData) {
        searchData.addresses = new int[1];
        searchData.addresses[0] = this.id;        
        return DataBases.getListings(searchData);
    }


    public boolean isEmpty() {
        if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(description))
            return true;
        return false;
    }
    
    @Override
    public String toString() {
        return this.name;
    }

    public boolean equals(Object obj) {
        Address comp = (Address) obj;
        if (comp!=null)
            if (comp.name != null)
                if (comp.name.equals(this.name) && comp.id == this.id)
                    return true;
        
        return false;
    }
    

    public void delete() {
        DataBases.removeAddress(this);        
    }
}