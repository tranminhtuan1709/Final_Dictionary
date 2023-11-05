package com.example.final_dictionary;

import Database.DataLite;
import Speech.TextToSpeechOnline;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

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
    @FXML
    public AnchorPane scrollpane;

    private boolean handleSearchButton(String word) {
        boolean check = true;
        try {
            DataLite d = new DataLite();
            String w = d.searchWord(word);
            scrollpane.setVisible(true);

            webView.getEngine().loadContent(w);
            if (w == null) check = false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return check;
    }

    private void handleSpeech(String word) {
        try {
            TextToSpeechOnline.textToSpeech(word);
            //TextToSpeech.Speech(word);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleListWord() {
        try {
            DataLite d = new DataLite();
            ArrayList<String> list = d.getListWord();

            for (int i = 0; i < list.size(); i += 234) {
                int endIndex = Math.min(i + 234, list.size());
                List<String> sublist = list.subList(i, endIndex);
                Platform.runLater(() -> listWord.getItems().addAll(sublist));
            }

            listWord.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
                String word = listWord.getSelectionModel().getSelectedItem().toString();

                handleSearchButton(word);

                scrollpane.setVisible(true);
            }));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AtomicReference<String> currentWord = new AtomicReference<>("");
        handleListWord();

        searchButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = searchBar.getText();
            currentWord.set(word);
            if (!handleSearchButton(word)) {
                scrollpane.setVisible(false);
            }
        }));

        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String word = searchBar.getText();
                currentWord.set(word);
                if (!handleSearchButton(word)) {
                    scrollpane.setVisible(false);
                }
            }
        });

        listWord.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = listWord.getSelectionModel().getSelectedItem().toString();
            currentWord.set(word);
            if (!handleSearchButton(word)) {
                scrollpane.setVisible(false);
            }
        }));

        speech.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = currentWord.get();
            if (word != null) {
                handleSpeech(word);
            } else {
                scrollpane.setVisible(false);
            }
        }));
    }
}