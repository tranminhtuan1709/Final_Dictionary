package com.example.final_dictionary;

import Speech.TextToSpeechOnline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.Tooltip;
import API.GoogleAPI;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import com.asprise.ocr.Ocr;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
        // multi-threading
        inputfieldtranslate.textProperty().addListener((observable, oldValue, newValue) -> {
            translateText();
        });
    }
    // multi-threading
    private void translateText() {
        Task<String> task = new Task<String>() {
            @Override
            protected String call() throws Exception {
                String input = inputfieldtranslate.getText().replace("\n" , " ");
                String from = translate.getValue();
                String to = meaning.getValue();
                String res;
                if (from.equals(to)) {
                    res = input;
                } else if ("   English".equals(from) && "   Vietnamese".equals(to)) {
                    res = GoogleAPI.translateEnToVi(input).trim().replace("[", "").replace("]", "")
;
                } else {
                    res = GoogleAPI.translateViToEn(input).trim().replace("[", "").replace("]", "");
                }
                return res;
            }
        };

        task.setOnSucceeded(event -> {
            Platform.runLater(() -> showmeaning.setText(task.getValue()));
        });

        new Thread(task).start();
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
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg"));

            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                Ocr ocr = new Ocr();
                ocr.startEngine("eng", Ocr.SPEED_FASTEST);
                BufferedImage bufferedImage;
                try {
                    bufferedImage = ImageIO.read(selectedFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String result = ocr.recognize(bufferedImage, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
                result = result.trim().replace("\n", " ");
                inputfieldtranslate.setText(result);
                ocr.stopEngine();
            } else {
                System.out.println("No image is selected!");
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
