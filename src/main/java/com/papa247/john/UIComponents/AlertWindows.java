/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 19, 2020
*/

package com.papa247.john.UIComponents;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class AlertWindows {

    private static Alert createMessageBox(String title, String header, String content, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }
    private static Alert createMessageBox(String title, String header, String content) {
        return createMessageBox(title, header, content, AlertType.ERROR);
    }
    
    // Public facing
    
    @SuppressWarnings("exports") // lol cool don't care
    public static Optional<ButtonType> showAlert(String title, String header, String content) {
        Alert alert = createMessageBox(title, header, content);
        return alert.showAndWait();
    }
    
    @SuppressWarnings("exports")
    public static Optional<ButtonType> showPrompt(String title, String header, String content) {
        Alert alert = createMessageBox(title, header, content, AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);
        return alert.showAndWait();
    }
    
    @SuppressWarnings("exports")
    public static Optional<ButtonType> showMessage(String title, String header, String content) {
        Alert alert = createMessageBox(title, header, content, AlertType.INFORMATION);
        alert.getButtonTypes().setAll(ButtonType.OK);
        return alert.showAndWait();
    }
    
    
    @SuppressWarnings("exports")
    public static Optional<ButtonType> showException(String title, String header, String content, Exception e) {
        Alert alert = createMessageBox(title, header, content);

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

        return alert.showAndWait();        
    }
}
