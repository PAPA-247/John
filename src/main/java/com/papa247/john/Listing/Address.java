/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Listing;

import org.json.JSONObject;
import com.papa247.john.User.User;

public class Address {

    public Listing[] listings;
    public String appartmentNumber;
    public User owner;
    public Object streetAddress;
    public Object city;
    public int id;

    public Address(JSONObject jsonObject) {
        // TODO Auto-generated constructor stub
    }

    /**
     * This adds a listing to the list of listings
     * Ignores duplicates
     * @param listing   The listing... to add...
     */
    public void addListing(Listing listing) {
        // TODO Auto-generated method stub
        
    }

    public JSONObject toJSON() {
        // TODO Auto-generated method stub
        return null;
    }

}
