package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

public class AutomaticSoundController implements Initializable {
    @FXML
    private Button man, woman;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        man.setOnAction(actionEvent -> {
            changeVoiceToMan();
        });
        woman.setOnAction(actionEvent -> {
            changeVoiceToWoman();
        });
    }

    @FXML
    public void changeVoiceToMan() {
        man.setStyle("-fx-background-color: green");
        woman.setStyle("-fx-background-color: transparent");
    }

    @FXML
    public void changeVoiceToWoman() {
        man.setStyle("-fx-background-color: transparent");
        woman.setStyle("-fx-background-color: green");
    }
}
