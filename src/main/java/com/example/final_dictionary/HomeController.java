package com.example.final_dictionary;

import Database.DataLite;
import Speech.TextToSpeech;
import Speech.TextToSpeechOnline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
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
    private AnchorPane scrollpane;

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

            //ListView<String> listWord = new ListView<>();

            DataLite d = new DataLite();
            ArrayList<String> list = d.getListWord();
            for (String s : list) {
                listWord.getItems().add(s);
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
        handleListWord();

        searchButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = searchBar.getText();
            handleSearchButton(word);

            scrollpane.setVisible(true);
        }));



        searchBar.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    String word = searchBar.getText();
                    if (handleSearchButton(word)) {
                        handleSpeech(word);
                    } else {
                        scrollpane.setVisible(false);
                    }
                }
            }
        });


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