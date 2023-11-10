package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private Button launchFlashcard, launchStudying, launchChallenging, launchMatching;
    @FXML
    private AnchorPane flashcardAP, studyingAP, challengingAP, matchingAP;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadAP();
            launchFlashcard.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Flashcard.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Flashcard");
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            launchStudying.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Studying.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Studying");
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            launchChallenging.setOnAction(actionEvent -> {
                try {
                    Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Challenging.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("Challenging");
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loadAP() throws IOException {
        if (flashcardAP == null) {
            flashcardAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Flashcard.fxml")));
        }
        if (studyingAP == null) {
            studyingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Studying.fxml")));
        }
        if (challengingAP == null) {
            challengingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Challenging.fxml")));
        }
        if (matchingAP == null) {
            matchingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Matching.fxml")));
        }
    }
}
