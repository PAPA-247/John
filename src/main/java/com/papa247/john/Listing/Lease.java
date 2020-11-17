/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.Listing;

import java.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.UsernameLookupType;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Exceptions;
import com.papa247.john.UIComponents.AlertWindows;
import com.papa247.john.User.User;

public class Lease {    
    public String title = "";
    public String contents = "";
    public String documentURL = "";
    public double rentLength = 0.0;
    
    public Signer[] signers = new Signer[0];
    
    // Other stuff
    public Listing listing;

    public Lease(Listing parent) {
        listing = parent;
    }
    
    public Lease(JSONObject lease, Listing parent) {
        // decode here... :))))
        listing = parent;
        if (!lease.has("title") || !lease.has("contents") || !lease.has("documentURL") || !lease.has("rentLength"))
            return;
        
        title = lease.getString("title");
        contents = lease.getString("contents");
        documentURL = lease.getString("documentURL");
        rentLength = lease.getDouble("rentLength");
        
        signers = new Signer[0];
        if (lease.has("signers")) {
            lease.getJSONArray("signers").forEach(e -> {
                JSONObject jo = (JSONObject) e;
                signers = ArrayUtils.add(signers, new Signer[signers.length+1], new Signer(jo));
            });
        }
        
    }
    
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();   // 

        jo.put("title", title);
        jo.put("contents", contents);
        jo.put("documentURL", documentURL);
        jo.put("rentLength", rentLength);
        
        JSONArray ja = new JSONArray();
        for (Signer signer : signers) {
            ja.put(signer.toJSON());
        }
        jo.put("signers", ja);
        
        return jo;
    }
    
    public void loadTemplate(String templateLocation) {
        
    }
    
    public Signer addSigner(User user) {
        Signer signer = new Signer(user);
        signers = ArrayUtils.add(signers, new Signer[signers.length+1], signer); 
        return signer;
    }
    public void removeSigner(User user) {
        Signer obj = new Signer();
        for (Signer signer : signers) {
            if (signer.user.equals(user)) {
                obj = signer;
            }
        }
        signers = ArrayUtils.remove(signers, new Signer[signers.length-1], obj);
    }
    
    public boolean getSigned(boolean partial) {
        boolean signed = false;
        for (Signer signer : signers) {
            if (signer.signed)
                signed = true;
            if (!signer.signed && !partial) {
                signed = false;
                break;
            }
        }
        return signed;
    }
    public boolean getSigned() {
        return getSigned(false);
    }
    
    public boolean sign(User user) {
        boolean found = false;
        boolean locked = false; // Block new signers?
        
        for (Signer signer : signers) {
            if (signer == null)
                continue;
            if (signer.user == null)
                continue;
            if (signer.user.equals(user)) {
                signer.sign();
                found = true;
                break;
            }
            if (signer.signed) {
                locked = true;
                break;
            }
        }
        
        if (!found && !locked) {
            // add as signer
            addSigner(user).sign();
        } else if(locked) {
            AlertWindows.showAlert("Failed to sign lease", "You cannot sign a lease already assigned to another user!",
                    "This lease has been signed by another user, and therefore locked. Ask them to add you as a co-signer.");
            return false;
        }
        
        try {
            DataBases.save();
            return true;
        } catch (Exceptions.SaveFailed e) {
            return false;
        }
    }
    
    
    @Override
    public String toString() {
        return this.title;
    }
}
