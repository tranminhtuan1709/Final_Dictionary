package com.example.final_dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    @FXML
    private Button homeButton, savedWordButton, translateButton, addWordButton, gameButton, infoButton, settingButton;
    @FXML
    private Button usernameButton;
    @FXML
    private Button quitMenu;
    @FXML
    private Button showMenu;
    @FXML
    private AnchorPane container;
    @FXML
    private AnchorPane menuAP;
    @FXML
    private AnchorPane switchAP;
    @FXML
    private AnchorPane usernameAP;
    @FXML
    private Button usernameButton2;
    @FXML
    private Button signOutButton;
    @FXML
    private AnchorPane homeAP, savedWordAP, translateAP, addWordAP, gameAP, infoAP, settingAP;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            usernameButton.setText(Login.userName);
            usernameButton2.setText(Login.userName);
            loadAP();
            switchAP.getChildren().add(homeAP);
            switchAP.toFront();
            switchAP.setVisible(true);
            switchAP.setDisable(false);
            showMenu.setOnAction(actionEvent -> showMenu());
            quitMenu.setOnAction(actionEvent -> quitMenu());
            container.setOnMouseClicked(mouseEvent -> {
                if (!menuAP.isHover() && !menuAP.isDisable()) {
                    quitMenu();
                }
                if (!usernameAP.isHover() && !usernameAP.isDisable()) {
                    quitUsernameAP();
                }
            });
            usernameButton.setOnAction(actionEvent -> showUsernameAP());
            usernameButton2.setOnAction(actionEvent -> quitUsernameAP());
            savedWordButton.setOnAction(actionEvent -> {
                quitMenu();
                switchToSavedWordAP();
            });
            homeButton.setOnAction(actionEvent -> {
                quitMenu();
                switchToHomeAP();
            });
            translateButton.setOnAction(actionEvent -> {
                quitMenu();
                switchToTranslateAP();
            });
            addWordButton.setOnAction(actionEvent -> {
                quitMenu();
                switchToAddWordAP();
            });
            infoButton.setOnAction(actionEvent -> {
                quitMenu();
                switchToInfoAP();
            });
            settingButton.setOnAction(actionEvent -> {
                quitMenu();
                switchToSettingAP();
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void showMenu() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(true);
        }
        menuAP.setDisable(false);
        menuAP.setVisible(true);
        menuAP.toFront();
    }

    @FXML
    public void quitMenu() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(false);
        }
        menuAP.setDisable(true);
        menuAP.setVisible(false);
        menuAP.toBack();
    }

    @FXML
    public void showUsernameAP() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(true);
        }
        usernameButton.setDisable(true);
        usernameButton.setVisible(false);
        usernameAP.setDisable(false);
        usernameAP.setVisible(true);
        usernameAP.toFront();
    }

    @FXML
    public void quitUsernameAP() {
        for (Node i : switchAP.getChildren()) {
            i.setDisable(false);
        }
        usernameAP.setDisable(true);
        usernameAP.setVisible(false);
        usernameButton.setVisible(true);
        usernameButton.setDisable(false);
    }

    @FXML
    public void switchToSavedWordAP() {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(savedWordAP);
    }

    @FXML
    public void switchToHomeAP() {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(homeAP);
    }

    @FXML
    public void switchToTranslateAP() {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(translateAP);
    }

    @FXML
    public void switchToAddWordAP() {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(addWordAP);
    }

    @FXML
    public void switchToInfoAP() {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(infoAP);
    }

    @FXML
    public void switchToSettingAP() {
        switchAP.getChildren().clear();
        switchAP.getChildren().add(settingAP);
    }

    @FXML
    public void loadAP() throws IOException {
        if (homeAP == null) {
            homeAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Home.fxml")));
        }
        if (savedWordAP == null) {
            savedWordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Save.fxml")));
        }
        if (translateAP == null) {
            translateAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Translate.fxml")));
        }
        if (addWordAP == null) {
            addWordAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/AddWords.fxml")));
        }
        if (gameAP == null) {
            gameAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Game.fxml")));
        }
        if (infoAP == null) {
            infoAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Info.fxml")));
        }
        if (settingAP == null) {
            settingAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Setting.fxml")));
        }
    }

    @FXML
    public void handleSignOutButton(ActionEvent e) {
        try {
            signOutButton.getScene().getWindow().hide();
            Parent borderPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(borderPane);
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            stage.getIcons().add(icon);
            stage.setTitle("Login");
            stage.setScene(scene);
            stage.setX((screen.getWidth() - 1000) / 2);
            stage.setY((screen.getHeight() - 800) / 2);
            stage.setResizable(false);

            stage.show();
        } catch (IOException ev) {
            throw new RuntimeException(ev);
        }
    }
}
