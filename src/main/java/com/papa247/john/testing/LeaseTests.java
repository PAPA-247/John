/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Nov 17, 2020
*/

package com.papa247.john.testing;

import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Lease;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
import com.papa247.john.Listing.Signer;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.DataBases;
import com.papa247.john.User.User;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LeaseTests {
 // We already know a lease can be loaded and saved (via JSON) from the Listing tests
    public User sampleUser;
    public Address parent = new Address();
    public Listing listing = new Listing();
    public Lease lease;

    public String leaseContents = "Lease agreement";
    public String leaseTitle = "Lease title";
    public int leaseRentLength = 12;
    
    public int id = DataBases.assignNewListingID();
    public String apartmentNumber = "";
    public double monthlyPrice = 100;
    public String title = "Testing";
    public String description = "Description";
    public ListingType listingType = ListingType.APARTMENT;
    
    public String[] photos = new String[0];
    public Amminities[] amminities = new Amminities[0];
    public Room[] rooms = new Room[0];
    public Room[] bedrooms = new Room[0];
    
    @BeforeEach
    void setup() {
        parent = new Address();
        parent.city = "Testvill";
        parent.streetAddress = "123 Test";
        parent.state = "Panic";
        parent.postalCode = "04523";
        
        
        lease = new Lease(listing);
        lease.contents = leaseContents;
        lease.title = leaseTitle;
        lease.rentLength = leaseRentLength;
        
        
        JSONObject jo = new JSONObject();
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
        lease.listing = listing;
        listing.lease = lease;
                
        DataBases.addListing(listing);
        DataBases.saveListings();
        
        sampleUser = new User();
        sampleUser.firstName = "Billy";
        sampleUser.middleName = "";
        sampleUser.lastName = "Thomas";
        sampleUser.phoneNumber = "+15386661888";
        sampleUser.emailAddress = "billyt@team.papa";
        sampleUser.accountType = AccountType.STUDENT;
        sampleUser.studentID = "W0080082";
        sampleUser.username = "sampleuserobj";
        sampleUser.setPassword("1234".toCharArray());
        
        DataBases.addUser(sampleUser);
        DataBases.saveUsers();
    }
    
    @AfterEach
    void tearDown() {
        DataBases.removeListing(listing);
        DataBases.removeUser(sampleUser);
        DataBases.save();
    }
    

    @Test
    void testAddSigner() {
        lease.addSigner(sampleUser);
        if (ArrayUtils.contains(lease.signers, new Signer(sampleUser))) {
            System.out.println("[Lease: AddSigner] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Lease: AddSigner] Test failed.");
            assertFalse(true);
        }
    }

    @Test
    void testRemoveSigner() {
        lease.signers = ArrayUtils.add(lease.signers, new Signer[lease.signers.length+1], new Signer(sampleUser));
        lease.removeSigner(sampleUser);
        if (!ArrayUtils.contains(lease.signers, new Signer(sampleUser))) {
            System.out.println("[Lease: RemoveSigner] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Lease: RemoveSigner] Test failed.");
            assertFalse(true);
        }
    }

    @Test
    void testGetSigned() {
        lease.signers = new Signer[0];                
        Signer signer =  new Signer(sampleUser);
        signer.sign();
        lease.signers = ArrayUtils.add(lease.signers, new Signer[lease.signers.length + 1], signer);
        if (lease.getSigned(true) && lease.getSigned()) {
            System.out.println("[Lease: GetSigned] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Lease: GetSigned] Test failed.");
            assertFalse(true);
        }
    }

    @Test
    void testSign() {
        lease.signers = new Signer[0];
        lease.sign(sampleUser);
        if (!lease.getSigned()) {
            System.out.println("[***Lease: Signed] Test failed.");
            assertFalse(true);
        }
        
        lease.signers = new Signer[0];
        Signer signer =  new Signer(sampleUser);
        lease.signers = ArrayUtils.add(lease.signers, new Signer[1], signer);
        lease.sign(sampleUser);
        if (lease.getSigned()) {
            System.out.println("[Lease: Signed] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Lease: Signed] Test failed.");
            assertFalse(true);
        }
        
    }

    @Test
    void testTostring() {
        if (lease.toString().equals(lease.title)) {
            System.out.println("[Lease: tostring] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Lease: tostring] Test failed.");
            assertFalse(true);
        }
    }
}
