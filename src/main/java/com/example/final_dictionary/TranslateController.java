package com.example.final_dictionary;

import Speech.TextToSpeechOnline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import API.GoogleAPI;
import java.io.IOException;
import java.net.URISyntaxException;
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

    @FXML
    private TextArea showmeaning;

    private final String[] items = {"   English", "   Vietnamese"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
    }

    @FXML
    private void setUp() {
        translate.setValue("   English");
        translate.setTooltip(new Tooltip("Select the input language"));
        meaning.setTooltip(new Tooltip("Select the output language"));
        meaning.setValue("   Vietnamese");
        translate.getItems().addAll(items);
        meaning.getItems().addAll(items);
    }

    @FXML
    public void handleTransferButton() {
        transfer.setOnMouseClicked(mouseEvent -> {
            String tmp = translate.getValue();
            translate.setValue(meaning.getValue());
            meaning.setValue(tmp);

            if(showmeaning.getText().isEmpty()) {
                inputfieldtranslate.clear();
            }

            if(inputfieldtranslate.getText().isEmpty()) {
                showmeaning.clear();
            }

            if(!showmeaning.getText().equals(inputfieldtranslate.getText())) {
                String tmp1 = inputfieldtranslate.getText();
                inputfieldtranslate.setText(showmeaning.getText());
                showmeaning.setText(tmp1);
            } else {
                showmeaning.clear();
            }
        });
    }

    @FXML
    public void handleTranslateButton() {
        translateButton.setOnMouseClicked(mouseEvent -> {
            String input = inputfieldtranslate.getText();
            String from = translate.getValue();
            String to = meaning.getValue();

            try {
                String output;

                if (from.equals(to)) {
                    output = input;
                } else if ("   English".equals(from) && "   Vietnamese".equals(to)) {
                    output = GoogleAPI.translateEnToVi(input).trim().replace("[", "").replace("]", "");
                } else {
                    output = GoogleAPI.translateViToEn(input).trim().replace("[", "").replace("]", "");
                }

                showmeaning.setText(output);
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    @FXML
    public void handleSoundButton() {
        String meaningText = showmeaning.getText();
        String language = meaning.getValue();
        if (language.equals("   Vietnamese")) {
            try {
                TextToSpeechOnline.textToSpeechVie(meaningText);
                //TextToSpeech.Speech(word);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else {
            try {
                TextToSpeechOnline.textToSpeech(meaningText);
                //TextToSpeech.Speech(word);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
