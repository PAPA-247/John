/**
 * This is used for the ExampleDB
 */

package com.papa247.john.Support;

import org.json.JSONObject;

public class ExampleEntry {
	public String name;
	public String description;
	
	/*
	 * Load a new ExampleEntry from JSON
	 */
	public ExampleEntry(JSONObject json) {
		this.name = json.getString("name"); // Set 'name' to the whatever the key 'name' is in the JSON object
		this.description = json.getString("description");
	}
	
	public ExampleEntry(String nam, String des) {
		this.name = nam;
		this.description = des;
	}

	public boolean equals(ExampleEntry entry) {
	    if (entry == null)
	        return false;
		if (entry.name.equals(this.name) && entry.description.equals(this.description))
			return true;
		return false;
	}
	
	/*
	 * Convert object to JSON
	 */
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		jo.put("name", name);
		jo.put("description", description);
		return jo;
	}
}
