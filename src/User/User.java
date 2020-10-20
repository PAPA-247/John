import java.util.ArrayList;

import javax.tools.JavaFileObject;


public class User {
public String usename;
public String firstName;
public String middleName;
public String phoneNumber;
public String emailAddress;
public ArrayList<Listings> SavedListings;
public AccountType accountType;
public ArrayList<Review> review;
public Address[] addresses;
public Listings[] ownedListings;
private String password;

	public User() {
		// TODO Auto-generated constructor stub
	}
	public boolean isPassword(String pwd) {
		if(pwd.equals(password)) {
			return true;
		}else {
			return false;
		}
	}
	public void setPassword(String pwd) {
		if(pwd.length()>12) {
			password = pwd;
		}
	}
	public boolean saveListing(Listings listing) {
		return SavedListings.add(listing);
	}
	public boolean saveListing(int listingID) {
		return false;
		
	}
	public boolean unsaveListing(Listings listing) {
		return false;
		
	}
	public boolean unsaveListing(int listingID) {
		return false;
		
	}
	public JavaFileObject toJSON() {
		return null;
		
	}
	public ArrayList<Review> getReviews(){
		return review;
	}
	public void  addReview(Review rvw) {
		review.add(rvw);
	}
	public void removeReview(Review rvw) {
		int index;
		if (review.indexOf(rvw) != -1) {
			index = review.indexOf(rvw);
			review.remove(index);
		}
	}
	public double getRating() {
		return 0;
		//return rating;
	}
	public void delete() {
		//userDb.deleteUser(this);
	}
}
