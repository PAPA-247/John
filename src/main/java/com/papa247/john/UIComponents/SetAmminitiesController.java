/**
* PAPA-247: Project JOHN
*
*/

package com.papa247.john.UIComponents;

import java.util.ArrayList;
import java.util.HashMap;
import com.jfoenix.controls.JFXCheckBox;
import com.papa247.john.Enumerators.Amminities;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class SetAmminitiesController {
    
    @FXML
    private VBox vbox;
    
    public HashMap<Amminities, JFXCheckBox> entries = new HashMap<>();;
    
    @FXML
    void initialize() {
        for (Amminities a : Amminities.values()) {
            JFXCheckBox chk = new JFXCheckBox();
            chk.setText(a.toString());
            chk.setSelected(false);
            entries.put(a, chk);
        }
    }
    
    public void setUp(Amminities[] amminities) {
        for (Amminities a : amminities) {
            entries.get(a).setSelected(true);
        }
    }
    
    public Amminities[] getChecked() {
        ArrayList<Amminities> checked = new ArrayList<Amminities>(0);
        entries.forEach((A, chk) -> {
            if (chk.isSelected())
                checked.add(A);
        });
        return (Amminities[]) checked.toArray();
    }
    
}
