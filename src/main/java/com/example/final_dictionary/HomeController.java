package com.example.final_dictionary;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.web.WebView;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane homeAP;
    @FXML
    private TextField searchBar;

    @FXML
    private WebView wordshow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
