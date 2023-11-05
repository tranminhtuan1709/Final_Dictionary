package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    @FXML
    private Button automaticSoundButton, themeButton, changePasswordButton, deleteAccountButton;
    @FXML
    private AnchorPane automaticSoundAP, themeAP, changePasswordAP, deleteAccountAP;
    @FXML
    private AnchorPane switchOptionAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadAP();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        automaticSoundButton.setOnAction(actionEvent -> {
            switchToAutomaticSoundAP();
        });
        themeButton.setOnAction(actionEvent -> {
            switchToThemeAP();
        });
        changePasswordButton.setOnAction(actionEvent -> {
            switchToChangePasswordAP();
        });
        deleteAccountButton.setOnAction(actionEvent -> {
            switchToDeleteAccountAP();
        });
    }

    @FXML
    public void switchToAutomaticSoundAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(automaticSoundAP);
    }

    @FXML
    public void switchToThemeAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(themeAP);
    }

    @FXML
    public void switchToChangePasswordAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(changePasswordAP);
    }

    @FXML
    public void switchToDeleteAccountAP() {
        switchOptionAP.getChildren().clear();
        switchOptionAP.getChildren().add(deleteAccountAP);
    }

    @FXML
    public void loadAP() throws IOException {
        if (automaticSoundAP == null) {
            automaticSoundAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/AutomaticSound.fxml")));
        }
        if (themeAP == null) {
            themeAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Theme.fxml")));
        }
        if (changePasswordAP == null) {
            changePasswordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/ChangePassword.fxml")));
        }
        if (deleteAccountAP == null) {
            deleteAccountAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/DeleteAccount.fxml")));
        }
    }
}
