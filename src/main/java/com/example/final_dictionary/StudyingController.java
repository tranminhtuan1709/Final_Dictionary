package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceA.setOnAction(actionEvent -> {
            if (true) { // Thêm điều kiện vào đây
                choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                resultText.setStyle("-fx-text-fill: #385723");
                resultText.setText("Well done!");
                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFDB93, 30, 0.5, 0, 0);");
                resultAP.setVisible(true);
            } else {
                choiceA.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                resultText.setStyle("-fx-text-fill: #C00000");
                resultText.setText("Don’t worry, just keep learning!");
                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFDB93, 30, 0.5, 0, 0);");
                resultAP.setVisible(true);
            }
            rectangle.toFront();
        });

        choiceB.setOnAction(actionEvent -> {
            if (true) {
                choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                resultText.setStyle("-fx-text-fill: #385723");
                resultText.setText("Well done!");
                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFDB93, 30, 0.5, 0, 0);");
                resultAP.setVisible(true);
            } else {
                choiceB.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                resultText.setStyle("-fx-text-fill: #C00000");
                resultText.setText("Don’t worry, just keep learning!");
                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFDB93, 30, 0.5, 0, 0);");
                resultAP.setVisible(true);
            }
            rectangle.toFront();
        });

        choiceC.setOnAction(actionEvent -> {
            if (true) {
                choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #C5E0B4; -fx-text-fill: #385723");
                resultText.setStyle("-fx-text-fill: #385723");
                resultText.setText("Well done!");
                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFDB93, 30, 0.5, 0, 0);");
                resultAP.setVisible(true);
            } else {
                choiceC.setStyle("-fx-font-weight: bold; -fx-background-color: #FFB097; -fx-text-fill: #C00000");
                resultText.setStyle("-fx-text-fill: #C00000");
                resultText.setText("Don’t worry, just keep learning!");
                resultAP.setStyle("-fx-effect: dropshadow(gaussian, #FFDB93, 30, 0.5, 0, 0);");
                resultAP.setVisible(true);
            }
            rectangle.toFront();
        });
    }
}
