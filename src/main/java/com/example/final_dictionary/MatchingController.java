package com.example.final_dictionary;

import Database.DataLite;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class MatchingController implements Initializable {
    @FXML
    private Label box1, box2, box3, box4, box5, box6, box7, box8, box9, box10;
    @FXML
    private Label box11, box12, box13, box14, box15, box16, box17, box18, box19, box20;

    private final DataLite d = new DataLite();

    public MatchingController() throws SQLException {
    }
    private final List<Label> selectedLabels = new ArrayList<>();
    private List<String> generateRandomValues() throws SQLException {
        List<String> originalValues = d.getMatchingWord();
        List<String> values = new ArrayList<>(originalValues);

        if (values.isEmpty()) {
            return values;
        }
        for (String value : originalValues) {
            String mean = d.getMatchingMean(value);
            values.add(mean);
        }
        Collections.shuffle(values);
        return values;
    }
    private void randomBox() {
        List<Label> boxes = new ArrayList<>(List.of(box1, box2, box3, box4, box5, box6, box7, box8, box9, box10,
                box11, box12, box13, box14, box15, box16, box17, box18, box19, box20));

        List<String> values;
        try {
            values = generateRandomValues();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (int i = 0; i < boxes.size(); i++) {
            Label label = boxes.get(i);
            if (label != null) {
                label.setText(values.get(i));
                label.setOnMouseClicked(event -> {
                    try {
                        handleLabelClick(label);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }
    private void checkMatch(Label label1, Label label2) throws SQLException {//, Map<Label, String> originalTexts
        String text1 = label1.getText();
        String text2 = label2.getText();

        String meaning1 = d.getMatchingMean(text1);
        String meaning2 = d.getMatchingMean(text2);

        boolean isMatch = (meaning1 != null && meaning1.equals(text2)) || (meaning2 != null && meaning2.equals(text1));

        if (isMatch) {
                label1.setVisible(false);
                label2.setVisible(false);
        } else {
            label1.setDisable(false);
            label2.setDisable(false);
        }
    }

    private void handleLabelClick(Label label) throws SQLException {
        if (selectedLabels.size() < 2) {
            selectedLabels.add(label);
            label.setDisable(true);

            if (selectedLabels.size() == 2) {
                checkMatch(selectedLabels.get(0), selectedLabels.get(1));
                selectedLabels.clear();
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        randomBox();
    }
}
