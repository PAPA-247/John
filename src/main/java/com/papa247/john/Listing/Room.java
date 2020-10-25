/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 23, 2020
*/

package com.papa247.john.Listing;

import com.papa247.john.Enumerators.*;

public class Room {
    public RoomType type;
    public double size;
    public String dimensions;
    public FloorType flooring;
    public Extensions[] extensions;
    public Appliances[] appliances;
    public Fixtures[] fixtures;
    public Furniture[] furniture;
    public int windows;
    public String name;
    
    public Room() {
        
    }
    
    public void reset() {
        type = null;
        size = 0;
        dimensions = null;
        flooring = null;
        extensions = null;
        appliances = null;
        fixtures = null;
        furniture = null;
        windows = 0;
        name = null;
    }
}
