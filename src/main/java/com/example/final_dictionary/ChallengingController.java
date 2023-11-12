package com.example.final_dictionary;

import Database.DataLite;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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

    private int score_player = 0;
    private final DataLite d = new DataLite();

    public ChallengingController() throws SQLException {
    }
    public AtomicReference<String> question = new AtomicReference<>("");
    private final Random random = new Random();

    private void loadQuestion() throws SQLException {
        ArrayList<String> questionList = d.getQuestion();
        if (questionList.isEmpty()) {return;
        }
        int randomIndex = random.nextInt(questionList.size());
        // Lấy câu hỏi tại chỉ số ngẫu nhiên
        String ques = questionList.get(randomIndex);
        word.setText(ques);
        question.set(ques);
    }

    private void loadChoices() throws SQLException {
        ArrayList<String> choiceList = d.getChoice(String.valueOf(question));
        if (choiceList.isEmpty()) {
            return;
        }

        // Trộn choiceList để ngẫu nhiên
        Collections.shuffle(choiceList);

        choiceA.setText(choiceList.get(0));
        choiceB.setText(choiceList.get(1));
        choiceC.setText(choiceList.get(2));
    }

    private String checkAnswer() throws SQLException {
        return d.getAnswer(String.valueOf(question));
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
                String key = checkAnswer();
                if (choiceA.getText().equals(key)) { // thêm điều kiện vào đây
                    choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done! + 10pt");
                    score_player +=10;
                } else {
                    choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("That’s incorrect! - 5pt");
                    score_player -=5;
                }
                score.setText("Score: " + (score_player));
                resultAP.setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rectangle.toFront();
        });

        choiceB.setOnAction(actionEvent -> {
            try {
                String key = checkAnswer();
                if (choiceB.getText().equals(key)) {
                    choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done! + 10pt");
                    score_player +=10;
                } else {
                    choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("That’s incorrect! - 5pt");
                    score_player -=5;
                }
                score.setText("Score: " + (score_player));
                resultAP.setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rectangle.toFront();
        });

        choiceC.setOnAction(actionEvent -> {
            try {
                String key = checkAnswer();
                if (choiceC.getText().equals(key)) {
                    choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done! + 10pt");
                    score_player +=10;
                } else {
                    choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("That’s incorrect! - 5pt");
                    score_player -=5;
                }
                score.setText("Score: " + (score_player));
                resultAP.setVisible(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            rectangle.toFront();
        });

        next.setOnAction(actionEvent -> {
            choiceA.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
            choiceB.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
            choiceC.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
            try {
                loadQuestion();
                loadChoices();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            resultAP.setVisible(false);
            rectangle.toBack();
        });
    }
}
