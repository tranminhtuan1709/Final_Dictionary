package com.example.final_dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController implements Initializable {

    @FXML
    private ChoiceBox<String> translate;
    @FXML
    private ChoiceBox<String> meaning;
    @FXML
    private Button transfer;
    @FXML
    private Button translateButton;
    @FXML
    private TextArea inputfieldtranslate;
//    @FXML
//    private TextField showmeaningtrans; // lỗi ở đây
    @FXML
    private TextArea showmeaning;

    private String[] items = {"English", "Vietnamese"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        translate.setValue("English");
        translate.setTooltip(new Tooltip("Select the input language"));
        meaning.setTooltip(new Tooltip("Select the output language"));
        meaning.setValue("Vietnamese");
        translate.getItems().addAll(items);
        meaning.getItems().addAll(items);
    }

    public void handleTransferButton(ActionEvent e) {
        String tmp = translate.getValue();
        translate.setValue(meaning.getValue());
        meaning.setValue(tmp);
    }

    public void handleTranslateButton(ActionEvent e) {
        String text = inputfieldtranslate.getText();
        //handle translate here
        showmeaning.setText(text);
    }
}
