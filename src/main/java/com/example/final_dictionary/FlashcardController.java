package com.example.final_dictionary;

import Database.DataLite;
import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class FlashcardController implements Initializable {
    @FXML
    private AnchorPane flashcardFront, flashcardBack;
    @FXML
    private StackPane stackPane;
    @FXML
    private Button backward, forward;
    @FXML
    private Label quantity, ipa, meaning, word;

    private final DataLite d = new DataLite();

    public FlashcardController() throws SQLException {
    }
    private final Random random = new Random();
    public AtomicReference<String> f = new AtomicReference<>("");

    private void loadFlashcardFront() throws SQLException {
        ArrayList<String> front = d.getFlashcardFront();
        if (front.isEmpty()) {
            return;
        }
        int randomIndex = random.nextInt(front.size());
        word.setText(front.get(randomIndex));
        f.set(front.get(randomIndex));
    }

    private void loadFlashcardBack() throws SQLException {
        ArrayList<String> back = d.getFlashcardBack(f.get());
        if (back.isEmpty()) {
            return;
        }
        ipa.setText(back.get(0));
        meaning.setText(back.get(1));
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadFlashcardFront();
            loadFlashcardBack();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        stackPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (flashcardFront.isVisible()) {
                    RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(0.25), stackPane);
                    rotateTransition1.setAxis(Rotate.X_AXIS);
                    rotateTransition1.setFromAngle(0);
                    rotateTransition1.setToAngle(-90);
                    rotateTransition1.play();

                    flashcardFront.setVisible(false);
                    flashcardBack.setVisible(true);

                    RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(0.25), stackPane);
                    rotateTransition2.setFromAngle(-90);
                    rotateTransition2.setToAngle(-180);
                    rotateTransition2.play();
                } else {
                    RotateTransition rotateTransition1 = new RotateTransition(Duration.seconds(0.25), stackPane);
                    rotateTransition1.setAxis(Rotate.X_AXIS);
                    rotateTransition1.setFromAngle(0);
                    rotateTransition1.setToAngle(90);
                    rotateTransition1.play();

                    flashcardFront.setVisible(true);
                    flashcardBack.setVisible(false);
                    flashcardFront.setStyle("-fx-scale-y: -1");

                    RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(0.25), stackPane);
                    rotateTransition2.setFromAngle(90);
                    rotateTransition2.setToAngle(180);
                    rotateTransition2.play();
                }
            }
        });

        backward.setOnAction(actionEvent -> {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), stackPane);
            translateTransition.setByX(-1000);
            translateTransition.play();
        });

        forward.setOnAction(actionEvent -> {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5), stackPane);
            translateTransition.setByX(1000);
            translateTransition.play();
        });
    }
}
