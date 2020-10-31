/**
* PAPA-247: Project JOHN
*
*
* File created by cnewb on Oct 30, 2020
*/

package com.papa247.john.UIComponents;

import com.jfoenix.controls.JFXButton;
import com.papa247.john.DataBases;
import com.papa247.john.DataBases.SearchData;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.papa247.john.Enumerators.Amminities;
import com.papa247.john.Support.Range;
import com.papa247.john.Support.Session;
import com.papa247.john.Support.CallBack;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class SearchPannelController {

    @FXML
    private AnchorPane apPrice;

    @FXML
    private JFXTextField txtPriceLower;

    @FXML
    private JFXTextField txtPriceUpper;

    @FXML
    private AnchorPane apBedrooms;

    @FXML
    private JFXTextField txtBedroomsLower;

    @FXML
    private JFXTextField txtBedroomsUpper;

    @FXML
    private AnchorPane apMustInclude;

    @FXML
    private JFXComboBox<String> comboMustInclude;

    @FXML
    private JFXChipView<String> chipMustInclude;

    @FXML
    private AnchorPane apMustExclude;

    @FXML
    private JFXComboBox<String> comboMustExclude;

    @FXML
    private JFXChipView<String> chipMustExclude;

    @FXML
    private AnchorPane apLeaseLength;

    @FXML
    private JFXTextField txtLengthLower;

    @FXML
    private JFXTextField txtLengthUpper;

    @FXML
    private JFXButton btnApply;
    
    // TODO: Compare to companion field
    // Make it so the upper limit can't be lower than the lower limit and vice-versa
    
    public void setListener(JFXTextField txt) { // Set to digits only. Replaces none digits with "".
        txt.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,  String newValue) {
                if (!newValue.matches("\\d*")) {
                    txt.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });        
    }
    
    @FXML
    void initialize() {
        
        for (Amminities a : Amminities.values()) {
            comboMustInclude.getItems().add(a.toString());
            comboMustExclude.getItems().add(a.toString());
            chipMustInclude.getSuggestions().add(a.toString());
            chipMustExclude.getSuggestions().add(a.toString());
        }
        
        comboMustInclude.setOnAction(e -> {
            chipMustInclude.getChips().add(comboMustInclude.getValue().toString());
            chipMustInclude.autosize();
        });
        
        comboMustExclude.setOnAction(e -> {
            chipMustExclude.getChips().add(comboMustExclude.getValue().toString());
            chipMustExclude.autosize(); // Move this to when the chip box gets something
        });
        
    }

    public void setListeners() {
        setListener(txtPriceLower);
        setListener(txtPriceUpper); // Cursed, wont let me reference it...
        setListener(txtBedroomsLower);
        setListener(txtBedroomsUpper);
        setListener(txtLengthLower);
        setListener(txtLengthUpper);
    }
    
    @FXML
    void adjustSearch(ActionEvent event) {
//        updateMethod.run(getSearchData()); // a bit laggy, do in seperate thread?
    }

    @FXML
    void btnApply(ActionEvent event) {
        Session.updateListings.run(getSearchData());
    }
    
    
    private int getNumber(JFXTextField txt) {
        return Integer.parseInt(txt.getText());
    }

    public SearchData getSearchData() {
        SearchData searchData = new SearchData();
        if (!txtBedroomsLower.getText().equals("") && !txtBedroomsUpper.getText().equals(""))
            searchData.bedroomCount = new Range<Integer>(getNumber(txtBedroomsLower),getNumber(txtBedroomsUpper));
        if (!txtPriceLower.getText().equals("") && !txtPriceUpper.getText().equals(""))
            searchData.priceRange = new Range<Integer>(getNumber(txtPriceUpper), getNumber(txtPriceUpper));
        if (!txtLengthLower.getText().equals("") && !txtLengthUpper.getText().equals("")) // Not empty
            searchData.rentLength = new Range<Integer>(getNumber(txtLengthLower), getNumber(txtLengthUpper));
        
        for (String a : chipMustInclude.getChips()) {
            searchData.amminities.put(a, "2");
        }
        for (String a : chipMustExclude.getChips()) {
            searchData.amminities.put(a, "0");
        }
        
        return searchData;
    }

}