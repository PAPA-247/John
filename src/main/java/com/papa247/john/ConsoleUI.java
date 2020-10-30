/**
* PAPA-247: Project JOHN
*
*   Text based interaction with JOHN
*
*/

package com.papa247.john;

import java.util.Scanner;

public class ConsoleUI {

    private static Scanner input;
    
    /**
     * Simple print
     * @param s string to print
     */
    private static <T> void print(T s) {
        System.out.println(s);
    }
    private static void print() {
        System.out.println();
    }
    
    /**
     * Call this to begin the console UI driver
     */
    public static void main() {
        input = new Scanner(System.in);
        
        // Your program now.
        // -> Note, DataBase *should* be setup.
        while (true) {
            print();
            print();
            print();
            print("******************");
            print("*   TEAM PAPA    *");
            print("******************");
            
            print("Select an action: ");
            
            // do stuff here.
            input.nextLine();
        }
    }
    
}
