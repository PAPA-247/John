/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Listing;

import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Review;
import com.papa247.john.User.User;

public class Listing {

    public Address parent;
    public int id;
    public String apartmentNumber;
    public double monthlyPrice;

    public Lease lease;
    public ListingType listingType;
    
    public String[] photos;
    public Amminities[] amminities;
    public Room[] rooms;
    public Room[] bedrooms;
    
    
    public Listing(JSONObject jo, Address parent) {
        if (!jo.has("id") || !jo.has("apartmentNumber") || !jo.has("monthlyPrice") || !jo.has("lease") || !jo.has("listingType") || !jo.has("bedrooms"))
            return; // No valid (or empty either way).
        
        id = jo.getInt("id");
        apartmentNumber = jo.getString("apartmentNumber");
        monthlyPrice = jo.getDouble("montlyPrice");
    
        lease = new Lease(jo.getJSONObject("lease"), this);
        listingType = ListingType.fromString(jo.getString("listingType"));
        
        photos = new String[0];
        if (jo.has("photos"))
            jo.getJSONArray("photos").forEach(e -> {
                String photoURL = (String) e;
                photos = ArrayUtils.add(photos, new String[photos.length+1], photoURL);
            });
        
        amminities = new Amminities[0];
        if (jo.has("amminities"))
            jo.getJSONArray("amminities").forEach(e -> {
                Amminities a = Amminities.fromString((String) e);
                amminities = ArrayUtils.add(amminities, new Amminities[amminities.length+1], a);
            });
        
        rooms = new Room[0];
        if (jo.has("rooms"))
            jo.getJSONArray("rooms").forEach(e -> {
                Room r = new Room((JSONObject) e);
                rooms = ArrayUtils.add(rooms, new Room[rooms.length+1], r);
            });
        
        bedrooms = new Room[0];
        if (jo.has("bedrooms"))
            jo.getJSONArray("bedrooms").forEach(e -> {
                Room r = new Room((JSONObject) e);
                bedrooms = ArrayUtils.add(bedrooms, new Room[bedrooms.length+1], r);
            });
    }
    
    public Listing() {
        
    }
    
    
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        
        jo.put("id", id);
        jo.put("apartmentNumber", apartmentNumber);
        jo.put("monthlyPrice", monthlyPrice);
        jo.put("lease", lease.toJSON());
        jo.put("listingType", listingType.toString());
        
        JSONArray ja = new JSONArray();
        for (String photoURL: photos)
            ja.put(photoURL);
        jo.put("photos", ja);
        
        ja = new JSONArray();
        for (Amminities amminities : amminities)
            ja.put(amminities.toString());
        jo.put("amminities", ja);

        ja = new JSONArray();
        for (Room room : rooms)
            ja.put(room.toJSON());
        jo.put("rooms", ja);
        ja = new JSONArray();
        for (Room room : bedrooms)
            ja.put(room.toJSON());
        jo.put("bedrooms", ja);

        return jo;
    }
    
    
    
    
    public void addRoom(Room room) {
        rooms = ArrayUtils.add(rooms, new Room[rooms.length+1], room);
    }
    public void addBedroom(Room room) {
        bedrooms = ArrayUtils.add(bedrooms, new Room[bedrooms.length+1], room);
    }
    
    public void removeRoom(Room room) {
        if (rooms.length==0)
            return;
        
        rooms = ArrayUtils.remove(rooms, new Room[rooms.length-1], room);
    }
    
    public void removeBedroom(Room room) {
        if (bedrooms.length==0)
            return;
        
        bedrooms = ArrayUtils.remove(bedrooms, new Room[bedrooms.length-1], room);
    }
    
    
    public double getTotalPrice() {
        return lease.rentLength*monthlyPrice;
    }
    /**
     * For how long can I rent this?
     * @return
     */
    public double getRentLength() {
        return lease.rentLength;
    }
    /**
     * Returns the total square footage of this listing
     * @return
     */
    public double getTotalSize() {
        double size = 0;
        for (Room room : rooms)
            size += room.size;
        return size;
    }
    
    public boolean Delete() {
        return false;
    }
}
