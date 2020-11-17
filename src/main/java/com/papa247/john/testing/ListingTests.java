/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.testing;

import com.papa247.john.DataBases;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Lease;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.Support.ArrayUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListingTests {
    public Address parent = new Address();
    public Listing listing = new Listing();
    
    public int id = DataBases.assignNewListingID();
    public String apartmentNumber = "";
    public double monthlyPrice = 100;
    public String title = "Testing";
    public String description = "Description";
    public Lease lease;
    
    public String leaseContents = "Lease agreement";
    public String leaseTitle = "Lease title";
    public int leaseRentLength = 12;
    
    public ListingType listingType = ListingType.APARTMENT;
    
    public String[] photos = new String[0];
    public Amminities[] amminities = new Amminities[0];
    public Room[] rooms = new Room[0];
    public Room[] bedrooms = new Room[0];
    
    @BeforeEach
    void setup() {
        // Initialize the (listing) object for testing
        
        JSONObject jo = new JSONObject();
        
        parent.city = "Testvill";
        parent.streetAddress = "123 Test";
        parent.state = "Panic";
        parent.postalCode = "04523";
        
        lease = new Lease(listing);
        lease.contents = leaseContents;
        lease.title = leaseTitle;
        lease.rentLength = leaseRentLength;
        listing.lease = lease;
        
        jo.put("id", id);
        jo.put("apartmentNumber", apartmentNumber);
        jo.put("monthlyPrice", monthlyPrice);
        jo.put("lease", lease.toJSON());
        jo.put("listingType", listingType.toString());
        jo.put("title", title);
        jo.put("description", description);
        
        JSONArray ja = new JSONArray();
        for (Room bedroom : bedrooms)
            ja.put(bedroom.toJSON());
        jo.put("bedrooms", ja);
        
        ja = new JSONArray();
        for (Room room : rooms)
            ja.put(room.toJSON());
        jo.put("rooms", ja);
        
        ja = new JSONArray();
        for (Amminities amminitie : amminities)
            ja.put(amminitie.toString());
        jo.put("amminities", ja);
        
        
        listing = new Listing(jo, parent);
        
        parent.name = "Testing Obj";
        
        
        
        
        
        DataBases.addListing(listing);
        DataBases.saveListings();
    }
    
    @AfterEach
    void tearDown() {
    	DataBases.removeListing(listing);
    	DataBases.saveListings();
    }

    @Test
    void testJson() {
        JSONObject jo = listing.toJSON();
        if (new Listing(jo, parent).equals(listing)) {
            System.out.println("[JSON] Testing passed.");
            assertTrue(true);
        } else {
            System.out.println("[***JSON] Testing failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testAddRoom() {
        Room room = new Room();
        listing.rooms = new Room[0];
        listing.addRoom(room);
        if (ArrayUtils.contains(listing.rooms, room)) {
            System.out.println("[Add Room] Testing passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Add Room] Testing failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testAddBedroom() {
        Room bedroom = new Room();
        listing.bedrooms = new Room[0];
        listing.addBedroom(bedroom);
        if (ArrayUtils.contains(listing.bedrooms, bedroom)) {
            System.out.println("[Add bedroom] Testing passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Add bedroom] Testing failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testRemoveBedroom() {
        Room bedroom = new Room();
        listing.bedrooms = new Room[0];
        listing.addBedroom(bedroom);
        listing.removeBedroom(bedroom);
        if (!ArrayUtils.contains(listing.bedrooms, bedroom)) {
            System.out.println("[Add bedroom] Testing passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Add bedroom] Testing failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testRemoveRoom() {
        Room room = new Room();
        listing.bedrooms = new Room[0];
        listing.addRoom(room);
        listing.removeRoom(room);
        if (!ArrayUtils.contains(listing.bedrooms, room)) {
            System.out.println("[Add room] Testing passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Add room] Testing failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testGetTotalPrice() {
        if (listing.getTotalPrice() == (leaseRentLength*monthlyPrice)) {
            System.out.println("[Total Price] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Total Price] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testGetRentLength() {
        if (listing.getRentLength() == leaseRentLength) {
            System.out.println("[Total Rent Length] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Total Rent Length] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testGetTotalSize() {
        Room room = new Room();
        room.size = Math.round(Math.random()*100);
        listing.bedrooms = new Room[0];
        listing.addRoom(room);
        if (listing.getTotalSize() == room.size) {
            System.out.println("[Total Size] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Total Size] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testGetIsEmpty() {
        boolean b1 = false;
        String og = listing.title;
        listing.title = "";
        b1 = listing.isEmpty();
        listing.title = og + " ";
        if (!listing.isEmpty() && b1) {
            System.out.println("[IsEmpty] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***IsEmpty] Test failed.");
            assertFalse(true);
        }
    }
    
    // JavaFX and JUnit errors
    /*
    @Test
    void testEditLease() {
        try {
            listing.editLease();
        } catch (Exception e) {
            System.out.println("[***EditLease] Test failed.");
            assertFalse(true);
        }
        
        System.out.println("[EditLease] Test passed.");
        assertTrue(true);
    }
    
    @Test
    void testViewLease() {
        try {
            listing.viewLease();
        } catch (Exception e) {
            System.out.println("[***ViewLease] Test failed.");
            assertFalse(true);
        }
        
        System.out.println("[ViewLease] Test passed.");
        assertTrue(true);
    }*/
    
    @Test
    void testGetStreetAddress() {
        if (listing.getStreetAddress().equals(parent.streetAddress + " " + listing.apartmentNumber
                + ", " + parent.city + ", " + parent.state + " (" + parent.postalCode + ")")) {
            System.out.println("[GetStreetAddress] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***GetStreetAddress] Test failed.");
            assertFalse(true);
        }
    }
}
