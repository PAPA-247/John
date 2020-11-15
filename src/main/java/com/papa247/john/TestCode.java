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
import com.papa247.john.Enumerators.ListingType;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Lease;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Listing.Room;
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
            
            return failedCount;
        }
    }
    
    public static class ListingTests {
        private Address parent = new Address();
        private Listing listing = new Listing();
        
        private int id = DataBases.assignNewListingID();
        private String apartmentNumber = "";
        private double monthlyPrice = 100;
        private String title = "Testing";
        private String description = "Description";
        private Lease lease;
        
        private String leaseContents = "Lease agreement";
        private String leaseTitle = "Lease title";
        private int leaseRentLength = 12;
        
        private ListingType listingType = ListingType.APARTMENT;
        
        private String[] photos = new String[0];
        private Amminities[] amminities = new Amminities[0];
        private Room[] rooms = new Room[0];
        private Room[] bedrooms = new Room[0];
        
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
            System.out.println("[Listing] Tests completed.");
            if (failedCount>0)
                System.out.println("[Listing] Failed tests: " + failedCount);
            
            return failedCount;
        }
    }
}
