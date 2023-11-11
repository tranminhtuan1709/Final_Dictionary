package com.example.final_dictionary;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SavedWordNoteController implements Initializable {

    @FXML
    private Button discardButton;

    @FXML
    private TextArea noteArea;

    @FXML
    private Button saveButton;

    public static String word;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String filePath = "src/main/Note/" + Login.userName + "_" + word + ".txt";
        String text = "";
        try {
            FileReader reader = new FileReader(filePath);
            if (reader.ready()) {
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    text = text + line + "\n";
                }
            }
            reader.close();

        } catch (IOException e) {
        }
        noteArea.setText(text);

        discardButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            discardButton.getScene().getWindow().hide();
        }));

        saveButton.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                String content = noteArea.getText();
                writer.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
            saveButton.getScene().getWindow().hide();
        }));
    }
}
