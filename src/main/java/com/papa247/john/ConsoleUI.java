/**
* PAPA-247: Project JOHN
*
*   Text based interaction with JOHN
*   
*   
*   Sadly, Java doesn't support "raw console mode" rather it only support "cooked mode".
*   What does this mean? Rather than being able to read a single character and act on that, we have to wait for the "newline" character to be entered.
*   SO, we can't just listen for the key_press '1', we have to wait for '1 {enter}'
*   This makes the UI a little cumbersome to use, but it is what it is.
*
*/

package com.papa247.john;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;
import com.papa247.john.TestCode.UserTests;
import com.papa247.john.Support.ArrayUtils;

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
    
    
    private static void printL0Header() {
        print("****************************************");
        print("*====---       TEAM PAPA        ---====*");
        print("****************************************");
        
        print("Select an action: ");
    }
    
    private static void displayCommands(Command[] commands) {
        print();
        for (int i=0; i<commands.length; i++) {
            if (commands[i]!=null)
                print("\t" + commands[i].name + "): " + commands[i].description);
        }
        print();
    }
    
    private static void processCommands(String input, Command[] commands) {
        if (input.replace(" ", "").replace("\n","").replace("\r","").equals(""))
            return;
        
        for (Command command : commands) {
            if (command.isCommand(input)) {
                command.run();
                return;
            }
        }
        
        print("Unknown command \"" + input + "\"!");
    }
    
    /**
     * Call this to begin the console UI driver
     */
    public static void main() {
        // Each command deep is a 'level' This is L0, level 0 (the root)
        
        input = new Scanner(System.in);
        String command = "";
        
        Command[] L0Commands = new Command[4];
        // Nasty, yes, but it works...
        L0Commands[0] = new Command("1", "Run GUI", new CMD() {
            public void run() {
                print("testing");
            }
        });
        L0Commands[1] = new Command("2", "Login", () -> Login());
        
        L0Commands[2] = new Command("t", "Tests", () -> Tests());
        
        L0Commands[3] = new Command("e", "Exit", new CMD() {
            public void run() {
                print("Goodbye!");
                System.exit(0);
            }
        });
        
        
        printL0Header();
        displayCommands(L0Commands);
        System.out.print("? ");
        command = input.next();
        
        processCommands(command, L0Commands);
        
        // Your program now.
        // -> Note, DataBase *should* be setup.
        while (true) {
            print();
            print("========================================");
            print();
            printL0Header();
            
            
            displayCommands(L0Commands);
            System.out.print("? ");
            command = input.nextLine();
            processCommands(command, L0Commands);
        }
    }
    
    interface CMD {
        void run();
    }
    
    public static class Command {
        private String name;
        private String[] acceptedText = new String[0];
        private String description;
        private CMD method;
        
        public String getName() {
            return name;
        }
        
        public void addAccepted(String s) {
            s = s.toLowerCase().replace("\r", "").replace("\n", "");
            acceptedText = ArrayUtils.add(acceptedText, new String[acceptedText.length+1], s);
        }
        
        public Command(String name, String description, CMD method) {
            if (name == null || method == null)
                return; // Null
            
            if (name.replace(" ", "").equals(""))
                return; // Empty
            
            this.name = name.toLowerCase().replace("\r", "").replace("\n", "");
            this.description = description;
            addAccepted(name);
            this.method = method;
        }

        public boolean isCommand(String s) {
            s = s.replace("\r", "").replace("\n", ""); // Remove newlines / returns
            for (int i = 0; i<acceptedText.length; i++) {
                if (acceptedText[i].equals(s))
                    return true;
            }
            return false;
        }
        
        public void runIfCommand(String s) {
            if (isCommand(s))
                run();
        }
        
        public void run() {
            try {
                method.run();
            } catch (Exception e) {
                print("An unknown error has occured in running command " + name);
                e.printStackTrace();
            }
        }       
    }
    
    
    
    
    
    private static void Login() {
        print("Login:..==");
    }
    
    
    
    private static void Tests() {
        input = new Scanner(System.in);
        String command = "";
        
        Command[] TestCommands = new Command[4];
        // Nasty, yes, but it works...
        TestCommands[0] = new Command("1", "Test User class", () -> new CMD() {
                @Override
                public void run() {
                    UserTests userTests = new UserTests();
                    userTests.testAll();
                }
            });

        TestCommands[1] = new Command("2", "Test Listing class", () -> Login());
        
        TestCommands[2] = new Command("3", "Test Address class", () -> Tests());
        
        TestCommands[3] = new Command("b", "Main menu", () -> main());
        
        
        printL0Header();
        displayCommands(TestCommands);
        System.out.print("? ");
        command = input.next();
        
        processCommands(command, TestCommands);
        
        // Your program now.
        // -> Note, DataBase *should* be setup.
        while (true) {
            print();
            print("========================================");
            print();
            printL0Header();
            
            
            displayCommands(TestCommands);
            System.out.print("? ");
            command = input.nextLine();
            processCommands(command, TestCommands);
        }
    }
}