/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Listing;

import org.json.JSONObject;
import com.papa247.john.Support.Coordinate;
import com.papa247.john.Support.Review;
import com.papa247.john.User.User;

public class Address {

    public Listing[] listings;
    public String appartmentNumber;
    public User owner;
    public String streetAddress;
    public String city;
    public int id;
    public Coordinate cords;
    public String name = "Address";

    public Address(JSONObject jsonObject) {
        // TODO Auto-generated constructor stub
    }

    /**
     * This adds a listing to the list of listings
     * Ignores duplicates
     * @param listing   The listing... to add...
     */
    public void addListing(Listing listing) {
    }

    public JSONObject toJSON() {
        return null;
    }

    public void addReviewOf(Review review) {
    }

}
