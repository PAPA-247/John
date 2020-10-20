/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.UIComponents;


import com.papa247.john.TestCode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class DebugWindowController {
    @FXML
    private Button btnCheckExample;

    @FXML
    void checkUser(ActionEvent event) {
        System.out.println("'Check UserDB' clicked... running code...");
        TestCode.userDB();
    }
}

