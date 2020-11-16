/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 23, 2020
*/

package com.papa247.john.Listing;

import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.Enumerators.*;
import com.papa247.john.Support.ArrayUtils;
import com.papa247.john.Support.Exceptions;

public class Room {
    public RoomType type = RoomType.NONE;   // Type of room
    public double size = 0.0;               // feet^3 of room
    public int windows = 0;                 // Windows count
    public String name = "";                // Room name (this allows us to refer to the room via the lease (typically "A", "B", etc))
    
    public FloorType flooring = FloorType.NONE;         // Type of flooring in this room
    public Extensions[] extensions = new Extensions[0]; // Closets
    public Appliances[] appliances = new Appliances[0]; // ...
    public Fixtures[] fixtures =  new Fixtures[0];      // Ceiling lights, fans, etc
    public Furniture[] furniture = new Furniture[0];    // Desk, chair, bed, etc
    
    
    public Room(JSONObject jo) {
        if (!jo.has("type") || !jo.has("flooring"))
            throw new Exceptions.InvalidJSON("Invalid JSON data passed.");

        if (jo.has("type"))
            type = RoomType.fromString(jo.getString("type"));
        if (jo.has("size"))
            size = jo.getDouble("size");
        if (jo.has("windows"))
            windows = jo.getInt("windows");
        if (jo.has("name"))
            name = jo.getString("name");
        if (jo.has("flooring"))
            flooring = FloorType.fromString(jo.getString("flooring"));


        Extensions[] extensions = new Extensions[0];
        if (jo.has("extensions"))
            jo.getJSONArray("extensions").forEach(e -> {
                Extensions extension = Extensions.fromString((String) e);
                this.extensions = ArrayUtils.add(extensions, new Extensions[extensions.length+1], extension);
            });

        Appliances[] appliances = new Appliances[0];
        if (jo.has("appliances"))
            jo.getJSONArray("appliances").forEach(e -> {
                Appliances appliance = Appliances.fromString((String) e);
                this.appliances = ArrayUtils.add(appliances, new Appliances[appliances.length+1], appliance);
            });

        Fixtures[] fixtures = new Fixtures[0];
        if (jo.has("fixtures"))
            jo.getJSONArray("fixtures").forEach(e -> {
                Fixtures fixture = Fixtures.fromString((String) e);
                this.fixtures = ArrayUtils.add(fixtures, new Fixtures[fixtures.length+1], fixture);
            });

        Furniture[] furniture = new Furniture[0];
        if (jo.has("furniture"))
            jo.getJSONArray("furniture").forEach(e -> {
                Furniture fur = Furniture.fromString((String) e);
                this.furniture = ArrayUtils.add(furniture, new Furniture[furniture.length+1], fur);
            });
    }
    
    public Room() {
        // Blank
    }

    public JSONObject toJSON() {
        JSONObject jo = new JSONObject();
        
        jo.put("name", name);
        jo.put("type", type.toString());
        jo.put("size", size);
        jo.put("windows", windows);
        jo.put("flooring", flooring.toString());

        JSONArray ja = new JSONArray();
        for (Extensions a : extensions)
            ja.put(a.toString());
        jo.put("extensions", ja);
        
        ja = new JSONArray();
        for (Appliances a : appliances)
            ja.put(a.toString());
        jo.put("appliances", ja);
        
        ja = new JSONArray();
        for (Fixtures a : fixtures)
            ja.put(a.toString());
        jo.put("fixtures", ja);
        
        ja = new JSONArray();
        for (Furniture a : furniture)
            ja.put(a.toString());
        jo.put("furniture", ja);
        
        return jo;
    }
    
    public void reset() {
        type = RoomType.NONE;   // Type of room
        size = 0.0;               // feet^3 of room
        windows = 0;                 // Windows count
        name = "";                // Room name (this allows us to refer to the room via the lease (typically "A", "B", etc))
        
        flooring = FloorType.NONE;         // Type of flooring in this room
        extensions = new Extensions[0]; // Closets
        appliances = new Appliances[0]; // ...
        fixtures =  new Fixtures[0];      // Ceiling lights, fans, etc
        furniture = new Furniture[0];    // Desk, chair, bed, etc
    }
}
