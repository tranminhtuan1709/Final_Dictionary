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

public class StudyingController implements Initializable {
    @FXML
    private AnchorPane questionAP, resultAP;
    @FXML
    private Button choiceA, choiceB, choiceC, next;
    @FXML
    private Label word, resultText;
    @FXML
    private ImageView imageView;
    @FXML
    Rectangle rectangle;
    private int attemptCount = 0;
    private static final int MAX_ATTEMPTS = 20;
    private final DataLite d = new DataLite();
    public AtomicReference<String> question = new AtomicReference<>("");

    public StudyingController() throws SQLException {
    }
    private final Random random = new Random();
    private void loadQuestion() throws SQLException {
        ArrayList<String> questionList = d.getQuestion();
        if (questionList.isEmpty()) {
            return;
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
                if (checkAnswer().equals(choiceA.getText())) {
                    choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setVisible(true);
                } else {
                    choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setVisible(true);
                }
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceB.setOnAction(actionEvent -> {
            try {
                if (checkAnswer().equals(choiceB.getText())) {
                    choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setVisible(true);
                } else {
                    choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setVisible(true);
                }
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceC.setOnAction(actionEvent -> {
            try {
                if (checkAnswer().equals(choiceC.getText())) {
                    choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                    resultAP.setVisible(true);
                } else {
                    choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                    resultAP.setVisible(true);
                }
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        next.setOnAction(actionEvent -> {
            attemptCount++;
            if (attemptCount <= MAX_ATTEMPTS) {
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
            } else {
                next.setText("Finish");
                System.out.println("Congratulation!");
            }
        });
    }
}
