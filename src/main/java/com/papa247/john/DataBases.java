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
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;

import com.papa247.john.Enumerators.*;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Exceptions;
import com.papa247.john.Support.Range;
import com.papa247.john.UIComponents.AlertWindows;
import com.papa247.john.User.User;
import javafx.scene.control.ButtonType;


public class DataBases {
    
    private static User[] users = new User[0];
    private static Address[] addresses = new Address[0];
    private static Listing[] listings = new Listing[0];
    
    private static int lastListingID;
    private static int lastAddressID;
    
    // File paths
    private static class DataPaths {
        public static Path users = Paths.get("./data/users.json");
        public static Path addresses = Paths.get("./data/addresses.json");
    }

    
    
    /**
     * Saves a JSONArray to a file
     * 
     * @param name      The name of the database
     * @param path      The Path of the file (grab from DataPaths)
     * @param noError   Closes the program on load errors (used on startup)
     * @param roundTwo  Internal, set true after we created the empty file and want to load it
     * @return          The JSONArray loaded from the file (blank if load failed)
     */
    private static JSONArray loadFile(String name, Path path, boolean roundTwo) {
        System.out.println("[DataBases] Loading " + path.toString());
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
                        loadFile(name, path, true); // Go again
                    } catch (IOException e) {
                        AlertWindows.showException("Data base subsystem", "Data base: " + name, "Failed to save the file \"" + path.toString()  + "\"!", e);
                        return new JSONArray("[]");
                    }
                } else { // never mind.
                    AlertWindows.showMessage("Data base subsystem", "Data base: " + name, "Failed to read the file \"" + path.toString()
                            + "\"... Didn't we just create this?");
                }
            }
            return new JSONArray("[]");
        }
        
        return new JSONArray("[]"); // Wont reach
    }
    private static JSONArray loadFile(String name, Path path) {
        return loadFile(name, path, false);
    }
    
    /**
     * Save a JSONArray to file
     * 
     * @param ja    The JSONArray object we're saving
     * @param path  The file path we're writing to
     * @return      Whether the save was successful
     */
    private static boolean saveFile(JSONArray ja, Path path) {
        System.out.println("[DataBases] Saving " + path.toString());
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
        users = ArrayUtils.add(users, newUsers, user);
    }
    /**
     * Removes a user from the users array
     * @param user  The user object you wish to remove
     */
    public static void removeUser(User user) {
        if (users.length==0)
            return;
        
        User[] newUsers = new User[users.length-1];
        users = ArrayUtils.remove(users, newUsers, user);
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
     * Get a copy of the users array
     * @return the database's internal users array (but cloned)
     */
    public static User[] getUsers() {
        return users.clone();
    }
    
    /**
     * Load and populate the users array
     * @return load successful
     */
    public static boolean loadUsers() {
        JSONArray ja = loadFile("Users", DataPaths.users);
        int length = ja.length();
        for (int i=0; i<length; i++) {
            addUser(new User(new JSONObject(ja.getString(i)))); // Get the string -> convert to JSONObject -> convert to User object -> add to users
        }
        return true;
    }
    /**
     * Save the populated users array
     * @return save successful
     */
    public static boolean saveUsers() {
        JSONArray ja = new JSONArray();
        for (int i=0; i<users.length; i++) {
            ja.put(users[i].toJSON().toString());
        }
        return saveFile(ja, DataPaths.users);
    }
    
        
    // Address API
    /**
     * Returns a unique number to be used for a new address listing. No other Address will have this ID.
     * @return
     */
    public static int assignNewAddressID() {
        lastAddressID += 1;
        return lastAddressID++;
    }
    
    /**
     * Adds an Address to the addresses array (if not already there)
     * @param user  The address object you wish to add
     */
    public static void addAddress(Address address) {
        int ogLength = addresses.length;
        Address[] newAddresses = new Address[addresses.length+1];
        addresses = ArrayUtils.add(addresses, newAddresses, address);
        if (addresses.length==ogLength+1)
            lastAddressID = Math.max(addresses[ogLength].id, lastAddressID);
    }
    /**
     * Removes an address from the addresses array
     * @param address  The address object you wish to remove
     */
    public static void removeAddress(Address address) {
        if (addresses.length==0)
            return;
        
        Address[] newAddresses = new Address[addresses.length-1];
        addresses = ArrayUtils.remove(addresses, newAddresses, address);
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
    
    public static Address getAddress(int lID) {
        for (Address address : addresses) {
            if (lID == address.id)
                return address;
        }
        throw new Exceptions.NoListingFound("Listing ID invalid.");
    }
    
    public static Address[] getAddresses() {
        return addresses.clone();
    }
    
    /**
     * Load and populate the addresses array
     * @return load successful
     */
    public static boolean loadAddresses() {
        JSONArray ja = loadFile("Addresses", DataPaths.addresses);
        int length = ja.length();
        for (int i=0; i<length; i++) {
            addAddress(new Address(ja.getJSONObject(i)));
        }        
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
            ja.put(addresses[i].toJSON().toString());
        }
        return saveFile(ja, DataPaths.addresses);
    }
    
    
    // Listing API
    /**
     * Not public, but who cares.
     * Fills the listings array using the listing data in the addresses (addresses[i].listings => listings)
     */
    private static void renderListings() {
        int lID = 0;
        for (Address address : addresses) {
            for (Listing listing : address.listings) {
                lID = Math.max(lID, listing.id); // Used when assigning listing IDs (just a counter that goes up)
                addListing(listing);
            }
        }
    }
    /**
     * For each address in each listing we add that listing to the address
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
    
    /**
     * Assign a new unique listing ID, which has not and will not be claimed by another listing
     * @return unique listing ID
     */
    public static int assignNewListingID() {
        lastListingID += 1;
        return lastListingID++;
    }
    /**
     * Add a listing object to the database's listing array. You will need to save the changes.
     * @param listing the listing object to add
     */
    public static void addListing(Listing listing) {
        int ogLength = listings.length;
        Listing[] newListings = new Listing[ogLength+1];
        listings = ArrayUtils.add(listings, newListings, listing);
        if (listings.length==ogLength+1)
            lastListingID = Math.max(lastListingID, listings[ogLength].id);
    }
    /**
     * Remove a listing object from the database's listing array. You will need to save the changes.
     * @param listing the listing object to remove
     */
    public static void removeListing(Listing listing) {
        Listing[] newListings = new Listing[listings.length-1];
        listings = ArrayUtils.remove(listings, newListings, listing);
    }
    
    /**
     * Get the listing object with ID lID
     * @param lID the unique listing ID
     * @return the listing object with ID lID
     */
    public static Listing getListing(int lID) {
        for (Listing listing : listings) {
            if (lID == listing.id)
                return listing;
        }
        throw new Exceptions.NoListingFound("Listing ID invalid.");
    }
    /**
     * Get a listing object from a saved apartment number (used in searching)
     * @param appartmentNumber
     * @return
     */
    public static Listing getListing(String apartmentNumber) {
        for (Listing listing : listings) {
            if (listing.appartmentNumber.equals(apartmentNumber))
                return listing;
        }
        throw new Exceptions.NoListingsFound("Failed to find any search results.");
    }
    
    // meat and potatoes :)
    /**
     * Provides an array of listings adhearing to the provided searchData
     * @param searchData a new instance of SearchData with filled in requirements
     * @return an array of listings to searchData.
     */
    public static Listing[] getListings(SearchData searchData) {
        /*
         * For every Listing in listings, we compare it's data to searchData. If we have a match, we add that listing to a new array (sData). At the end we return that array
         * 
         * We need this to be relatively quick, so mark if we need to check a search term (or whatever) We do this below with:
         */
        boolean chkPrice = (searchData.priceRange == null)? false : true; // If the searchData entry for that is NULL, set to FALSE (do not check)
        boolean chkRentLength = (searchData.rentLength == null)? false : true;
        boolean chkOwner = (searchData.owner == null)? false : true;
        boolean chkBedroomCount = (searchData.bedroomCount == null)? false : true;
        boolean chkTotalSize = (searchData.totalSize == null)? false : true;
        boolean chkWindowCount = (searchData.windows == null)? false : true;
        boolean chkBedroomSize = (searchData.bedroomSize == null)? false : true;
        
        boolean chkAmminities = false;
        boolean chkListingType = false;
        boolean chkRoomType = false;
        boolean chkFloorType = false;
        boolean chkExtensions = false;
        boolean chkAppliances = false;
        boolean chkFixtures = false;
        boolean chkFurniture = false;
        
        for (Amminities e : Amminities.values()) {
            if (searchData.amminities.getInt(e.toString()) != 1) {
                chkAmminities = true;
                break;
            }
        }
        for (ListingType e : ListingType.values()) {
            if (searchData.listingTypes.getInt(e.toString()) != 1) {
                chkListingType = true;
                break;
            }
        }
        for (RoomType e : RoomType.values()) {
            if (searchData.roomTypes.getInt(e.toString()) != 1) {
                chkRoomType = true;
                break;
            }
        }
        for (FloorType e : FloorType.values()) {
            if (searchData.floorTypes.getInt(e.toString()) != 1) {
                chkFloorType = true;
                break;
            }
        }
        for (Extensions e : Extensions.values()) {
            if (searchData.extensions.getInt(e.toString()) != 1) {
                chkExtensions = true;
                break;
            }
        }
        for (Appliances e : Appliances.values()) {
            if (searchData.appliances.getInt(e.toString()) != 1) {
                chkAppliances = true;
                break;
            }
        }
        for (Fixtures e : Fixtures.values()) {
            if (searchData.fixtures.getInt(e.toString()) != 1) {
                chkFixtures = true;
                break;
            }
        }
        for (Furniture e : Furniture.values()) {
            if (searchData.furniture.getInt(e.toString()) != 1) {
                chkFurniture = true;
                break;
            }
        }
        
        Listing[] foundListings = new Listing[listings.length];
        int length = 0;
        for (Listing listing : listings) {
            if (chkPrice) {
                if (!searchData.priceRange.within(listing.monthlyPrice))
                    continue;
            }
            if (chkRentLength) {
                if (!searchData.rentLength.within(listing.rentLength))
                    continue;
            }
            if (chkOwner) {
                if (!searchData.owner.equals(listing.owner))
                    continue;

            }
            if (chkBedroomCount) {
                if (!searchData.bedroomCount.within(listing.bedrooms.length))
                    continue;
            }
            if (chkTotalSize) {
                if (!searchData.totalSize.within(listing.getTotalSize()))
                    continue;
            }
            if (chkWindowCount) {
                int mWin = 0;
                for (Room room : listing.bedrooms)
                    mWin = Math.max(room.windows, mWin);
                if (!searchData.windows.within(mWin))
                    continue;
            }
            if (chkBedroomSize) {
                boolean matched = false;
                for (Room room : listing.bedrooms) {
                    if (searchData.bedroomSize.within(room.size)) {
                        matched = true;
                        break;
                    }
                }
                if (!matched)
                    continue;
            }
            
            if (chkAmminities) {
                boolean matched = true;
                for (Amminities e : Amminities.values()) {
                    int val = searchData.amminities.getInt(e.toString());
                    if (val == 0) { // Can't have
                        matched = !ArrayUtils.contains(listing.amminities, e); // If we do have 'e' in the array, we DO NOT match.
                    } else if (val == 2) {
                        matched = ArrayUtils.contains(listing.amminities, e); // If we do have 'e' in the array, WE MATCH
                    }
                    
                    if (!matched)
                        break;
                }
                if (!matched)
                    continue;
            }
            if (chkListingType) {
                boolean matched = true;
                for (ListingType e : ListingType.values()) {
                    int val = searchData.listingTypes.getInt(e.toString());
                    if (val == 0) { // Can't have
                        matched = !listing.listingType.equals(e); // If we do have 'e' in the array, we DO NOT match.
                    } else if (val == 2) {
                        matched = listing.listingType.equals(e); // If we do have 'e' in the array, WE MATCH
                    }
                    
                    if (!matched)
                        break;
                }
                if (!matched)
                    continue;
            }
            if (chkRoomType) {
                boolean match = true;
                for (RoomType e : RoomType.values()) {
                    int val = searchData.roomTypes.getInt(e.toString());
                    if (val == 0) { // Can't have
                        for (Room room : listing.rooms) {
                            match = (room.type != e && match); // If room type is NOT e (what we're checking) AND we're already a 'match', set 'match' to true
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (room.type != e && match);
                        }
                    } else if (val == 2) {
                        match = false;
                        for (Room room : listing.rooms) {
                            match = (room.type == e || match); // If room type IS e (what we're checking) OR we're a 'match', set 'match' to true
                        }
                        if (match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (room.type == e || match);
                        }
                    }
                    
                    if (!match)
                        break;
                }
                if (!match)
                    continue;
            }
            if (chkFloorType) {
                boolean match = true;
                for (FloorType e : FloorType.values()) {
                    int val = searchData.floorTypes.getInt(e.toString());
                    if (val == 0) { // Can't have
                        for (Room room : listing.rooms) {
                            match = (room.flooring != e && match);
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = !(room.flooring != e && match);
                        }
                    } else if (val == 2) {
                        for (Room room : listing.rooms) {
                            match = (room.flooring == e || match);
                        }
                        for (Room room : listing.rooms) {
                            match = (room.flooring == e || match);
                        }
                    }
                    
                    if (!match)
                        break;
                }
                if (!match)
                    continue;
            }
            if (chkExtensions) {
                boolean match = true;
                for (Extensions e : Extensions.values()) {
                    int val = searchData.extensions.getInt(e.toString());
                    if (val == 0) { // Can't have
                        for (Room room : listing.rooms) {
                            match = (!ArrayUtils.contains(room.extensions, e) && match); // If room contains existion, set false.
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (!ArrayUtils.contains(room.extensions, e) && match);
                        }
                    } else if (val == 2) {
                        for (Room room : listing.rooms) {
                            match = (ArrayUtils.contains(room.extensions, e) || match);
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (ArrayUtils.contains(room.extensions, e) || match);
                        }
                    }
                    
                    if (!match)
                        break;
                }
                if (!match)
                    continue;
            }
            if (chkAppliances) {
                boolean matched = true;
                for (Appliances e : Appliances.values()) {
                    int val = searchData.appliances.getInt(e.toString());
                    if (val == 0) { // Can't have
                        matched = !(ArrayUtils.contains(listing.rooms, e.toString()) || ArrayUtils.contains(listing.bedrooms, e.toString()));
                    } else if (val == 2) {
                        matched = (ArrayUtils.contains(listing.rooms, e.toString()) || ArrayUtils.contains(listing.bedrooms, e.toString()));
                    }
                    
                    if (!matched)
                        break;
                }
                if (!matched)
                    continue;
            }
            if (chkFixtures) {
                boolean match = true;
                for (Fixtures e : Fixtures.values()) {
                    int val = searchData.fixtures.getInt(e.toString());
                    if (val == 0) { // Can't have
                        for (Room room : listing.rooms) {
                            match = (!ArrayUtils.contains(room.fixtures, e) && match);
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (!ArrayUtils.contains(room.fixtures, e) && match);
                        }
                    } else if (val == 2) {
                        for (Room room : listing.rooms) {
                            match = (ArrayUtils.contains(room.fixtures, e) || match);
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (ArrayUtils.contains(room.fixtures, e) || match);
                        }
                    }
                    
                    if (!match)
                        break;
                }
                if (!match)
                    continue;
            }
            if (chkFurniture) {
                boolean match = true;
                for (Furniture e : Furniture.values()) {
                    int val = searchData.furniture.getInt(e.toString());
                    if (val == 0) { // Can't have
                        for (Room room : listing.rooms) {
                            match = (!ArrayUtils.contains(room.fixtures, e) && match);
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (!ArrayUtils.contains(room.fixtures, e) && match);
                        }
                    } else if (val == 2) {
                        for (Room room : listing.rooms) {
                            match = (ArrayUtils.contains(room.fixtures, e) || match);
                        }
                        if (!match)
                            break;
                        for (Room room : listing.rooms) {
                            match = (ArrayUtils.contains(room.fixtures, e) || match);
                        }
                    }
                    
                    if (!match)
                        break;
                }
                if (!match)
                    continue;
            }
        }

        
        throw new Exceptions.NoListingsFound("Failed to find any search results.");
    }
    /**
     * Get an array of listings at a particular address
     * @param address the address that owns the listings
     * @return array of listings own by an address
     */
    public static Listing[] getListings(Address address) {
        for (Address ad : addresses) {
            if (ad.equals(address))
                return ad.listings;
        }
        throw new Exceptions.NoListingsFound("Failed to find any search results.");
    }
    /**
     * Return all database listings
     * @return databases listings (cloned)
     */
    public static Listing[] getListings() {
        return listings.clone();
    }
    
    
    /**
     * Load listings from the JSON files
     * @return if load successful
     */
    public static boolean loadListings() {
        return loadAddresses();
    }
    /**
     * Save listings to a JSON file
     * @return if save successful
     */
    public static boolean saveListings() {
        syncListings();        
        renderListings();
        return saveAddresses();
    }
    
    
    // TODO: Return a new exception when save/load fails
    // Optionally explain why the save/load failed. Either way this will allow our code to react to and explain the problem at hand.
    
    /**
     * Load and setup all data from JSON files for the DataBase
     * @return load successful
     */
    public static boolean load() {
        boolean okay = DataBases.loadUsers();
        if (!okay) return false;
        okay =  DataBases.loadListings(); // This will load the addresses then the listings.
        if (!okay) return false;
        for (User user : DataBases.getUsers()) {
            user.renderReviews(); // Update the review objects to point to the proper listings/addresses
        }
        return true;
    }
    /**
     * Save all database data to JSON files
     * @return save successful
     */
    public static boolean save() {
        boolean okay = DataBases.saveListings();
        if (!okay) return false;
        okay = DataBases.saveAddresses(); // Should already be called by saveListings()
        if (!okay) return false;
        okay = DataBases.saveUsers();
        return okay;
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
        public Range bedroomCount;
        public JSONObject amminities;
        public JSONObject listingTypes;
        public Range totalSize; // Apartment size (total size)
        
        // Room data
        public Range windows;
        public Range bedroomSize; // Room size
        public JSONObject roomTypes;
        public JSONObject floorTypes;
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
                amminities.put(e.toString(), 1);
            }
            for (ListingType e : ListingType.values()) {
                listingTypes.put(e.toString(), 1);
            }
            for (RoomType e : RoomType.values()) {
                roomTypes.put(e.toString(), 1);
            }
            for (FloorType e : FloorType.values()) {
                floorTypes.put(e.toString(), 1);
            }
            for (Extensions e : Extensions.values()) {
                extensions.put(e.toString(), 1);
            }
            for (Appliances e : Appliances.values()) {
                appliances.put(e.toString(), 1);
            }
            for (Fixtures e : Fixtures.values()) {
                fixtures.put(e.toString(), 1);
            }
            for (Furniture e : Furniture.values()) {
                furniture.put(e.toString(), 1);
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