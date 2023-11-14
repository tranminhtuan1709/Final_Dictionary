package com.example.final_dictionary;

import Database.DataLite;
import Speech.TextToSpeechOnline;
import javafx.application.Platform;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebView;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    private ListView historyList;
    @FXML
    public AnchorPane scrollpane;

    @FXML
    private Button saveButton;

    @FXML
    private Button unsaveButton;

    @FXML
    private Button speech2;


    @FXML
    private Button seeDetail;

    @FXML
    private Label posLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label wordLabel;

    @FXML
    private HTMLEditor htmlTextEditor;

    @FXML
    private Button editButton;

    @FXML
    private Button saveEditButton;

    @FXML
    private Rectangle corrector;

    @FXML
    private Button trashButton;

    @FXML
    private Button acceptButton;

    @FXML
    private AnchorPane alertPane;


    @FXML
    private Button discardButton;


    @FXML
    private Button okayButton;


    @FXML
    private AnchorPane notiPane;

    @FXML
    private Button acceptButton1;

    @FXML
    private AnchorPane alertPane1;


    @FXML
    private Button discardButton1;


    @FXML
    private Button okayButton1;


    @FXML
    private AnchorPane notiPane1;

    @FXML
    private AnchorPane homeAP;




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
            htmlTextEditor.setHtmlText(w);
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
        listWord.getItems().clear();
        try {
            listWord.setStyle("-fx-font-size: 14px;");
            ArrayList<String> list = d.getListWord();
            for (int i = 0; i < list.size(); i += 234) {
                int endIndex = Math.min(i + 234, list.size());
                List<String> sublist = list.subList(i, endIndex);
                Platform.runLater(() -> listWord.getItems().addAll(sublist));
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleHistory() {
        historyList.getItems().clear();
        try {
            historyList.setStyle("-fx-font-size: 14px;");
            ArrayList<String> list = d.getHistory();
            for (int i = 0; i < list.size(); i += 234) {
                int endIndex = Math.min(i + 234, list.size());
                List<String> sublist = list.subList(i, endIndex);
                Platform.runLater(() -> historyList.getItems().addAll(sublist));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        Month month = today.getMonth();
        String monthString = month.toString();
        int year = today.getYear();

        String date = dayOfMonth + ", "
                + monthString + " " + year;


        // Use the day as a seed for the random number generator
        Random random = new Random(dayOfMonth);

        // Generate a random number
        int randomNumber = random.nextInt(100000);

        try {
            wordLabel.setText(d.searchWordbyID(randomNumber));
            posLabel.setText(d.searchPOSbyID(randomNumber));
            dateLabel.setText(date);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        handleListWord();
        handleHistory();


        searchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            listWord.getItems().clear();
            String in1 = searchBar.getText();
            ArrayList<String> list1 = null;
            try {
                list1 = d.suggestWords(in1);
                System.out.println(list1.size());
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


        searchButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = searchBar.getText();
            currentWord.set(word);
            try {
                d.addHistory(word);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            handleHistory();
            if (!handleSearchButton(word)) {
                scrollpane.setVisible(false);
            }
        }));

        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String word = searchBar.getText();
                currentWord.set(word);
                try {
                    d.addHistory(word);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                handleHistory();
                if (!handleSearchButton(word)) {
                    scrollpane.setVisible(false);
                }
            }
        });

        listWord.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = listWord.getSelectionModel().getSelectedItem().toString();
            try {
                d.addHistory(word);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            handleHistory();
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

        speech2.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = wordLabel.getText();
            if (word != null) {
                handleSpeech(word);
            }
        }));

        seeDetail.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            String word = wordLabel.getText();

            try {
                d.addHistory(word);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            handleHistory();

            currentWord.set(word);
            if (!handleSearchButton(word)) {
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

        editButton.setOnAction(actionEvent -> {
            htmlTextEditor.setVisible(true);
            editButton.setVisible(false);
            saveEditButton.setVisible(true);
            corrector.setVisible(true);
            trashButton.setVisible(false);
        });

        saveEditButton.setOnAction(actionEvent -> {
            for (Node i : homeAP.getChildren()) {
                i.setDisable(true);
            }
            alertPane.setVisible(true);
            alertPane.setDisable(false);
        });

        acceptButton.setOnAction(actionEvent -> {
            //edit content in database here
            String detail = htmlTextEditor.getHtmlText();

            // Define the pattern to match the content between the specified tags
            String patternString = "<h1 style=\"color:#951D05; font-family: Segoe UI\">(.*?)</h1>";
            Pattern pattern = Pattern.compile(patternString);

            // Create a matcher
            Matcher matcher = pattern.matcher(detail);

            // Find and print the matched content
            String extractedText = null;
            if (matcher.find()) {
                extractedText = matcher.group(1);
            }

            if (extractedText != null) {
                String newWord = extractedText.replaceAll("<[^>]*>", "");
                System.out.println(newWord);
                if (!currentWord.get().equals(newWord)) {
                    try {
                        d.updateWord(newWord, detail, currentWord.get());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        d.updateWord(currentWord.get(), detail, currentWord.get());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                try {
                    d.updateWord(currentWord.get(), detail, currentWord.get());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }

            alertPane.setDisable(true);
            alertPane.setVisible(false);

            notiPane.setVisible(true);
            notiPane.setDisable(false);
        });

        okayButton.setOnAction(actionEvent -> {
            notiPane.setVisible(false);
            notiPane.setDisable(true);

            for (Node i : homeAP.getChildren()) {
                i.setDisable(false);
            }

            htmlTextEditor.setVisible(false);

            searchBar.clear();
            handleListWord();
            scrollpane.setVisible(false);
            editButton.setVisible(true);
            saveEditButton.setVisible(false);
            corrector.setVisible(false);
            trashButton.setVisible(true);
        });

        discardButton.setOnAction(actionEvent -> {
            alertPane.setVisible(false);
            alertPane.setDisable(true);

            for (Node i : homeAP.getChildren()) {
                i.setDisable(false);
            }
            htmlTextEditor.setVisible(false);
            editButton.setVisible(true);
            saveEditButton.setVisible(false);
            corrector.setVisible(false);
            trashButton.setVisible(true);
        });

        trashButton.setOnAction(actionEvent -> {
            for (Node i : homeAP.getChildren()) {
                i.setDisable(true);
            }

            alertPane1.setVisible(true);
            alertPane1.setDisable(false);
        });

        discardButton1.setOnAction(actionEvent -> {
            alertPane1.setVisible(false);
            alertPane1.setDisable(true);

            for (Node i : homeAP.getChildren()) {
                i.setDisable(false);
            }
        });

        acceptButton1.setOnAction(actionEvent -> {
            //delete word from database
            try {
                d.deleteWord(currentWord.get());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            searchBar.clear();
            handleListWord();
            scrollpane.setVisible(false);

            alertPane1.setVisible(false);
            alertPane1.setDisable(true);
            notiPane1.setDisable(false);
            notiPane1.setVisible(true);
        });

        okayButton1.setOnAction(actionEvent -> {
            notiPane1.setDisable(true);
            notiPane1.setVisible(false);

            for (Node i : homeAP.getChildren()) {
                i.setDisable(false);
            }
        });
    }
}