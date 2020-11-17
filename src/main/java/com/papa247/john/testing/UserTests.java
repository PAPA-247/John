/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Nov 17, 2020
*/

package com.papa247.john.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.User.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTests {
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
    
    @BeforeEach
    public void setup() {
        // Initialize the (user) object for testing
        
        JSONObject jo = new JSONObject();
        
        jo.put("username", username);
        jo.put("firstName", firstName);
        jo.put("middleName", middleName);
        jo.put("lastName", lastName);
        jo.put("phoneNumber", phoneNumber);
        jo.put("emailAddress", emailAddress);
        jo.put("studentID", studentID);
        jo.put("accountType", accountType.toNum());
        jo.put("token", "$USR$16$KaAL08EAgQKrzvgggBFm-Tp46aIGExBoCINk8FrrI94"); // Not used.
        
        user = new User(jo);
        
        // We can't open ANY type of window
        /*Review review = new Review(user, user);
        review.contents = "This is a simple review.";
        review.title = "A review";
        user.addReview(review);*/
        
        Listing listing = new Listing();
        listing.title = "Testing (user) listing";
        user.ownedListings = new Listing[1];
        user.ownedListings[0] = listing;
        
        user.setPassword(password.toCharArray());
        
        DataBases.addUser(user);
        DataBases.saveUsers();
        
    }
    
        
    @AfterEach
    public void tearDown() {
        DataBases.removeUser(user);
        DataBases.saveUsers();
    }
    
    @Test
    void testPassword() {
        if (user.isPassword(password.toCharArray())) {
            System.out.println("[Password] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Password] Test failed.");
            assertFalse(true);
        }
    }
    
    /*
    @Test
    void testRenderReviews() {
        try {
            System.out.println("[RenderReviews] Converting user to JSON...");
            JSONObject jo = user.toJSON();
            user = new User(jo);
            System.out.println("[RenderReviews] Rendering reviews ...");
            user.renderReviews();
            System.out.println("[RenderReviews] Test passed.");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("[***RenderReviews] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testRenderListings() {
        try {
            System.out.println("[RenderListings] Converting user to JSON...");
            JSONObject jo = user.toJSON();
            user = new User(jo);
            System.out.println("[RenderListings] Rendering ...");
            user.renderListings();
            System.out.println("[RenderListings] Test passed.");
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("[***RenderListings] Test failed.");
            assertFalse(true);
        }
    }*/
    
    @Test
    void testJson() {
        // Test user > json > user
        JSONObject jo = user.toJSON();
        if (new User(jo).equals(user)) {
            System.out.println("[User JSON] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***User JSON] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testFavoriteListing() {
        Listing listing = new Listing();
        listing.title = "Testing Data Obj 012";
        user.favoriteListing(listing);
        if (ArrayUtils.contains(user.savedListings, listing)) {
            System.out.println("[Favorite Listing] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Favorite Listing] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testUnfavoriteListing() {
        Listing listing = new Listing();
        listing.title = "Testing Data Obj 013";
        user.savedListings = new Listing[1];
        user.savedListings[0] = listing;
        user.unfavoriteListing(listing);
        if (ArrayUtils.contains(user.savedListings, listing)) {
            System.out.println("[***Unfavorite Listing] Test failed.");
            assertFalse(true);
        } else {
            System.out.println("[Unfavorite Listing] Test passed.");
            assertTrue(true);
        }   
    }
    
    /*
    @Test
    void testAddReview() {
        Review review = new Review(user, user);
        review.title = "Testing Data Obj 014";
        user.addReview(review);
        if (ArrayUtils.contains(user.getReviews(), review)) {
            System.out.println("[Add Review] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Add Review] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testAddReviewOf() {
        Review review = new Review(user, user);
        review.title = "Testing Data Obj 015";
        user.addReviewOf(review);
        if (ArrayUtils.contains(user.getReviewsOf(), review)) {
            System.out.println("[Add ReviewOf] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Add ReviewOf] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testGetRating() {
        Review[] reviews = user.getReviewsOf();
        for (Review review : reviews) {
            user.removeReviewOf(review);
        }
        Review review = new Review(user, user);
        review.rating = 5;
        user.addReviewOf(review);
        if (user.getRating() == 5) {
            System.out.println("[Get Rating] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Get Rating] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testRemoveReview() {
        Review review = new Review(user, user);
        review.title = "Testing Data Obj 016";
        user.addReview(review);
        user.removeReview(review);
        if (!ArrayUtils.contains(user.getReviews(), review)) {
            System.out.println("[Remove Review] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Remove Review] Test failed.");
            assertFalse(true);
        }
    }
    
    @Test
    void testRemoveReviewOf() {
        Review review = new Review(user, user);
        review.title = "Testing Data Obj 017";
        user.addReviewOf(review);
        user.removeReviewOf(review);
        if (!ArrayUtils.contains(user.getReviewsOf(), review)) {
            System.out.println("[Remove ReviewOf] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Remove ReviewOf] Test failed.");
            assertFalse(true);
        }
    }*/
    
    @Test
    void testTostring() {
        if (user.toString().equals(username)) {
            System.out.println("[ToString] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***ToString] Test failed...?");
            assertFalse(true);
        }
    }
    
    @Test
    void testEquals() {
        if (user.equals(user)) {
            System.out.println("[Equals] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Equals] Test failed.");
            assertFalse(true);
        }
    }
}
