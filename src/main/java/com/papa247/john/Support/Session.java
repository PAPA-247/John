/**
* PAPA-247: Project JOHN
*
*   Session: keeps various of application state information (mainly username state)
*
* File created by cnewb on Oct 21, 2020
*/

package com.papa247.john.Support;

import com.papa247.john.User.User;

public class Session {
    public static User user;
    public static String userLoginToken; // RocketFuel (may be used later for remote API calls and when WE'RE not authenticating users ourselves)
    
    
    public Session() {}
    
    public static boolean login(User user, char[] password) {
        // For now we're not authenticating with a remote api, so we have the final say in whether they are 'logged in' or not.
        // You should NEVER make a login system CLIENT SIDE, UNLESS you can prove the code is safe/secure... yada,yada
        
        if (user.isPassword(password)) {
            Session.user = user;
            userLoginToken = "SonyPS4LevelRandomString";
            return true;
        }
        return false;
    }
    public static boolean logout() {
        user = null;
        return true;
    }
}
