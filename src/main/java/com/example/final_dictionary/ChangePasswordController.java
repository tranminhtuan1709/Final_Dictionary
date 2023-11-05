package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangePasswordController implements Initializable {
    @FXML
    private PasswordField currentPass, newPass, confirmPass;
    @FXML
    private Button change, okay;
    @FXML
    private Label notificationText;
    @FXML
    private AnchorPane notificationAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        change.setOnAction(actionEvent -> {
            checkPass();
            showNotification();
        });
        okay.setOnAction(actionEvent -> {
            quitNotificationAP();
        });
    }

    @FXML
    public void checkPass() {
        notificationText.setStyle("-fx-text-fill: red");
        if (currentPass.getText().isEmpty()) {
            notificationText.setText("Current password field is empty!");
        } else if (newPass.getText().isEmpty()) {
            notificationText.setText("New password field is empty!");
        } else if (confirmPass.getText().isEmpty()) {
            notificationText.setText("Confirm new password field is empty!");
        } else if (currentPass.getText().equals(newPass.getText())) {
            notificationText.setText("Current password is them same as new password!");
        } else if (!newPass.getText().equals(confirmPass.getText())) {
            notificationText.setText("Confirm new password is not match the new password!");
        } else {
            notificationText.setText("Your password is updated successfully!");
            notificationText.setStyle("-fx-text-fill: green");
        }
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
