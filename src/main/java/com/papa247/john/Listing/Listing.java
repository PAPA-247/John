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
import com.papa247.john.Support.StringUtils;
import com.papa247.john.User.User;

public class Listing {
    public Address parent;
    public int id;
    public String apartmentNumber = "";
    public double monthlyPrice = 0.0;

    public String title = "";
    public String description = "";
    
    public Lease lease;
    public ListingType listingType = ListingType.APARTMENT;
    
    public String[] photos = new String[0];
    public Amminities[] amminities = new Amminities[0];
    public Room[] rooms = new Room[0];
    public Room[] bedrooms = new Room[0];
    
    
    public Listing(JSONObject jo, Address parent) {        
        if (!jo.has("id")
                || !jo.has("apartmentNumber")
                || !jo.has("monthlyPrice")
                || !jo.has("lease")
                || !jo.has("listingType")
                || !jo.has("bedrooms")
                || !jo.has("title")
                || !jo.has("description"))
            return; // No valid (or empty either way).
        
        this.parent = parent;
        
        id = jo.getInt("id");
        apartmentNumber = jo.getString("apartmentNumber");
        monthlyPrice = jo.getDouble("monthlyPrice");
    
        lease = new Lease(jo.getJSONObject("lease"), this);
        listingType = ListingType.fromString(jo.getString("listingType"));
        
        description = jo.getString("description");
        title = jo.getString("title");
        
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
        jo.put("title", title);
        jo.put("description", description);
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
            if (room!=null)
                ja.put(room.toJSON());
            else
                ja.put(new Room().toJSON());
        jo.put("rooms", ja);
        
        
        ja = new JSONArray();
        for (Room room : bedrooms)
            if (room!=null)
                ja.put(room.toJSON());
            else
                ja.put(new Room().toJSON());
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
    
    public void delete() {
        DataBases.removeListing(this);
        DataBases.save();
    }

    public boolean isEmpty() {
        if (StringUtils.isNullOrEmpty(this.title))
            return true; // Good enough for me
        return false;
    }
    
    
    
    // Setters
    private boolean isNullOrEmpty(String a) {
        if (a==null)
            return true;
        if (a.replace(" ", "").equals(""))
            return true;
        return false;
    }

    public void setApartmentNumber(String aN) {
        if (isNullOrEmpty(aN))
            return;

        apartmentNumber = aN;
    }

    public void parent (Address a) {
        if (!a.isEmpty())
            return;
        parent = a;
    }
    public void id (int a) {
        if (id<0)
            return;
        
        id = a;            
    }
    public void setMonthlyPrice (double a) {
        if (a>0)
            monthlyPrice = a;
    }
    public void setTitle (String a) {
        if (isNullOrEmpty(a))
            return;
        title = a;
    }
    public void setDescription (String a) {
        if (isNullOrEmpty(a))
            return;
        description = a;
    }

    @Override
    public String toString() {
        return this.title;
    }
    
    @Override
    public boolean equals(Object obj) {
        Listing comp = (Listing) obj;
        if (comp.title.equals(this.title) && comp.id == this.id)
            return true;
        
        return false;
    }
    
    // GUI
    public void editLease() {
        DataBases.editLease(this);
    }

    public void viewLease() {
        DataBases.viewLease(this);
        
    }

    public String getStreetAddress() {
        return parent.streetAddress + " " + apartmentNumber + ", " + parent.city + ", " + parent.state + " (" + parent.postalCode + ")";
    }
}
