package com.example.final_dictionary;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
            AnchorPane defaultAP = FXMLLoader.load(getClass().getResource("fxml/Home.fxml"));
            switchAP.getChildren().add(defaultAP);
            switchAP.toFront();
            switchAP.setVisible(true);
            switchAP.setDisable(false);
            showMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    showMenu();
                }
            });
            quitMenu.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    quitMenu();
                }
            });
            container.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (!menuAP.isHover() && !menuAP.isDisable()) {
                        quitMenu();
                    }
                    if (!usernameAP.isHover() && !usernameAP.isDisable()) {
                        quitUsernameAP();
                    }
                }
            });
            usernameButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    showUsernameAP();
                }
            });
            usernameButton2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    quitUsernameAP();
                }
            });

            savedWordButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        quitMenu();
                        switchToSavedWordAP();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            homeButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        quitMenu();
                        switchToHomeAP();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
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
}
