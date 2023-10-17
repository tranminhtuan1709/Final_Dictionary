package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.effect.Light;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.*;

public class MenuButtonController implements Initializable {
    @FXML
    private Button settingButton = new Button();
    @FXML
    private Button AcoountButton = new Button();
    @FXML
    private Button ggTransButton = new Button();
    @FXML
    private Button folderButton = new Button();
    @FXML
    private Button homeButton = new Button();

    public MenuButtonController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

//    public boolean checkPointInRect(Point2D p, Button button) {
//        Rectangle2D rect = new Rectangle2D(button.getLayoutX(), button.getLayoutY(),
//                button.getWidth(), button.getHeight());
//        return p.getX() >= rect.getMinX() && p.getX() <= rect.getMaxX()
//                && p.getY() >= rect.getMinY() && p.getY() <= rect.getMaxY();
//    }
//
//    @FXML
//    public void controlButton(MouseEvent mouseEvent) {
//        Point2D mouseCoordinate = new Point2D(mouseEvent.getX(), mouseEvent.getY());
//        if (checkPointInRect(mouseCoordinate, settingButton)) {
//            settingButton.setDisable(false);
//            settingButton.setVisible(true);
//        } else {
//            settingButton.setDisable(true);
//            settingButton.setVisible(false);
//        }
//    }
}
