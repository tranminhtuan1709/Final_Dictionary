package com.example.final_dictionary;


import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/splashScreen.fxml")));
        Scene scene = new Scene(pane);

        Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
        stage.getIcons().add(icon);

        Rectangle2D screen = Screen.getPrimary().getVisualBounds();

        stage.setX((screen.getWidth() - 637) / 2);//637
        stage.setY((screen.getHeight() - 360) / 2);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}