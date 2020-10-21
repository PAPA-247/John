/**
* PAPA-247: Project JOHN
*
*   Store test methods here
*
*/

package com.papa247.john;

import java.util.Scanner;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Enumerators.AccountType;
//import com.papa247.john.*;
import com.papa247.john.Support.*;
import com.papa247.john.User.User;

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
}
