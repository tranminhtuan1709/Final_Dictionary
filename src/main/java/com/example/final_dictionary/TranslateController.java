package com.example.final_dictionary;

import Speech.TextToSpeechOnline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import API.GoogleAPI;
import java.awt.*;
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

    private final String[] items = {"English", "Vietnamese"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUp();
    }

    private void setUp() {
        translate.setValue("English");
        translate.setTooltip(new Tooltip("Select the input language"));
        meaning.setTooltip(new Tooltip("Select the output language"));
        meaning.setValue("Vietnamese");
        translate.getItems().addAll(items);
        meaning.getItems().addAll(items);
    }

    public void handleTransferButton(ActionEvent e) {
        transfer.setOnMouseClicked(mouseEvent -> {
            String tmp = translate.getValue();
            translate.setValue(meaning.getValue());
            meaning.setValue(tmp);
            //nếu chưa bấm nút translate thì ở ô meaning chưa có chữ, khi bấm transfer thì
            // không làm gì cả
            if(showmeaning.getText().isEmpty()) {
                //nếu dịch đúng ngôn ngữ thì text 2 ô sẽ khác nhau, khi bấm transfer thì 2 ô đổi gtri cho nhau
            } else if (!showmeaning.getText().equals(inputfieldtranslate.getText())) {
                String tmp2 = inputfieldtranslate.getText();
                inputfieldtranslate.setText(showmeaning.getText());
                showmeaning.setText(tmp2);
            //ví dụ như nhập xin chào khi đang để eng to vie thì ô showmeaning cũng hiện xin chào
            //thì mình chỉ cần xóa cái ô showmeaning, để lại text ở ô input khi bấm transfer button
            } else {
                showmeaning.clear();
            }
        });
    }

    public void handleTranslateButton(ActionEvent e) {
        translateButton.setOnMouseClicked(mouseEvent -> {
            String input = inputfieldtranslate.getText();
            String from = translate.getValue();
            String to = meaning.getValue();

            try {
                String output;

                if (from.equals(to)) {
                    output = input;
                } else if ("English".equals(from) && "Vietnamese".equals(to)) {
                    output = GoogleAPI.translateEnToVi(input);
                } else {
                    output = GoogleAPI.translateViToEn(input);
                }

                showmeaning.setText(output);
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });
    }



    public void handleSoundButton(ActionEvent e) {
        String meaningText = showmeaning.getText();
        String language = meaning.getValue();
        if (language.equals("Vietnamese")) {
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
