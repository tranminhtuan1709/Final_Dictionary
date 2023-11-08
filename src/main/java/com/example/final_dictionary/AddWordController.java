package com.example.final_dictionary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddWordController implements Initializable {
    @FXML
    private TextArea addwordmeaning;

    @FXML
    private TextField breIPA;

    @FXML
    private TextField nameIPA;

    @FXML
    private TextField posField;

    @FXML
    private TextField wordField;

    @FXML
    private ImageView alert1;

    @FXML
    private ImageView alert2;

    @FXML
    private ImageView alert3;

    @FXML
    private AnchorPane addNotiPane;

    @FXML
    private AnchorPane changeNotiPane;

    @FXML
    private AnchorPane addwordPane;

    @FXML
    private AnchorPane alertPane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void handleDiscardButton(ActionEvent e) {
        wordField.clear();
        posField.clear();
        breIPA.clear();
        nameIPA.clear();
        addwordmeaning.clear();
    }

    public void handleSaveButton(ActionEvent e) {
        String word = wordField.getText();
        String pos = posField.getText();
        String bre = breIPA.getText();
        String name = nameIPA.getText();
        String meaning = addwordmeaning.getText();
        if (!word.isEmpty() && !pos.isEmpty() && !meaning.isEmpty()) {
            if (true/*word not in the dictionary*/) {
                for (Node i : addwordPane.getChildren()) {
                    i.setDisable(true);
                }
                addNotiPane.setDisable(false);
                addNotiPane.setVisible(true);
                addNotiPane.toFront();
                //add word definition to database
            } else {
                for (Node i : addwordPane.getChildren()) {
                    i.setDisable(true);
                }
                alertPane.setDisable(false);
                alertPane.setVisible(true);
                alertPane.toFront();
            }

            alert1.setVisible(false);
            alert2.setVisible(false);
            alert3.setVisible(false);
        } else {
            if (word.isEmpty()) alert1.setVisible(true);
            else alert1.setVisible(false);

            if (pos.isEmpty()) alert2.setVisible(true);
            else alert2.setVisible(false);

            if (meaning.isEmpty()) alert3.setVisible(true);
            else alert3.setVisible(false);
        }
    }

    //For addNotiPane
    public void handleOkayButton1(ActionEvent e) {
        for (Node i : addwordPane.getChildren()) {
            i.setDisable(false);
        }

        addNotiPane.setDisable(true);
        addNotiPane.setVisible(false);
        addNotiPane.toBack();
    }

    //For changeNotiPane
    public void handleOkayButton2(ActionEvent e) {
        for (Node i : addwordPane.getChildren()) {
            i.setDisable(false);
        }

        changeNotiPane.setDisable(true);
        changeNotiPane.setVisible(false);
        changeNotiPane.toBack();
    }

    public void handleUpdateButton(ActionEvent e) {
        alertPane.setDisable(true);
        alertPane.setVisible(false);
        alertPane.toBack();

        //Change meaning in the database

        changeNotiPane.setDisable(false);
        changeNotiPane.setVisible(true);
        changeNotiPane.toFront();
    }

    public void handleAppendButton(ActionEvent e) {
        alertPane.setDisable(true);
        alertPane.setVisible(false);
        alertPane.toBack();

        //append meaning in the database

        changeNotiPane.setDisable(false);
        changeNotiPane.setVisible(true);
        changeNotiPane.toFront();
    }
}
