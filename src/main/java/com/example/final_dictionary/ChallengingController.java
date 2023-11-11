package com.example.final_dictionary;

import Database.DataLite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class ChallengingController implements Initializable {
    @FXML
    private AnchorPane questionAP, resultAP;
    @FXML
    private Button choiceA, choiceB, choiceC, next;
    @FXML
    private Label word, resultText, time, score;
    @FXML
    private ImageView imageView;
    @FXML
    Rectangle rectangle;

    private final int score_player = 0;
    private final DataLite d = new DataLite();

    public ChallengingController() throws SQLException {
    }

    public AtomicReference<String> currentWord = new AtomicReference<>("");
    private void loadQuestion() throws SQLException {
        ArrayList<String> questionList = d.getQuestion();
        if (questionList.isEmpty()) {
            // Handle the case when the question list is empty
            return;
        }
        Random random = new Random();
        // Generate a random index within the range of the question list
        int randomIndex = random.nextInt(questionList.size());
        // Retrieve the question at the random index
        String ques =  questionList.get(randomIndex);
        currentWord.set(ques);
        word.setText(ques);
    }

    private void loadChoices() throws SQLException {
        ArrayList<String> choiceList = d.getChoice(currentWord.get());
        if (choiceList.isEmpty()) {
            return;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(choiceList.size());

        choiceA.setText(choiceList.get(randomIndex));
        choiceList.remove(randomIndex);

        randomIndex = random.nextInt(choiceList.size());
        choiceB.setText(choiceList.get(randomIndex));
        choiceList.remove(randomIndex);

        choiceC.setText(choiceList.get(0));
    }

    private boolean checkAnswer() throws SQLException {
        String key = d.getAnswer(currentWord.get());
        return choiceA.getText().equals(key) || choiceB.getText().equals(key) || choiceC.getText().equals(key);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadQuestion();
            loadChoices();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        choiceA.setOnAction(actionEvent -> {
            try {
                if (checkAnswer()) { // thêm điều kiện vào đây
                    choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done! + 10pt");
                    resultAP.setVisible(true);
                    score.setText("Score: " + (score_player - 5));
                } else {
                    choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("That’s incorrect! - 5pt");
                    score.setText("Score: " + (score_player - 5));
                    resultAP.setVisible(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rectangle.toFront();
        });

        choiceB.setOnAction(actionEvent -> {
            try {
                if (checkAnswer()) {
                    choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done! + 10pt");
                    score.setText("Score: " + (score_player + 10));
                    resultAP.setVisible(true);
                } else {
                    choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("That’s incorrect! - 5pt");
                    score.setText("Score: " + (score_player - 5));
                    resultAP.setVisible(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rectangle.toFront();
        });

        choiceC.setOnAction(actionEvent -> {
            try {
                if (checkAnswer()) {
                    choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done! + 10pt");
                    score.setText("Score: " + (score_player + 10));
                    resultAP.setVisible(true);
                } else {
                    choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("That’s incorrect! - 5pt");
                    score.setText("Score: " + (score_player - 5));
                    resultAP.setVisible(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rectangle.toFront();
        });
    }
}
