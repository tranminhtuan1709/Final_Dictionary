package com.example.final_dictionary;

import Database.DataLite;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SaveController implements Initializable {

    private EventBus eventBus = new EventBus();

    public Button ssw;


    @FXML
    private VBox savedWordsContainer;

    @FXML
    private ScrollPane SavedList;

    @FXML
    private Label wordCount;

    @FXML
    private Button launchQuiz1Button;

    @FXML
    private Button launchQuiz2Button;

    @FXML
    private Label nothingLabel;

    @FXML
    private ImageView image;

    private DataLite d = new DataLite();

    public SaveController() throws SQLException {
    }


    public void loadListSavedWords() {
        try {
            ArrayList<String> list = d.getFavorite();
            wordCount.setText(0 + " word");
            if (!list.isEmpty()) {
                if (list.size() == 1)
                wordCount.setText("1 word");
                else wordCount.setText(list.size() + " words");
                SavedList.setVisible(true);
                image.setVisible(false);
                nothingLabel.setVisible(false);
                savedWordsContainer.getChildren().clear();
                savedWordsContainer.setSpacing(10);
                Node[] nodes = new Node[list.size()];

                for (int i = 0; i < nodes.length; i++) {
                    try {
                        SavedItemController.index = i;
                        nodes[i] = FXMLLoader.load(SaveController.class.getResource("fxml/Item.fxml"));
                        savedWordsContainer.getChildren().add(nodes[i]);
                    } catch (IOException ev) {
                        ev.printStackTrace();
                    }
                }
            } else {
                SavedList.setVisible(false);
                image.setVisible(true);
                nothingLabel.setVisible(true);
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void handleReloadButton(ActionEvent e) {
        loadListSavedWords();
    }

    public static void handleNoteButton(ActionEvent e) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadListSavedWords();
        EventBus.subscribe(this::handleReloadButton);
    }

}
