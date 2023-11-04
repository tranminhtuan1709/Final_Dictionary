package com.example.final_dictionary;


import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Splash implements Initializable {

    private AnchorPane borderPane;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        splash();
    }

    private void splash() {
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(999);
                } catch (Exception e) {
                    //System.out.println(e);
                }

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            borderPane = FXMLLoader.load(getClass().getResource("fxml/Login.fxml"));
                            Stage stage = new Stage();
                            Scene scene = new Scene(borderPane);
                            Image icon = new Image(getClass().getResource("image/logo.png").toString());
                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();



                            stage.getIcons().add(icon);
                            stage.setTitle("My application");
                            stage.setScene(scene);
                            stage.setX((screen.getWidth() - 1000) / 2);
                            stage.setY((screen.getHeight() - 800) / 2);
                            stage.setResizable(false);

                            stage.show();
                            anchorPane.getScene().getWindow().hide();
                        } catch (IOException e) {
                        }

                    }
                });
            }
        }.start();
    }
}