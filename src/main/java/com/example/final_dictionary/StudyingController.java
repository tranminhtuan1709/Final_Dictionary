package com.example.final_dictionary;

import Database.DataLite;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
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
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

public class StudyingController implements Initializable {
    @FXML
    private AnchorPane questionAP1, questionAP2, resultAP;
    @FXML
    private Button choiceA1, choiceB1, choiceC1, choiceA2, choiceB2, choiceC2, next;
    @FXML
    private Label word1, word2, resultText;
    @FXML
    private ImageView imageView1, imageView2;
    @FXML
    Rectangle rectangle;
    private int attemptCount = 0;
    private static final int MAX_ATTEMPTS = 20;
    private final DataLite d = new DataLite();
    private final ArrayList<String> question = d.getQuestion();
    private int center = 1;
    private final Random random = new Random();

    public StudyingController() throws SQLException {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setQuestionAP1();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        choiceA1.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceA1.getText())) {
                    choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                } else {
                    choiceA1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                }
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceB1.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceB1.getText())) {
                    choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                } else {
                    choiceB1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                }
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceC1.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceC1.getText())) {
                    choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                } else {
                    choiceC1.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                }
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceA2.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceA2.getText())) {
                    choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                } else {
                    choiceA2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                }
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceB2.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceB2.getText())) {
                    choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                } else {
                    choiceB2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                }
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        choiceC2.setOnAction(actionEvent -> {
            try {
                if (checkAnswer(center, choiceC2.getText())) {
                    choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                    resultText.setStyle("-fx-text-fill: #385723");
                    resultText.setText("Well done!");
                } else {
                    choiceC2.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                    resultText.setStyle("-fx-text-fill: #C00000");
                    resultText.setText("Don’t worry, just keep learning!");
                }
                resultAP.setVisible(true);
                showResultChoice();
                rectangle.toFront();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        next.setOnAction(actionEvent -> {
            attemptCount++;
            if (attemptCount <= MAX_ATTEMPTS) {
                try {
                    refreshChoiceButtonEffect();
                    nextQuestion();
                    rectangle.toBack();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                next.setText("Finish");
                System.out.println("Congratulation!");
            }
        });
    }

    @FXML
    public void setChoices(Label word, Button choiceA, Button choiceB, Button choiceC) throws SQLException {
        ArrayList<String> choiceList = d.getChoice(String.valueOf(word.getText()));
        if (choiceList.isEmpty()) {
            return;
        }
        Collections.shuffle(choiceList);
        choiceA.setText(choiceList.get(0));
        choiceB.setText(choiceList.get(1));
        choiceC.setText(choiceList.get(2));
    }

    @FXML
    public void nextQuestion() throws SQLException {
        TranslateTransition transition1 = new TranslateTransition(Duration.seconds(0.5), questionAP1);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(0.5), questionAP2);
        if (center == 1) {
            transition1.setFromX(0);
            transition1.setToX(-1135);
            transition2.setFromX(1135);
            transition2.setToX(0);
            setQuestionAP2();
            center = 2;
        } else {
            transition1.setFromX(1135);
            transition1.setToX(0);
            transition2.setFromX(0);
            transition2.setToX(-1135);
            setQuestionAP1();
            center = 1;
        }
        hideResultChoice(transition1, transition2);
    }

    @FXML
    public void showResultChoice() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.25), resultAP);
        transition.setFromY(710);
        transition.setToY(0);
        transition.play();
    }

    @FXML
    public void hideResultChoice(TranslateTransition transition1, TranslateTransition transition2) {
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(0.5), resultAP);
        transition3.setFromY(0);
        transition3.setToY(710);
        ParallelTransition parallelTransition = new ParallelTransition(transition1, transition2, transition3);
        parallelTransition.play();
    }

    @FXML
    public boolean checkAnswer(int center, String answer) throws SQLException {
        if (center == 1 && answer.equals(d.getAnswer(word1.getText()))) {
            return true;
        } else if (center == 2 && answer.equals(d.getAnswer(word2.getText()))) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    public void setQuestionAP1() throws SQLException {
        //Image image = new Image("image/anime.jpg");
        //imageView1.setImage(image);
        int randomIndex = random.nextInt(question.size());
        word1.setText(question.get(randomIndex));
        setChoices(word1, choiceA1, choiceB1, choiceC1);
    }

    @FXML
    public void setQuestionAP2() throws SQLException {
        //Image image = new Image("image/anime.jpg");
        //imageView2.setImage(image);
        int randomIndex = random.nextInt(question.size());
        word2.setText(question.get(randomIndex));
        setChoices(word2, choiceA2, choiceB2, choiceC2);
    }

    @FXML
    public void refreshChoiceButtonEffect() {
        choiceA1.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceB1.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceC1.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");

        choiceA2.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceB2.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
        choiceC2.setStyle("-fx-font-weight: normal; -fx-background-color: #FFFFFF; -fx-text-fill: #000000");
    }
}
