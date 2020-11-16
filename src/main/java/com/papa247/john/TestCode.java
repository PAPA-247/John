/**
* PAPA-247: Project JOHN
*
*   Store test methods here
*
*/

package com.papa247.john;

import java.io.IOException;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Enumerators.Appliances;
import com.papa247.john.Enumerators.Extensions;
import com.papa247.john.Enumerators.Fixtures;
import com.papa247.john.Enumerators.FloorType;
import com.papa247.john.Enumerators.Furniture;
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.Enumerators.RoomType;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Lease;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
import com.papa247.john.Listing.Signer;
//import com.papa247.john.*;
import com.papa247.john.Support.*;
import com.papa247.john.UIComponents.ReviewController;
import com.papa247.john.User.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TestCode {
    
    private static Scanner in = new Scanner(System.in);
    
//  System.out.print("...wait...");
//  try {
//      Thread.sleep(2000);
//  } catch (InterruptedException e) {} // okay
//  
    
    public static void userDB() {
        System.out.println("Loading users data base...");
        DataBases.loadUsers();
        
        System.out.println("Listing users...");
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
        
        System.out.println("Creating a user (usrname: billy)");
        User user = new User();
        user.username = "billy";
        user.firstName = "Billy";
        user.middleName = "";
        user.lastName = "Thomas";
        user.phoneNumber = "+15386661888";
        user.emailAddress = "billyt@team.papa";
        user.accountType = AccountType.STUDENT;
        user.studentID = "W0080082";
        
        user.addReview(new Review(user, "Billy is amazing", "I absolutley love this guy! He's amazing!", user, 5));
        
        DataBases.addUser(user);
        
        
        System.out.println("User created, what should the password be?");
        
        user.setPassword(in.nextLine().toCharArray());
        
        
        System.out.println("Password set (users should be saved...)");
        
        System.out.println("Listing users...");
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
        
        User checkUser = DataBases.getUser("billy", UsernameLookupType.username);
        
        System.out.println("Checking password... Enter the password for Billy: ");
        System.out.println("Password correct: " + checkUser.isPassword(in.nextLine().toCharArray()));
        
        
        
        System.out.println("Removing billy...");
        DataBases.removeUser(DataBases.getUser(user.emailAddress, UsernameLookupType.email_address));
        
        System.out.println("Listing users...");
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
        
        System.out.println("User testing completed.");
    }
    
    public static void userList() {
        System.out.println("Listing users...");
        DataBases.loadUsers();
        for (User usr : DataBases.getUsers()) {
            System.out.println("Username: " + usr.username + " (email: " + usr.emailAddress + ")");
        }
    }
    
    public static void reviews() {
        //System.out.println("Be sure to login as billy first, otherwise you cannot edit the review.");
        User ogSession = Session.user;
        
        User user;
        try {
            user = DataBases.getUser("billy", UsernameLookupType.username);
        } catch (Exceptions.NoSuchUserFound e) {
            System.out.println("User billy not registered! Cannot test reviews. Make sure you register an account under \"billy\"!");
            return;
        }
        
        Session.user = user; // logged in now :^)
        System.out.println("Reviews of billy: ");
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
        
        System.out.println("Creating review object. (We're pointing it to Billy.");
        Review ourReview = new Review(user, user);
        
        System.out.println("Our review (title): " + ourReview.title);
        System.out.println("Our review (contents): " + ourReview.contents);
        
        DataBases.save();
        
        System.out.println("Opening review viewer");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("UIComponents/Review.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            ReviewController controller = fxmlLoader.getController();
            Scene reviewScene = new Scene(root, 640,320);
            Stage reviewScreen = new Stage();
            reviewScreen.initStyle(StageStyle.UTILITY);
//            reviewScreen.initModality(Modality.WINDOW_MODAL);
            reviewScreen.setScene(reviewScene);
            reviewScreen.setOnShown(e -> controller.setup(ourReview));
            reviewScreen.showAndWait();
        } catch (IOException e) {
            System.out.println("Or not...\n" + e.getMessage());
        }
        
        System.out.println("Reviews of billy: ");
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
        
//        if (ourReview!=null) {
//            System.out.println("Deleting the review...");
//            ourReview.delete();
//        }
        
        Session.user = ogSession; // back to normal
    }
    
    public static void deleteReviews() {
        User user = Session.user;
        
        if (user==null) {
            try {
                user = DataBases.getUser("billy", UsernameLookupType.username);
            } catch (Exceptions.NoSuchUserFound e) {
                System.out.println("No account found, please login first.");
                return;
            }
            System.out.println("Not logged in, using billy.");
        }
        
        System.out.println("Revmoing reviews of " + user.username);
        
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
            review.delete();
        }
        System.out.println("Revmoing reviews by " + user.username);
        for (Review review : user.getReviews()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
            review.delete();
        }
    }
    
    public static void listReviews(User user) {
        if (user == null) {
            user = Session.user;
            
            if (user==null) {
                try {
                    user = DataBases.getUser("billy", UsernameLookupType.username);
                } catch (Exceptions.NoSuchUserFound e) {
                    System.out.println("No account found, please login first.");
                    return;
                }
                System.out.println("Not logged in, using billy.");
            }
        }
        
        System.out.println("==============\n"
                + "Reviews by " + user.username);
        for (Review review : user.getReviews()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
        System.out.println("===============\n"
                + "Reviews of " + user.username);
        for (Review review : user.getReviewsOf()) {
            System.out.println("Title: " + review.title + " (contents: " + review.contents + ")");
        }
    }

    public static void selection() {
        System.out.println("Testing address selection.");
        Address a = Select.getAddress();
        if (a!=null)
            System.out.println("Selected: " + a.name);
        
        System.out.println("Testing listing selection.");
        Listing b = Select.getListing();
        if (b!=null)
            System.out.println("Selected: " + b.title);
        
        System.out.println("Testing user selection.");
        User c = Select.getUser();
        if (c!=null)
            System.out.println("Selected: " + c.username);
        
        
        System.out.println("Testing address selection.");
        Address[] aa = Select.getAddresses();
        for (Address a1 : aa)
            if (a1!=null)
                System.out.println("Selected: " + a1.name);
        
        System.out.println("Testing listing selection.");
        Listing[] bb = Select.getListings();
        for (Listing b1 : bb)
            if (b1!=null)
                System.out.println("Selected: " + b1.title);
        
        System.out.println("Testing user selection.");
        User[] cc = Select.getUsers();
        for (User c1 : cc)
            if (c1!=null)
                System.out.println("Selected: " + c1.username);
    }
    
    
    /**
     * Test the user class
     * 
     * Tests each method (besides delete, since we're not apart of the database).
     * 
     * @author cnewb
     *
     */
    public static class UserTests {
        
        private User user; // What we use to test with
        
        // These are the values we use for this user
        private String username = "testUser";
        private String firstName = "testing";
        private String lastName = "object";
        private String middleName = "AB";
        private String phoneNumber = "+15696661888";
        private String emailAddress = "testing@team.papa";
        private String studentID = "BNA001";
        private AccountType accountType = AccountType.ADMINISTRATOR;
        private String password = "testing123";
        
        
        
        public UserTests() {
            // Initialize the (user) object for testing
            
            JSONObject jo = new JSONObject();
            
            jo.put("username", username);
            jo.put("firstName", firstName);
            jo.put("middleName", middleName);
            jo.put("lastName", lastName);
            jo.put("phoneNumber", phoneNumber);
            jo.put("emailAddress", emailAddress);
            jo.put("studentID", "BNA001");
            jo.put("accountType", accountType.toNum());
            jo.put("token", "$USR$16$KaAL08EAgQKrzvgggBFm-Tp46aIGExBoCINk8FrrI94"); // Not used.
            
            user = new User(jo);
            
            Review review = new Review(user, user);
            review.contents = "This is a simple review.";
            review.title = "A review";
            user.addReview(review);
            
            Listing listing = new Listing();
            listing.title = "Testing (user) listing";
            user.ownedListings = new Listing[1];
            user.ownedListings[0] = listing;
            
            user.setPassword(password.toCharArray());
            
            DataBases.addUser(user);
            DataBases.saveUsers();
        }
        
        public boolean password() {
            if (user.isPassword(password.toCharArray())) {
                System.out.println("[Password] Test passed.");
                return true;
            } else {
                System.out.println("[***Password] Test failed.");
                return false;
            }
        }
        
        public boolean renderReviews() {
            try {
                System.out.println("[RenderReviews] Converting user to JSON...");
                JSONObject jo = user.toJSON();
                user = new User(jo);
                System.out.println("[RenderReviews] Rendering reviews ...");
                user.renderReviews();
                System.out.println("[RenderReviews] Test passed.");
                return true;
            } catch (Exception e) {
                System.out.println("[***RenderReviews] Test failed.");
                return false;
            }
        }
        
        public boolean renderListings() {
            try {
                System.out.println("[RenderListings] Converting user to JSON...");
                JSONObject jo = user.toJSON();
                user = new User(jo);
                System.out.println("[RenderListings] Rendering ...");
                user.renderListings();
                System.out.println("[RenderListings] Test passed.");
                return true;
            } catch (Exception e) {
                System.out.println("[***RenderListings] Test failed.");
                return false;
            }
        }
        
        public boolean json() {
            // Test user > json > user
            JSONObject jo = user.toJSON();
            if (new User(jo).equals(user)) {
                System.out.println("[User JSON] Test passed.");
                return true;
            } else {
                System.out.println("[***User JSON] Test failed.");
                return false;
            }
        }
        
        public boolean favoriteListing() {
            Listing listing = new Listing();
            listing.title = "Testing Data Obj 012";
            user.favoriteListing(listing);
            if (ArrayUtils.contains(user.savedListings, listing)) {
                System.out.println("[Favorite Listing] Test passed.");
                return true;
            } else {
                System.out.println("[***Favorite Listing] Test failed.");
                return false;
            }
        }
        
        public boolean unfavoriteListing() {
            Listing listing = new Listing();
            listing.title = "Testing Data Obj 013";
            user.savedListings = new Listing[1];
            user.savedListings[0] = listing;
            user.unfavoriteListing(listing);
            if (ArrayUtils.contains(user.savedListings, listing)) {
                System.out.println("[***Unfavorite Listing] Test failed.");
                return false;
            } else {
                System.out.println("[Unfavorite Listing] Test passed.");
                return true;
            }   
        }
        
        public boolean addReview() {
            Review review = new Review(user, user);
            review.title = "Testing Data Obj 014";
            user.addReview(review);
            if (ArrayUtils.contains(user.getReviews(), review)) {
                System.out.println("[Add Review] Test passed.");
                return true;
            } else {
                System.out.println("[***Add Review] Test failed.");
                return false;
            }
        }
        
        public boolean addReviewOf() {
            Review review = new Review(user, user);
            review.title = "Testing Data Obj 015";
            user.addReviewOf(review);
            if (ArrayUtils.contains(user.getReviewsOf(), review)) {
                System.out.println("[Add ReviewOf] Test passed.");
                return true;
            } else {
                System.out.println("[***Add ReviewOf] Test failed.");
                return false;
            }
        }
        
        public boolean getRating() {
            Review[] reviews = user.getReviewsOf();
            for (Review review : reviews) {
                user.removeReviewOf(review);
            }
            Review review = new Review(user, user);
            review.rating = 5;
            user.addReviewOf(review);
            if (user.getRating() == 5) {
                System.out.println("[Get Rating] Test passed.");
                return true;
            } else {
                System.out.println("[***Get Rating] Test failed.");
                return false;
            }
        }
        
        public boolean removeReview() {
            Review review = new Review(user, user);
            review.title = "Testing Data Obj 016";
            user.addReview(review);
            user.removeReview(review);
            if (!ArrayUtils.contains(user.getReviews(), review)) {
                System.out.println("[Remove Review] Test passed.");
                return true;
            } else {
                System.out.println("[***Remove Review] Test failed.");
                return false;
            }
        }
        
        public boolean removeReviewOf() {
            Review review = new Review(user, user);
            review.title = "Testing Data Obj 017";
            user.addReviewOf(review);
            user.removeReviewOf(review);
            if (!ArrayUtils.contains(user.getReviewsOf(), review)) {
                System.out.println("[Remove ReviewOf] Test passed.");
                return true;
            } else {
                System.out.println("[***Remove ReviewOf] Test failed.");
                return false;
            }
        }
        
        public boolean tostring() {
            if (user.toString().equals(username)) {
                System.out.println("[ToString] Test passed.");
                return true;
            } else {
                System.out.println("[***ToString] Test failed...?");
                return false;
            }
        }
        
        public boolean equals() {
            if (user.equals(user)) {
                System.out.println("[Equals] Test passed.");
                return true;
            } else {
                System.out.println("[***Equals] Test failed.");
                return false;
            }
        }
        
        public int testAll() {
            System.out.println("[User] Testing...");
            int failedCount = 0;
            if (!password())
                failedCount++;
            if (!renderReviews())
                failedCount++;
            if (!renderListings())
                failedCount++;
            if (!json())
                failedCount++;
            if (!favoriteListing())
                failedCount++;
            if (!unfavoriteListing())
                failedCount++;
            if (!addReview())
                failedCount++;
            if (!addReviewOf())
                failedCount++;
            if (!getRating())
                failedCount++;
            if (!removeReview())
                failedCount++;
            if (!removeReviewOf())
                failedCount++;
            if (!tostring())
                failedCount++;
            if (!equals())
                failedCount++;
            System.out.println("[User] Tests completed.");
            if (failedCount>0)
                System.out.println("[User] Failed tests: " + failedCount);
            
            DataBases.removeUser(user);
            
            return failedCount;
        }
    }
    
    public static class ListingTests {
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
        
        public ListingTests() {
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
        
        public boolean json() {
            JSONObject jo = listing.toJSON();
            if (new Listing(jo, parent).equals(listing)) {
                System.out.println("[JSON] Testing passed.");
                return true;
            } else {
                System.out.println("[***JSON] Testing failed.");
                return false;
            }
        }
        
        public boolean addRoom() {
            Room room = new Room();
            listing.rooms = new Room[0];
            listing.addRoom(room);
            if (ArrayUtils.contains(listing.rooms, room)) {
                System.out.println("[Add Room] Testing passed.");
                return true;
            } else {
                System.out.println("[***Add Room] Testing failed.");
                return false;
            }
        }
        
        public boolean addBedroom() {
            Room bedroom = new Room();
            listing.bedrooms = new Room[0];
            listing.addBedroom(bedroom);
            if (ArrayUtils.contains(listing.bedrooms, bedroom)) {
                System.out.println("[Add bedroom] Testing passed.");
                return true;
            } else {
                System.out.println("[***Add bedroom] Testing failed.");
                return false;
            }
        }
        
        public boolean removeBedroom() {
            Room bedroom = new Room();
            listing.bedrooms = new Room[0];
            listing.addBedroom(bedroom);
            listing.removeBedroom(bedroom);
            if (!ArrayUtils.contains(listing.bedrooms, bedroom)) {
                System.out.println("[Add bedroom] Testing passed.");
                return true;
            } else {
                System.out.println("[***Add bedroom] Testing failed.");
                return false;
            }
        }
        
        public boolean removeRoom() {
            Room room = new Room();
            listing.bedrooms = new Room[0];
            listing.addRoom(room);
            listing.removeRoom(room);
            if (!ArrayUtils.contains(listing.bedrooms, room)) {
                System.out.println("[Add room] Testing passed.");
                return true;
            } else {
                System.out.println("[***Add room] Testing failed.");
                return false;
            }
        }
        
        public boolean getTotalPrice() {
            if (listing.getTotalPrice() == (leaseRentLength*monthlyPrice)) {
                System.out.println("[Total Price] Test passed.");
                return true;
            } else {
                System.out.println("[***Total Price] Test failed.");
                return false;
            }
        }
        
        public boolean getRentLength() {
            if (listing.getRentLength() == leaseRentLength) {
                System.out.println("[Total Rent Length] Test passed.");
                return true;
            } else {
                System.out.println("[***Total Rent Length] Test failed.");
                return false;
            }
        }
        
        public boolean getTotalSize() {
            Room room = new Room();
            room.size = Math.round(Math.random()*100);
            listing.bedrooms = new Room[0];
            listing.addRoom(room);
            if (listing.getTotalSize() == room.size) {
                System.out.println("[Total Size] Test passed.");
                return true;
            } else {
                System.out.println("[***Total Size] Test failed.");
                return false;
            }
        }
        
        public boolean getIsEmpty() {
            boolean b1 = false;
            String og = listing.title;
            listing.title = "";
            b1 = listing.isEmpty();
            listing.title = og + " ";
            if (!listing.isEmpty() && b1) {
                System.out.println("[IsEmpty] Test passed.");
                return true;
            } else {
                System.out.println("[***IsEmpty] Test failed.");
                return false;
            }
        }
        
        public boolean editLease() {
            try {
                listing.editLease();
            } catch (Exception e) {
                System.out.println("[***EditLease] Test failed.");
                return false;
            }
            
            System.out.println("[EditLease] Test passed.");
            return true;
        }
        
        public boolean viewLease() {
            try {
                listing.viewLease();
            } catch (Exception e) {
                System.out.println("[***ViewLease] Test failed.");
                return false;
            }
            
            System.out.println("[ViewLease] Test passed.");
            return true;
        }
        
        public boolean getStreetAddress() {
            if (listing.getStreetAddress().equals(parent.streetAddress + " " + listing.apartmentNumber
                    + ", " + parent.city + ", " + parent.state + " (" + parent.postalCode + ")")) {
                System.out.println("[GetStreetAddress] Test passed.");
                return true;
            } else {
                System.out.println("[***GetStreetAddress] Test failed.");
                return false;
            }
        }
        
        
        /**
         * Lease Testing
         * @return
         */
        public static class LeaseTests {
            // We already know a lease can be loaded and saved (via JSON) from the Listing tests
            public User sampleUser;
            public ListingTests listingTests;
            public Lease lease;
            
            public LeaseTests(ListingTests lT) {
                listingTests = lT;
                lease = lT.lease;
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
            }
            
            public boolean addSigner() {
                lease.addSigner(sampleUser);
                if (ArrayUtils.contains(lease.signers, new Signer(sampleUser))) {
                    System.out.println("[Lease: AddSigner] Test passed.");
                    return true;
                } else {
                    System.out.println("[***Lease: AddSigner] Test failed.");
                    return false;
                }
            }
            
            public boolean removeSigner() {
                lease.signers = ArrayUtils.add(lease.signers, new Signer[lease.signers.length+1], new Signer(sampleUser));
                lease.removeSigner(sampleUser);
                if (!ArrayUtils.contains(lease.signers, new Signer(sampleUser))) {
                    System.out.println("[Lease: RemoveSigner] Test passed.");
                    return true;
                } else {
                    System.out.println("[***Lease: RemoveSigner] Test failed.");
                    return false;
                }
            }
            
            public boolean getSigned() {
                lease.signers = new Signer[1];                
                Signer signer =  new Signer(sampleUser);
                signer.sign();
                lease.signers = ArrayUtils.add(lease.signers, new Signer[lease.signers.length + 1], signer);
                if (lease.getSigned(true) && lease.getSigned()) {
                    System.out.println("[Lease: GetSigned] Test passed.");
                    return true;
                } else {
                    System.out.println("[***Lease: GetSigned] Test failed.");
                    return false;
                }
            }
            
            public boolean sign() {
                lease.signers = new Signer[1];
                lease.sign(sampleUser);
                if (!lease.getSigned()) {
                    System.out.println("[***Lease: Signed] Test failed.");
                    return false;
                }
                
                lease.signers = new Signer[0];
                Signer signer =  new Signer(sampleUser);
                lease.signers = ArrayUtils.add(lease.signers, new Signer[1], signer);
                lease.sign(sampleUser);
                if (!lease.getSigned()) {
                    System.out.println("[Lease: Signed] Test passed.");
                    return true;
                } else {
                    System.out.println("[***Lease: Signed] Test failed.");
                    return false;
                }
                
            }
            
            public boolean tostring() {
                if (lease.toString().equals(lease.title)) {
                    System.out.println("[Lease: tostring] Test passed.");
                    return true;
                } else {
                    System.out.println("[***Lease: tostring] Test failed.");
                    return true;
                }
            }
            
            public int testAll() {
                System.out.println("[Listing] Tests completed.");
                int failedCount = 0;
                if (!addSigner())
                    failedCount++;
                if (!removeSigner())
                    failedCount++;
                if (!getSigned())
                    failedCount++;
                if (!sign())
                    failedCount++;
                if (!tostring())
                    failedCount++;
                if (failedCount>0)
                    System.out.println("[Listing] Failed tests: " + failedCount);
                
                return failedCount;
            }
        }
        
        
        public int testAll() {
            System.out.println("[Listing] Testing...");
            int failedCount = 0;
            if (!json())
                failedCount++;
            if (!addRoom())
                failedCount++;
            if (!addBedroom())
                failedCount++;
            if (!removeBedroom())
                failedCount++;
            if (!removeRoom())
                failedCount++;
            if (!getTotalPrice())
                failedCount++;
            if (!getRentLength())
                failedCount++;
            if (!getTotalSize())
                failedCount++;
            if (!getIsEmpty())
                failedCount++;
            if (!editLease())
                failedCount++;
            if (!viewLease())
                failedCount++;
            if (!getStreetAddress())
                failedCount++;
            
            
            if (failedCount>0)
                System.out.println("[Listing] Failed tests: " + failedCount);
            
            
            System.out.println("[Listing Lease] Testing...");
            LeaseTests lT = new LeaseTests(this);
            lT.testAll();
                        
            
            return failedCount;
        }
    }
    public static class RoomTests {
        
        private Room room; // What we use to test with
        
        // These are the values we use for this room
        private RoomType type = RoomType.GUEST;   // Type of room
        private double size = 100.0;               // feet^3 of room
        private int windows = 2;                 // Windows count
        private String name = "Room Test Class";                // Room name (this allows us to refer to the room via the lease (typically "A", "B", etc))
        
        private FloorType flooring = FloorType.CARPET;         // Type of flooring in this room
        public Extensions[] extensions = new Extensions[0]; // Closets
        public Appliances[] appliances = new Appliances[0]; // ...
        public Fixtures[] fixtures =  new Fixtures[0];      // Ceiling lights, fans, etc
        public Furniture[] furniture = new Furniture[0];    // Desk, chair, bed, etc
        
        
        
        public RoomTests() {
            // Initialize the (user) object for testing
           
            
            JSONObject jo = new JSONObject();
            
            jo.put("name", name);
            jo.put("type", type.toString());
            jo.put("size", size);
            jo.put("windows", windows);
            jo.put("flooring", flooring.toString());

            JSONArray ja = new JSONArray();
            for (Extensions a : extensions)
                ja.put(a.toString());
            jo.put("extensions", ja);
            
            ja = new JSONArray();
            for (Appliances a : appliances)
                ja.put(a.toString());
            jo.put("appliances", ja);
            
            ja = new JSONArray();
            for (Fixtures a : fixtures)
                ja.put(a.toString());
            jo.put("fixtures", ja);
            
            ja = new JSONArray();
            for (Furniture a : furniture)
                ja.put(a.toString());
            jo.put("furniture", ja);
            
            
            room = new Room(jo);
            
 
        }
        
      
        
        public boolean isReset() {
          Room testRoom = new Room();
          testRoom.type = RoomType.BEDROOM;
          testRoom.size = 5.0;
          testRoom.windows = 2;
          testRoom.name = "Test Room";
          testRoom.flooring = FloorType.CARPET;
          testRoom.extensions = ArrayUtils.add(extensions, new Extensions[extensions.length+1], Extensions.CLOSET);
          testRoom.appliances = ArrayUtils.add(appliances, new Appliances[appliances.length+1], Appliances.AC);
          testRoom.fixtures = ArrayUtils.add(fixtures, new Fixtures[fixtures.length+1], Fixtures.BATHTUB);
          testRoom.furniture = ArrayUtils.add(furniture, new Furniture[furniture.length+1], Furniture.CHAIR);
          
          testRoom.reset();
          if(testRoom.type == RoomType.NONE ||  testRoom.size == 0.0 || testRoom.windows == 0 || testRoom.name == "" || testRoom.flooring == FloorType.NONE 
                  || testRoom.extensions == new Extensions[0] || testRoom.appliances == new Appliances[0] || testRoom.fixtures ==  new Fixtures[0] || testRoom.furniture == new Furniture[0]) {
              System.out.println("[Reset] Test passed.");
              return true;
          } else {
              System.out.println("[***Reset] Test failed.");
              return false;
          }
         }
        
   
        public boolean json() {
            // Test Room > json > room
            JSONObject jo = room.toJSON();
            if (new Room(jo).equals(room)) {
                System.out.println("[Room JSON] Test passed.");
                return true;
            } else {
                System.out.println("[***Room JSON] Test failed.");
                return false;
            }
        }
        
        public int testAll() {
            System.out.println("[Listing] Testing...");
            int failedCount = 0;
            if (!json())
                failedCount++;
            if (!isReset())
                failedCount++;
            return failedCount;
        }
    }
}
        

    

