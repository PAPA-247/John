/**
* PAPA-247: Project JOHN
*
*   Store test methods here
*
* File created by cnewb on Oct 17, 2020
*/

package com.papa247.john;

//import com.papa247.john.*;
import com.papa247.john.OldDataBases.*;
import com.papa247.john.Support.*;

public class TestCode {
    
    public static void testExampleDB() {
        /*
         * Load DB,
         * Add entry x2,
         * Save DB,
         * wait
         * Load DB,
         * Remove entry,
         * Save DB
         */
        
        ExampleDB exampleDB = new ExampleDB();
        
        System.out.print("Loading data base (from file): ");
        System.out.println(exampleDB.load());
        
        System.out.print("Adding entry Bobby: ");
        ExampleEntry entry = new ExampleEntry("Bobby","Bobby is a dude, I think.");
        System.out.println(entry.equals(exampleDB.addEntry(entry))? "true" : "false");
        System.out.print("Adding entry Tommy: ");
        ExampleEntry entry2 = new ExampleEntry("Tommy","Bobby's brother, actually.");
        System.out.println(entry2.equals(exampleDB.addEntry(entry2))? "true" : "false");
        
        System.out.println("Entries: \n" + exampleDB.toString());
        
        System.out.print("Saving data base (to file) ");
        System.out.println(exampleDB.save());
        
        
        
        System.out.print("...wait...");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {} // okay
        
        
        
        System.out.print("Loading data base (from file): ");
        System.out.println(exampleDB.load());
        
        ExampleEntry e = exampleDB.getEntry("Bobby");
        System.out.println("Bobby?: " + e.name);
        
        System.out.print("Remove entry Bobby: ");
        System.out.println(exampleDB.removeEntry(exampleDB.getEntry("Bobby"))? "true" : "false");
        
        System.out.println("Entries: \n" + exampleDB.toString());
        
        System.out.print("Saving data base (to file) ");
        System.out.println(exampleDB.save());
    }
}
