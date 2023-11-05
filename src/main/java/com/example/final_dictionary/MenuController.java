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
    private Button homeButton, savedWordButton, translateButton, addWordButton, gameButton, infoButton;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            AnchorPane defaultAP = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Home.fxml")));
            switchAP.getChildren().add(defaultAP);
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
                try {
                    quitMenu();
                    switchToSavedWordAP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            homeButton.setOnAction(actionEvent -> {
                try {
                    quitMenu();
                    switchToHomeAP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            translateButton.setOnAction(actionEvent -> {
                quitMenu();
                try {
                    switchToTranslateAP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            addWordButton.setOnAction(actionEvent -> {
                quitMenu();
                try {
                    switchToAddWordAP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            infoButton.setOnAction(actionEvent -> {
                quitMenu();
                try {
                    switchToInfoAP();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
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
    public void switchToSavedWordAP() throws IOException {
        switchAP.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("fxml/Save.fxml")));
        switchAP.getChildren().add(anchorPane);
    }

    @FXML
    public void switchToHomeAP() throws IOException {
        switchAP.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("fxml/Home.fxml")));
        switchAP.getChildren().add(anchorPane);
    }

    @FXML
    public void switchToTranslateAP() throws IOException {
        switchAP.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("fxml/Translate.fxml")));
        switchAP.getChildren().add(anchorPane);
    }

    @FXML
    public void switchToAddWordAP() throws IOException {
        switchAP.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("fxml/AddWords.fxml")));
        switchAP.getChildren().add(anchorPane);
    }

    @FXML
    public void switchToInfoAP() throws IOException {
        switchAP.getChildren().clear();
        AnchorPane anchorPane = FXMLLoader.load(
                Objects.requireNonNull(getClass().getResource("fxml/Info.fxml")));
        switchAP.getChildren().add(anchorPane);
    }

    @FXML
    public void signOut(ActionEvent e) {
        try {
            Parent borderPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Login.fxml")));
            Stage stage = new Stage();
            Scene scene = new Scene(borderPane);
            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
            stage.getIcons().add(icon);
            stage.setTitle("My application");
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
