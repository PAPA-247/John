/**
 * This is an example of how the database classes should look/operate
 * @author cnewb
 */

package com.papa247.john.DataBases;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import org.json.JSONArray;

import com.papa247.john.Support.ExampleEntry;

public class ExampleDB {
	private ExampleEntry[] entries;
	
	private static Path file = Paths.get("../../examples.json");
	
	public ExampleDB() {}
	
	public ExampleEntry addEntry(ExampleEntry entry) {
		ExampleEntry[] newEntries = new ExampleEntry[entries.length+1];
		
		// Doesn't already exist
		for (int i=0; i<entries.length; i++)
			if (entries[i].equals(entry))
				return entries[i];
		
		for (int i=0; i<entries.length; i++)
			newEntries[i] = entries[i];
		
		newEntries[entries.length] = entry;
		entries = newEntries;
		return entry;
	}
	
	/**
	 * Get a stored entry from the entries array using the name for lookup
	 * @param name
	 * @return
	 */
	public ExampleEntry getEntry(String name) {
		for (ExampleEntry entry : entries) {
			if (entry.name == name)
				return entry;
		}
		return null;
	}
	
	/**
	 * Deletes an entry from our entries array
	 * @param entry
	 */
	public boolean removeEntry(ExampleEntry entry) {
		boolean shift = false; // Move keys/values over?
		for (int i=0; i<entries.length; i++) {
			if (entries[i].equals(entry)) {
				shift = true; // Start shifting
				entries[i] = null; // Remove
			}
			if (shift && i<entries.length-1)
				entries[i] = entries[i+1]; // Shift everything left
		}
		return true;
	}
	
	
	public boolean load() {
		return load(false);
	}
	
	public boolean load(boolean roundTwo) {
		
		String contents = "";
		if (file.toFile().isFile()) {
			try {
				contents = contents + new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
				JSONArray ja = new JSONArray(contents);
				
				int length = ja.length(); // Add the JSON objects to the entries array
				for (int i=0; i<length; i++) {
					addEntry(new ExampleEntry(ja.getJSONObject(i))); // Get JSON object at i, make a new ExampleEntry, add that new entry
				}
				
				return true; // Done
				
			} catch (IOException e) {
		        Alert alert = new Alert(AlertType.ERROR);
		        alert.setTitle("Example.JSON");
		        alert.setHeaderText("Error loading example.json");
		        alert.setContentText("Could not read example.json!");


		        // Create expandable Exception.
		        StringWriter sw = new StringWriter();
		        PrintWriter pw = new PrintWriter(sw);
		        e.printStackTrace(pw);
		        String exceptionText = sw.toString();

		        Label label = new Label("The exception stacktrace was:");

		        TextArea textArea = new TextArea(exceptionText);
		        textArea.setEditable(false);
		        textArea.setWrapText(true);

		        textArea.setMaxWidth(Double.MAX_VALUE);
		        textArea.setMaxHeight(Double.MAX_VALUE);
		        GridPane.setVgrow(textArea, Priority.ALWAYS);
		        GridPane.setHgrow(textArea, Priority.ALWAYS);

		        GridPane expContent = new GridPane();
		        expContent.setMaxWidth(Double.MAX_VALUE);
		        expContent.add(label, 0, 0);
		        expContent.add(textArea, 0, 1);

		        // Set expandable Exception into the dialog pane.
		        alert.getDialogPane().setExpandableContent(expContent);

		        alert.showAndWait();
		        
				e.printStackTrace();
				System.exit(1);
			}
		} else {
	        Alert alert = new Alert(AlertType.CONFIRMATION);
	        alert.setTitle("Examples.JSON");
	        alert.setHeaderText("Failed to find the examples.json file!");
	        alert.setContentText("The examples.json file does not exist, would you like to create one?");
	        if (!roundTwo) {
	        	alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

		        Optional<ButtonType> result = alert.showAndWait();
		        if (result.get() == ButtonType.YES){
		        	try {
						Files.write(file, "{[]}".getBytes());
						load(true); // Go again
					} catch (IOException e) {
						Alert alert2 = new Alert(AlertType.INFORMATION);
				        alert2.setTitle("Examples.JSON");
				        alert2.setHeaderText("Failed to save the 'examples.json' file!");
				        alert2.setContentText(e.toString());
				        alert2.showAndWait();
	
						e.printStackTrace();
						System.exit(1);
					}
		        } else {
		        	//JOptionPane.showMessageDialog(null, "Examples file load failed, no file exists.", "Examples.json", JOptionPane.INFORMATION_MESSAGE);
		        }
	        }
	        alert.showAndWait();
			System.exit(1);
		}
		
		return false;
	}

	public boolean save() {
		JSONArray ja = new JSONArray();
		for (int i=0; i<entries.length; i++) {
			ja.put(entries[i].toJSON());
		}
		try {
			Files.write(file, ja.toString().getBytes());
			return true;
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Examples.JSON");
	        alert.setHeaderText("Failed to save the 'examples.json' file!");
	        alert.setContentText(e.toString());
	        alert.showAndWait();

			e.printStackTrace();
			System.exit(1);
		}
		return false;
	}
	
	public String toString() {
		String a = "";
		for (int i=0; i<entries.length; i++)
			a = a + entries[i] + "\n";
		return a;
	}
	
}
