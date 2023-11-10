package com.example.final_dictionary;

import Database.DataLite;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SavedItemController implements Initializable {

    @FXML
    private Label detailLabel;

    @FXML
    private Button noteButton;

    @FXML
    private Label ipaLabel;

    @FXML
    private Button trashButton;

    @FXML
    private Label wordLabel;

    @FXML
    private HBox savedItem;

    private DataLite d = new DataLite();

    public static int index = 0;

    private ArrayList<String> wordList = d.getFavorite();

    private ArrayList<String> pronounceList = d.getFavoritePOS();

    private ArrayList<String> detailList = d.getFavoriteDetail();

    private SaveController sv = new SaveController();

    public SavedItemController() throws SQLException {
    }


    /**
     * Update the label of word, IPA, and detail of each card in the list.
     */
    public void updateItem() {
        wordLabel.setText(wordList.get(index));
        ipaLabel.setText(pronounceList.get(index));
        detailLabel.setText(detailList.get(index));
    }

    public void handleNoteButton(ActionEvent e) {
        SaveController.handleNoteButton(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateItem();

        trashButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            try {
                if(d.isExistFavorite(wordLabel.getText())) {
                    d.deleteFavorite(wordLabel.getText());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            savedItem.setDisable(true);
        }));
    }
}
