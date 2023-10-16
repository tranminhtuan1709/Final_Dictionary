package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private AnchorPane anchorPane = new AnchorPane();
    @FXML
    private ImageView menuBar = new ImageView();
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

    public Controller() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
