/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 31, 2020
*/

package com.papa247.john.Listing;

import java.time.LocalDateTime;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.User.User;

public class Signer {
    public User user;
    public boolean signed = false;
    public String signedOn = "na";
    
    public Signer() {
    }
    
    public Signer(User user) {
        this.user = user;
    }
    
    public Signer(JSONObject jo) {
        user = DataBases.getUser(jo.getString("user"), UsernameLookupType.username);
        if (jo.has("signed"))
            signed = jo.getBoolean("signed");
        if (jo.has("signedOn"))
            signedOn = jo.getString("signedOn"); // maybe ...?
    }
    
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("user", user.username);
        jo.put("signed", signed);
        jo.put("signedOn", signedOn);
        
        return jo;
    }
    
    @Override
    public boolean equals(Object signr) {
        Signer signer = (Signer) signr;
        if (signer.user.equals(this.user) && signer.signed == this.signed)
            return true;
        return false;
    }
    
    public void sign() {
        signedOn = LocalDateTime.now().toString(); // UTC..??
        signed = true;
    }
}