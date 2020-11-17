/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.json.JSONArray;
import org.json.JSONObject;
import com.papa247.john.Enumerators.Appliances;
import com.papa247.john.Enumerators.Extensions;
import com.papa247.john.Enumerators.Fixtures;
import com.papa247.john.Enumerators.FloorType;
import com.papa247.john.Enumerators.Furniture;
import com.papa247.john.Enumerators.RoomType;
import com.papa247.john.Listing.Room;
import com.papa247.john.Support.ArrayUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Conversion of Kyle's test
class RoomTests {
    private Room room; // What we use to test with
    
    // These are the values we use for this room
    private RoomType type = RoomType.GUEST;   // Type of room
    private double size = 100.0;               // feet^3 of room
    private int windows = 2;                 // Windows count
    private String name = "Room Test Class";                // Room name (this allows us to refer to the room via the lease (typically "A", "B", etc))
    
    private FloorType flooring = FloorType.CARPET;         // Type of flooring in this room
    public Extensions[] extensions = new Extensions[0]; // Closets
    public Appliances[] appliances = new Appliances[0]; // ...
    public Fixtures[] fixtures =  new Fixtures[0];      // Ceiling lights, fans, etc
    public Furniture[] furniture = new Furniture[0];    // Desk, chair, bed, etc
    
    
    @BeforeEach
    void setup() {
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
        
        
        room = new Room(jo);
    }
    
    @Test
    void isReset() {
      Room testRoom = new Room();
      testRoom.type = RoomType.BEDROOM;
      testRoom.size = 5.0;
      testRoom.windows = 2;
      testRoom.name = "Test Room";
      testRoom.flooring = FloorType.CARPET;
      testRoom.extensions = ArrayUtils.add(extensions, new Extensions[extensions.length+1], Extensions.CLOSET);
      testRoom.appliances = ArrayUtils.add(appliances, new Appliances[appliances.length+1], Appliances.AC);
      testRoom.fixtures = ArrayUtils.add(fixtures, new Fixtures[fixtures.length+1], Fixtures.BATHTUB);
      testRoom.furniture = ArrayUtils.add(furniture, new Furniture[furniture.length+1], Furniture.CHAIR);
      
      testRoom.reset();
      if(testRoom.type == RoomType.NONE ||  testRoom.size == 0.0 || testRoom.windows == 0 || testRoom.name == ""|| testRoom.flooring == FloorType.NONE 
              || testRoom.extensions == new Extensions[0] || testRoom.appliances == new Appliances[0] || testRoom.fixtures ==  new Fixtures[0] || testRoom.furniture == new Furniture[0]) {
          System.out.println("[Reset] Test passed.");
          assertTrue(true);
      } else {
          System.out.println("[***Reset] Test failed.");
          assertFalse(true);
      }
    }
    
    @Test
    void json() {
        // Test Room > json > room
        JSONObject jo = room.toJSON();
        if (new Room(jo).equals(room)) {
            System.out.println("[Room JSON] Test passed.");
            assertTrue(true);
        } else {
            System.out.println("[***Room JSON] Test failed.");
            assertFalse(true);
        }
    }

}
