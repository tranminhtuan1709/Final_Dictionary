package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;

public class DeleteAccountController implements Initializable {
    @FXML
    private TextField email, username;
    @FXML
    private PasswordField password;
    @FXML
    private Button next, confirm, back, okay;
    @FXML
    private Label notificationText;
    @FXML
    private AnchorPane notificationAP, warningAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        next.setOnAction(actionEvent -> {
            showWarningAP();
        });
        back.setOnAction(actionEvent -> {
            quitWarningAP();
        });
        confirm.setOnAction(actionEvent -> {
            quitWarningAP();
            checkAccountInformation();
            showNotification();
        });
        okay.setOnAction(actionEvent -> {
            quitNotificationAP();
        });
    }

    @FXML
    public void checkAccountInformation() {
        notificationText.setStyle("-fx-text-fill: red");
        if (email.getText().isEmpty()) {
            notificationText.setText("Email field is empty!");
        } else if (username.getText().isEmpty()) {
            notificationText.setText("Username field is empty!");
        } else if (password.getText().isEmpty()) {
            notificationText.setText("Password field is empty!");
        } else {
            notificationText.setText("Your account is deleted successfully!");
            notificationText.setStyle("-fx-text-fill: green");
        }
    }

    @FXML
    public void showWarningAP() {
        Parent p = warningAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("warningAP")) {
                i.setDisable(true);
            }
        }
        warningAP.setDisable(false);
        warningAP.setVisible(true);
        warningAP.toFront();
    }

    @FXML
    public void quitWarningAP() {
        Parent p = warningAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("warningAP")) {
                i.setDisable(false);
            }
        }
        warningAP.setDisable(true);
        warningAP.setVisible(false);
        warningAP.toBack();
    }

    @FXML
    public void showNotification() {
        Parent p = notificationAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("notificationAP")) {
                i.setDisable(true);
            }
        }
        notificationAP.setDisable(false);
        notificationAP.setVisible(true);
        notificationAP.toFront();
    }

    @FXML
    public void quitNotificationAP() {
        Parent p = notificationAP.getParent();
        for (Node i : p.getChildrenUnmodifiable()) {
            if (i.getId() == null || !i.getId().equals("notificationAP")) {
                i.setDisable(false);
            }
        }
        notificationAP.setDisable(true);
        notificationAP.setVisible(false);
        notificationAP.toBack();
    }
}
