/**
* PAPA-247: Project JOHN
*
*   This file will host ALL databases in one.
*   Want the users? Do getUsers()
*   Addresses? Sure, getAddresses()
*   so on so forth... we just slap all the methods and properties from the other Databases into here
*   
*   ... which makes UserDB, AddressDB, and ListingDB deprecated, but eh
*   
*   
*   Important information:
*       Reviews are stored with the user's data. This means loading the user's data we'll have to phrase the reviews.. which requires the addresses/listings...
*       SO, we MUST load the addresses & listings and THEN load the Users. This shouldn't be a problem, but is something to keep in mind.
*   
*   
*   
*   
*
* @author cnewb
*/

package com.papa247.john;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import com.papa247.john.Enumerators.*;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Support.Exceptions;
import com.papa247.john.Support.Range;
import com.papa247.john.UIComponents.AlertWindows;
import com.papa247.john.User.User;
import javafx.scene.control.ButtonType;


public class DataBases {
    
    private static User[] users;
    private static Address[] addresses;
    private static Listing[] listings;
    
    // File paths
    private static class DataPaths {
        public static Path users = Paths.get("./data/users.json");
        public static Path addresses = Paths.get("./data/addresses.json");
    }
    
    public DataBases() {}
    
    
    /**
     * Saves a JSONArray to a file
     * 
     * @param name      The name of the database
     * @param path      The Path of the file (grab from DataPaths)
     * @param noError   Closes the program on load errors (used on startup)
     * @param roundTwo  Internal, set true after we created the empty file and want to load it
     * @return          The JSONArray loaded from the file (blank if load failed)
     */
    private static JSONArray loadFile(String name, Path path, boolean noError, boolean roundTwo) {
        String contents = "";
        if (path.toFile().isFile()) {
            try {
                contents = contents + new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                return new JSONArray(contents);
                
            } catch (Exception e) {
                AlertWindows.showException("Data base subsystem", "Data base: " + name, "Failed to read the file \"" + path.toString() + "\"", e);
            }
        } else {
            if (!roundTwo) { // Try to create the file
                Optional<ButtonType> result = AlertWindows.showPrompt("Data base subsystem", "Data base: " + name, "Failed to read the file \"" + path.toString()
                        + "\" because it does not exist.\nWould you like to create it?");
                
                if (result.get() == ButtonType.YES){
                    try {
                        Files.write(path, "[]".getBytes());
                        loadFile(name, path, noError, true); // Go again
                    } catch (IOException e) {
                        AlertWindows.showException("Data base subsystem", "Data base: " + name, "Failed to save the file \"" + path.toString()  + "\"!", e);
                        if (noError)
                            System.exit(1);
                        return new JSONArray("[]");
                    }
                } else { // never mind.
                    AlertWindows.showMessage("Data base subsystem", "Data base: " + name, "Failed to read the file \"" + path.toString()
                            + "\"... Didn't we just create this?");
                }
            }
            if (noError)
                System.exit(1);
            return new JSONArray("[]");
        }
        
        return new JSONArray("[]"); // Wont reach
    }
    private static JSONArray loadFile(String name, Path path, boolean noError) {
        return loadFile(name, path, noError, false);
    }
    private static JSONArray loadFile(String name, Path path) {
        return loadFile(name, path, false, false);
    }
    
    /**
     * Save a JSONArray to file
     * 
     * @param ja    The JSONArray object we're saving
     * @param path  The file path we're writing to
     * @return      Whether the save was successful
     */
    private static boolean saveFile(JSONArray ja, Path path) {
        try {
            Files.write(path, ja.toString().getBytes());
            return true;
        } catch (Exception e) {
            AlertWindows.showException("Data base subsystem", "Data base save failure. ", "Failed to save the file \"" + path.toString()  + "\"!", e);
            return false;
        }
    }
    
    
    
    
    
    // Public API
    
    // User API
    /**
     * Adds a User to the users array (if not already there)
     * @param user  The user object you wish to add
     */
    public static void addUser(User user) {
        User[] newUsers = new User[users.length+1];
        for (int i = 0; i<users.length; i++) {
            if (users[i].equals(user))
                return;
        }
        
        newUsers[users.length] = user;
        users = newUsers;
    }
    /**
     * Removes a user from the users array
     * @param user  The user object you wish to remove
     */
    public static void removeUser(User user) {
        if (users.length==0)
            return;
        
        User[] newUsers = new User[users.length-1];
        int shift = 0;
        for (int i=0; i<users.length; i++) {
            if (users[i].equals(user)) {
                shift = 1;
            } else {
                newUsers[i-shift] = users[i];
            }
        }
        users = newUsers;
    }
    
    /**
     * Get a user object with an attribute
     * @param lookup        The attribute we're searching for
     * @param lookupType    What that attribute is
     * @return              The user object
     */
    public static User getUser(String lookup, UsernameLookupType lookupType) {
        switch (lookupType) {
            case email_address:
                for (User user : users)
                    if (user.emailAddress.equals(lookup))
                        return user;
                break;
                
            case username:
                for (User user : users)
                    if (user.username.equals(lookup))
                        return user;
                break;
                
            case phone_number:
                for (User user : users)
                    if (user.phoneNumber.equals(lookup))
                        return user;
                break;
                
            case student_id:
                for (User user : users)
                    if (user.studentID.equals(lookup))
                        return user;
                break;
            
        }
        
        throw new Exceptions.NoSuchUserFound("No user could be found");
    }
    
    /**
     * Load and populate the users array
     * @return load successful
     */
    public static boolean loadUsers() {
        loadFile("Users", DataPaths.users);
        return true;
    }
    /**
     * Save the populated users array
     * @return save successful
     */
    public static boolean saveUsers() {
        JSONArray ja = new JSONArray();
        for (int i=0; i<users.length; i++) {
            
        }
        return saveFile(ja, DataPaths.users);
    }
    
        
    // Address API
    /**
     * Adds an Address to the addresses array (if not already there)
     * @param user  The address object you wish to add
     */
    public static void addAddress(Address address) {
        Address[] newAddresses = new Address[addresses.length+1];
        for (int i = 0; i<addresses.length; i++) {
            if (addresses[i].equals(address))
                return;
        }
        
        newAddresses[users.length] = address;
        addresses = newAddresses;
    }
    /**
     * Removes an address from the addresses array
     * @param address  The address object you wish to remove
     */
    public static void removeAddress(Address address) {
        if (addresses.length==0)
            return;
        
        Address[] newAddresses = new Address[addresses.length-1];
        int shift = 0;
        for (int i=0; i<addresses.length; i++) {
            if (addresses[i].equals(address)) {
                shift = 1;
            } else {
                newAddresses[i-shift] = addresses[i];
            }
        }
        addresses = newAddresses;
    }
    
    /**
     * Returns an address that is owned by owner
     * @param owner The user that owns the address
     * @return      The address object a user owns
     */
    public static Address getAddress(User owner) {
        for (Address address : addresses)
            if (address.owner.equals(owner))
                return address;
        
        throw new Exceptions.NoSuchAddressFound("No address found for user " + owner);
    }
    /**
     * Get an address at a particular street address in a city
     * @param streetAddress The physical street address
     * @param city          The physical city
     * @return              The address object
     */
    public static Address getAddress(String streetAddress, String city) {
        for (Address address : addresses)
            if (address.streetAddress.equals(streetAddress) && address.city.equals(city))
                return address;
        
        throw new Exceptions.NoSuchAddressFound("No address found for street address " + streetAddress + " in city " + city);
    }
    /**
     * Lookup address using a child listing
     * @param listing   A listing under the address we're searching
     * @return          The address object that owns {listing}
     */
    public static Address getAddress(Listing listing) {
        return listing.address;
    }
    /**
     * Load and populate the addresses array
     * @return load successful
     */
    public static boolean loadAddresses() {
        loadFile("Addresses", DataPaths.addresses);
        renderListings();
        return true;
    }
    /**
     * Save the populated addresses array
     * @return save successful
     */
    public static boolean saveAddresses() {
        // Save Listings
        syncListings();        
        JSONArray ja = new JSONArray();
        for (int i=0; i<addresses.length; i++) {
            
        }
        return saveFile(ja, DataPaths.addresses);
    }
    
    
    // Listing API
    /**
     * Not public, but who cares.
     * Fills the listings array using the listing data in the addresses (addresses[i].listings => listings)
     */
    private static void renderListings() {
        for (Address address : addresses) {
            for (Listing listing : address.listings)
                addListing(listing);
        }
    }
    /**
     * Same deal above, just in reverse
     */
    private static void syncListings() {
        for (Listing listing : listings) {
            for (Address address : addresses) {
                if (address.equals(listing.address)) {
                    address.addListing(listing); // This method SHOULD ignore duplicates...
                }
            }
        }
        renderListings(); // we synced the listings, which will include everything
    }
    
    
    public static void addListing(Listing listing) {
        
    }
    
    public static void removeListing(Listing listing) {
        
    }
        
    public static Listing getListing(String appartmentNumber) {
        for (Listing listing : listings) {
            if (listing.address.appartmentNumber.equals(appartmentNumber))
                return listing;
        }
        throw new Exceptions.NoListingsFound("Failed to find any search results.");
    }
    // meat and potatoes :)
    public static Listing[] getListings(SearchData searchData) {
        
        throw new Exceptions.NoListingsFound("Failed to find any search results.");
    }
    public static Listing[] getListings(Address address) {
        for (Address ad : addresses) {
            if (ad.equals(address))
                return ad.listings;
        }
        throw new Exceptions.NoListingsFound("Failed to find any search results.");
    }
    
    public static boolean loadListings() {
        return loadAddresses();
    }
    
    public static boolean saveListings() {
        syncListings();        
        renderListings();
        return saveAddresses();
    }
    
        
    // Other stuff (support)
    /**
     * A container for any and all search terms the system indexes and allows users to lookup by (so almost any listing/room attribute)
     * @author cnewb
     *
     */
    public static class SearchData {
        public Range priceRange;
        public Range rentLength;
        public User owner;
        public Range roomCount;
        public JSONObject amminities;
        public JSONObject listingTypes;
        public Range totalSize; // Apartment size (total size)
        
        // Room data
        public Range windows;
        public Range roomSize; // Room size
        public JSONObject roomTypes;
        public JSONObject flooring;
        public JSONObject extensions;
        public JSONObject appliances;
        public JSONObject fixtures;
        public JSONObject furniture;
        
        public SearchData() {
            // Setup JSONObjects
            /* Should be something like this:
            *  fixtures.fan = 0/1/2
            *  
            *  0 = Do not include (can not have)
            *  1 = Doesn't matter
            *  2 = Include (must have)
            *
            */
            for (Amminities e : Amminities.values()) {
                amminities.append(e.toString(), "1");
            }
            for (ListingType e : ListingType.values()) {
                listingTypes.append(e.toString(), "1");
            }
            for (RoomType e : RoomType.values()) {
                roomTypes.append(e.toString(), "1");
            }
            for (FloorType e : FloorType.values()) {
                flooring.append(e.toString(), "1");
            }
            for (Extensions e : Extensions.values()) {
                extensions.append(e.toString(), "1");
            }
            for (Appliances e : Appliances.values()) {
                appliances.append(e.toString(), "1");
            }
            for (Fixtures e : Fixtures.values()) {
                fixtures.append(e.toString(), "1");
            }
            for (Furniture e : Furniture.values()) {
                furniture.append(e.toString(), "1");
            }
            
        }
    }
    
    /**
     * Used when looking up data. This tells the lookup method what we're providing (email, username, oID, etc)
     */
    public enum UsernameLookupType {
        username,
        student_id,
        email_address,
        phone_number;
    }
    
    
    
}