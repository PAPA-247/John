/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 16, 2020
*/

package com.papa247.john.User;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.Enumerators.AccountType;
import com.papa247.john.Enumerators.TargetType;
import com.papa247.john.Listing.Address;
import com.papa247.john.Listing.Listing;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Exceptions;
import com.papa247.john.Support.Review;

public class User {
    public String username;            // ...
	public String firstName;           // ...
	public String middleName;          // ...
	public String lastName;            // ...
	public String phoneNumber;         // ...
	public String emailAddress;        // ...
	public String studentID;		   // ...
	public String profilePhotoURL;     // Profile photo (hosted on projects.cnewb.co/PAPA247/John/Avatars/ most likely)
	public AccountType accountType;    // Owner, Student, Admin, etc
	public Listing[] savedListings = new Listing[0];   // Favorites
	private Review[] reviews = new Review[0];          // Saved with User data
	private Review[] reviewsOf = new Review[0];        // These are the reviews WE'RE a target of (do not save)
	public Address[] ownedAddresses = new Address[0];  // Owner
	public Listing[] ownedListings = new Listing[0];   // Owner

	private JSONArray _reviews;        // We have to renders these AFTER loading the other data
	private JSONArray _savedListings;  // "
	private JSONArray _ownedAddresses; // "
	private JSONArray _ownedListings;  // "
	
	// Password stuff (sensitive stuff)
	/**
	 * @see <a href="http://stackoverflow.com/a/2861125/3474">StackOverflow</a>
	 */
	private String token;              // The saved password (in a way, see layout)
	private final SecureRandom random; // Secure random number generator (or is it..????)
	private static final Pattern layout = Pattern.compile("\\$USR\\$(\\d\\d?)\\$(.{43})"); // How passwords are stored
	// $USR$<cost>$
	private final int cost = 16;       // The 'cost' of encryption. (Iterations)
	private static final int SIZE = 128;
	

	// Password methods
    private String hashPassword(char[] pwd) {
        byte[] salt = new byte[SIZE / 8];
        random.nextBytes(salt);
        
        byte[] dk = pbkdf2(pwd, salt, 1 << cost);
        byte[] hash = new byte[salt.length + dk.length];
        System.arraycopy(salt, 0, hash, 0, salt.length);
        System.arraycopy(dk, 0, hash, salt.length, dk.length);
        Base64.Encoder enc = Base64.getUrlEncoder().withoutPadding();
        
        
        // clear
        for (int i=0; i<pwd.length; i++)
            pwd[i] = 0;
        
        return "$USR$" + cost + '$' + enc.encodeToString(hash);
    }

    private static byte[] pbkdf2(char[] password, byte[] salt, int iterations) {
        KeySpec spec = new PBEKeySpec(password, salt, iterations, SIZE);
      try {
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        return f.generateSecret(spec).getEncoded();
      }
      catch (NoSuchAlgorithmException ex) {
        throw new IllegalStateException("Missing algorithm PBKDF2WithHmacSHA1", ex);
      }
      catch (InvalidKeySpecException ex) {
        throw new IllegalStateException("Invalid SecretKeyFactory", ex);
      }
    }
    private static int iterations(int cost) {
      if ((cost < 0) || (cost > 30))
        throw new IllegalArgumentException("cost: " + cost);
      return 1 << cost;
    }
    
    // Quickie
    private boolean save() {
        return DataBases.saveUsers();
    }
    
    
    // Public API
    /**
     * Check a user's password
     * @param pwd   The character map of the user's password
     * @return if the password matches what is on file
     */
	public boolean isPassword(char[] pwd) {
	    Matcher m = layout.matcher(token);
	    if (!m.matches())
	      throw new IllegalArgumentException("Invalid token format");
	    int iterations = iterations(Integer.parseInt(m.group(1)));
	    byte[] hash = Base64.getUrlDecoder().decode(m.group(2));
	    byte[] salt = Arrays.copyOfRange(hash, 0, SIZE / 8);
	    byte[] check = pbkdf2(pwd, salt, iterations);
	    int zero = 0;
	    for (int idx = 0; idx < check.length; ++idx)
	      zero |= hash[salt.length + idx] ^ check[idx];
	    return zero == 0;
	}
	
	/**
	 * Sets a user's password to the provided character map
	 * @param pwd  The user's new password
	 * @return if successfully saved.
	 */
	public boolean setPassword(char[] pwd) {
		if (pwd.length<4) {
		    throw new Exceptions.PasswordComplexityNotMet("Provided password was too sort.");
		}
		
		System.out.println("[User] Changing " + username + "'s password");
		
		token = hashPassword(pwd);
		return save(); // Save changes
	}
	
	
	
	
	public User() {
        this.random = new SecureRandom();
    }
	/**
	 * Convert JSONObject to a user object
	 * @param jo save JSON data for the user.
	 */
    public User(JSONObject jo) {
        this.random = new SecureRandom();

        if (!jo.has("username") || !jo.has("token"))
        	throw new Exceptions.InvalidUserJSON("No a valid user JSONObject.");
        	
        if (jo.has("username"))
        	username = jo.getString("username");

        if (jo.has("firstName"))
			firstName = jo.getString("firstName");
		if (jo.has("middleName"))
		    middleName = jo.getString("middleName");
		if (jo.has("lastName"))
			lastName = jo.getString("lastName");
		if (jo.has("phoneNumber"))
			phoneNumber = jo.getString("phoneNumber");
		if (jo.has("emailAddress"))
			emailAddress = jo.getString("emailAddress");
		if (jo.has("studentID"))
			studentID = jo.getString("studentID");
		if (jo.has("accountType"))
			accountType = AccountType.fromNum(jo.getInt("accountType"));

		
		savedListings = new Listing[0];
		if (jo.has("savedListings"))
            _savedListings = jo.getJSONArray("savedListings");
		
		ownedListings = new Listing[0];
		if (jo.has("ownedListings"))
            _ownedListings = jo.getJSONArray("ownedListings");
		
		ownedAddresses = new Address[0];
		if (jo.has("ownedAddresses"))
            _ownedAddresses = jo.getJSONArray("ownedAddresses");
    
		
        reviews = new Review[0];
        if (jo.has("reviews"))
	        _reviews = jo.getJSONArray("reviews");
    
        if (jo.has("token"))
	        token = jo.getString("token");
    }
    
    /**
     * JSONObject reviews -> Review reviews.
     * We do this here because we need to call this AFTER loading the listings/addresses and other users to make sure those objects are setup correctly.
     */
    public void renderReviews() {
        if (reviews.length==0)
            _reviews.forEach(reviewObj -> {
                Review review = new Review((JSONObject) reviewObj);
                reviews = ArrayUtils.add(reviews, new Review[reviews.length+1], review);
            });
        
        for (Review review : reviews) {
            // Add the review object to the target object :)
            if (review.targetType == TargetType.User)
                review.target.user.addReviewOf(review);
            else
                review.target.address.addReviewOf(review);
        }
    }
    /**
     * Replaces the listing IDs with actual listing objects
     */
    public void renderListings() {
        if (savedListings.length==0)
            if (_savedListings!=null)
                _savedListings.forEach(lID -> {
                    try {
                        Listing listing = DataBases.getListing((Integer) lID);
                        savedListings = ArrayUtils.add(savedListings, new Listing[savedListings.length+1], listing);
                    } catch (Exception e) {
                        
                    }
                });
        
        if (ownedListings.length==0)
            if (_ownedListings!=null)
                _ownedListings.forEach(lID -> {
                    try {
                        Listing listing = DataBases.getListing((Integer) lID);
                        ownedListings = ArrayUtils.add(ownedListings, new Listing[ownedListings.length+1], listing);
                    } catch (Exception e) {
                        
                    }
                });
        
        if (ownedAddresses.length==0)
            if (_ownedAddresses!=null)
                _ownedAddresses.forEach(lID -> {
                    try {
                        Address address = DataBases.getAddress((Integer) lID);
                        ownedAddresses = ArrayUtils.add(ownedAddresses, new Address[ownedAddresses.length+1], address);
                    } catch (Exception e) {
                    
                    }
                });
    }
    
    
    /**
     * Returns a JSONObject of this class
     * @return
     */
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        
        jo.put("username", username);
		jo.put("firstName", firstName);
		jo.put("middleName", middleName);
		jo.put("lastName", lastName);
		jo.put("phoneNumber", phoneNumber);
		jo.put("emailAddress", emailAddress);
		jo.put("studentID", studentID);
		jo.put("accountType", accountType.toNum());
		
		
		JSONArray ja = new JSONArray();
		for (Listing listing : savedListings)
		    ja.put(listing.id);
		jo.put("savedListings", ja);
		
		ja = new JSONArray();
        for (Listing listing : ownedListings)
            ja.put(listing.id);
        jo.put("ownedListings", ja);
		
        ja = new JSONArray();
        for (Address address : ownedAddresses)
            ja.put(address.id);
        jo.put("ownedAddresses", ja);
        
        ja = new JSONArray();
        for (Review review : reviews)
            ja.put(review.toJSON());
        jo.put("reviews", ja);
        
        
        jo.put("token", token);
        
        return jo;
    }
	
	
	
	/**
	 * Favorites a listing
	 * @param listing The listing to add to favorites (savedListings)
	 * @return save successful
	 */
	public boolean favoriteListing(Listing listing) {		
		savedListings = ArrayUtils.add(savedListings, new Listing[savedListings.length+1], listing);
	    
	    return save();	
	}
	/**
	 * Removes a listing from favorites
	 * @param listing The listing to remove from favorites (savedListings)
	 * @return save successful
	 */
	public boolean unfavoriteListing(Listing listing) {
	    if (savedListings.length==0)
	        return true;
	    
	    savedListings = ArrayUtils.remove(savedListings, new Listing[savedListings.length+1], listing);
                
        return save();
	}

	
	public Review[] getReviews() {
		return reviews;
	}
	public Review[] getReviewsOf() {
	    return reviewsOf;
	}
	public boolean addReview(Review review) {
	    if (review.equals(new Review()))
            return true; // Blank review
	    
	    reviews = ArrayUtils.add(reviews, new Review[reviews.length+1], review);
        
        return save();
	}
	public boolean addReviewOf(Review review) {
	    if (review.equals(new Review()))
            return true; // Blank review
        
        reviewsOf = ArrayUtils.add(reviewsOf, new Review[reviewsOf.length+1], review);
        
        return save();
	}
	
	
	public boolean removeReview(Review review) {
	    if (reviews.length==0)
	        return true;
		reviews = ArrayUtils.remove(reviews, new Review[reviews.length-1], review);
		return save();
	}
	public boolean removeReviewOf(Review review) {
	    if (reviewsOf.length==0)
            return true;
	    reviewsOf = ArrayUtils.remove(reviewsOf, new Review[reviewsOf.length-1], review);
        return save();
	}
	
	public double getRating() {
	    if (reviewsOf.length==0)
	        return 5;
	    
		double rating = reviewsOf[0].rating; // A running average
		if (reviewsOf.length>1) {
		    for (int i=1; i<reviewsOf.length; i++) {
		        if (reviewsOf[i].targetType == TargetType.User) 
		            if (reviewsOf[i].target.user.equals(this))
		                rating = (rating + reviewsOf[i].rating)/2; // Add rating to avg
		    }
		    
		    return rating;
		} else {
		    return reviewsOf[0].rating;
		}
	}
	public boolean delete() {
		DataBases.removeUser(this);
		return save();
	}
	
	@Override
	public String toString() {
	    return this.username;
	}
	
	@Override
	public boolean equals(Object user) {
	    User usr = (User) user;
	    if (this==null || usr == null)
	        return false;
	    
	    if (usr.username!=null && this.username!=null)
	        if (usr.username.equalsIgnoreCase(this.username))
	            return true;
	    return false;
	}
}
