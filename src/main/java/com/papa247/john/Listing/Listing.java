/**
* PAPA-247: Project JOHN
*
*
*/

package com.papa247.john.Listing;

import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.Support.Review;
import com.papa247.john.User.User;

public class Listing {

    public Address address;
    public String id;
    public String apartmentNumber;
    public double monthlyPrice;
    public double rentLength;
    public User owner;
    public Lease leaseAgreement;
    public Room[] rooms;
    public Room[] bedrooms;
    public Amminities[] amminities;
    public ListingType listingType;
    public String userName;
    public Listing(JSONObject listing) {
        id = (String)listing.get("id");
        apartmentNumber = (String)listing.get("apartmentNumber");
        monthlyPrice = (double)listing.get("montlyPrice");
        rentLength = (double)listing.get("rentLength");
    
        userName = listing.get("owner");
        owner = DataBases.getUser(userName,username);
        
                
    }
    public void addRoom(Room room) {
        for(int i = 0;i<rooms.length;i++) {
            if(rooms[i]==null) {
                rooms[i] = room;
            }
        }
    }
    public void removeRoom(Room room) {
        for(int i = 0;i<rooms.length;i++) {
            if(rooms[i]!=null&&rooms[i].equals(room)) {
                rooms[i]=null;            
            }
        }
    }
    public double getTotalPrice() {
        double totalPrice = 0;
        totalPrice = rentLength*=montlyPrice;
        return totalPrice;
    }
    public double getRentLength() {
        return rentLength;
    }
    
    public double getTotalSize() {
        double size = 0;
        for (Room room : rooms)
            size += room.size;
        return size;
    }
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        
        jo.put("id", id);
        jo.put("apartmentNumber", apartmentNumber);
        jo.put("monthlyPrice", monthlyPrice);
        jo.put("rentLength", rentLength);
        jo.put("owner", userName);
        JSONArray ja = new JSONArray();
        for (Amminities amminities : amminities)
            ja.put(amminities);
        jo.put("amminities", ja);   
        ja = new JSONArray();
        for (ListingType listingType : listingType)
            ja.put(listingType);
        jo.put("listingType", ja);
        return jo;    }
    public boolean Delete() {
        return false;
    }
}
