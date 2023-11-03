package com.example.final_dictionary;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

import javafx.scene.web.WebView;
import Database.*;
import Speech.*;
public class HomeController implements Initializable {
    @FXML
    public WebView webView;
    @FXML
    public Button speech;
    @FXML
    public AnchorPane usernameAP;
    @FXML
    public TextField searchBar;
    @FXML
    public Button searchButton;
    @FXML
    public ListView listWord;

    private void handleSearchButton(String word) {
        try {
            DataLite d = new DataLite();
            String w = d.searchWord(word);
            webView.getEngine().loadContent(w);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleSpeech(String word) {
        try {
            TextToSpeechOnline.textToSpeech(word);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleListWord() {
        try {
            ListView<String> listWord = new ListView<>();
            DataLite d = new DataLite();
            ArrayList<String> list = d.getListWord();
            for (String s : list) {
                listWord.getItems().add(s);
            }

            listWord.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
                String word = listWord.getSelectionModel().getSelectedItem();
                handleSearchButton(word);
            }));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        handleListWord();

        searchButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = searchBar.getText();
            handleSearchButton(word);
        }));

        speech.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            if(searchBar.getText().isEmpty()){
                String word1 = listWord.getSelectionModel().getSelectedItem().toString();
                handleSpeech(word1);
            } else {
                String word2 = searchBar.getText();
                handleSpeech(word2);
            }
        }));
    }
}
