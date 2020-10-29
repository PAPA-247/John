/**
 * Coordinate class is used to store a physical location via geographical coordinates.
 * These coordinates can then later be used to calculate distances and so on.
 * 
 * @author cnewb
 *
 */

package com.papa247.john.Support;

import org.json.JSONObject;

public class Coordinate {
	public String degrees;
	public String minutes;
	public String seconds;
	
	public Coordinate(String deg, String min, String sec) {
		degrees = deg;
		minutes = min;
		seconds = sec;
	}

    public Coordinate(JSONObject jo) {
        if (!jo.has("degrees") || !jo.has("minutes") || !jo.has("seconds"))
            return;
        
        degrees = jo.getString("degrees");
        minutes = jo.getString("minutes");
        seconds = jo.getString("seconds");
    }
    
    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        jo.put("degrees", degrees);
        jo.put("minutes", minutes);
        jo.put("seconds", seconds);
        return jo;
    }
}
