package com.example.final_dictionary;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

import java.awt.*;
import java.net.URL;

import java.util.ResourceBundle;

public class FlashcardController implements Initializable {
    @FXML
    private AnchorPane flashcardFront, flashcardBack, flashcardAP;
    @FXML
    private Button backward, forward;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flashcardFront.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flashcardBack.toFront();
            }
        });

        flashcardBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                flashcardFront.toFront();
            }
        });
    }
}
