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

    @FXML
    private Button saveButton;

    @FXML
    private Button unsaveButton;

    private final DataLite d = new DataLite();

    public AtomicReference<String> currentWord = new AtomicReference<>("");

    public HomeController() throws SQLException {
    }

    private boolean handleSearchButton(String word) {
        boolean check = true;
        try {
            String w = d.searchWord(word);
            scrollpane.setVisible(true);

            webView.getEngine().loadContent(w);
            if (w == null) check = false;

            try {
                if(d.isExistFavorite(currentWord.get())) {
                    saveButton.setVisible(false);
                    saveButton.setDisable(true);
                    unsaveButton.setVisible(true);
                    unsaveButton.setDisable(false);
                } else {
                    saveButton.setVisible(true);
                    saveButton.setDisable(false);
                    unsaveButton.setVisible(false);
                    unsaveButton.setDisable(true);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

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
            ArrayList<String> list = d.getListWord();
            for (int i = 0; i < list.size(); i += 234) {
                int endIndex = Math.min(i + 234, list.size());
                List<String> sublist = list.subList(i, endIndex);
                Platform.runLater(() -> listWord.getItems().addAll(sublist));
            }

            searchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
                listWord.getItems().clear();
                String in1 = searchBar.getText();
                ArrayList<String> list1 = null;
                try {
                    list1 = d.suggestWords(in1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

                for (int i = 0; i < list1.size(); i += 234) {
                    int endIndex = Math.min(i + 234, list1.size());
                    List<String> sublist = list1.subList(i, endIndex);
                    Platform.runLater(() -> listWord.getItems().addAll(sublist));
                }
            });
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

        saveButton.setOnAction(actionEvent -> {
            try {
                if(!d.isExistFavorite(currentWord.get())) {
                    d.addFavorite(currentWord.get());
                }
                //when clicking save word, the star will be brightened
                //it means that the unsaveButton appears, and the saveButton disappears.
                saveButton.setVisible(false);
                saveButton.setDisable(true);
                unsaveButton.setVisible(true);
                unsaveButton.setDisable(false);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        unsaveButton.setOnAction(actionEvent -> {
            try {
                if(d.isExistFavorite(currentWord.get())) {
                    d.deleteFavorite(currentWord.get());
                }

                saveButton.setVisible(true);
                saveButton.setDisable(false);
                unsaveButton.setVisible(false);
                unsaveButton.setDisable(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }
}