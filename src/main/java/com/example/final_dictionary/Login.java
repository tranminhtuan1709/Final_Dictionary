package com.example.final_dictionary;


import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.scene.input.KeyEvent;

//import java.awt.event.KeyEvent;
import java.io.IOException;
import java.sql.*;

import java.net.URL;

import java.util.Objects;
import java.util.ResourceBundle;



public class Login implements Initializable {

    @FXML
    private AnchorPane borderPane;

    @FXML
    private Hyperlink createAcc;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink signIn;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField su_email;

    @FXML
    private PasswordField su_pass;

    @FXML
    private TextField su_username;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private AnchorPane login_form;

    @FXML
    private Rectangle waiting;

    @FXML
    private ProgressIndicator waiting1;

    @FXML
    private Label waiting2;


    //DATABASE
    private Connection connect;
    private PreparedStatement statement;
    private ResultSet result;


//    public Connection connectDB() {
//        connect = null;
//        try {
//            //Class.forName("com.mysql.cj.jdbc.Driver");
//            connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/userdb?characterEncoding=utf8", "root", "22028238vnu");
//
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        return connect;
//    }

    public void login(ActionEvent e) {
        //connect = connectDB();

        try {

            String sql = "SELECT * FROM userinformation WHERE username = ? AND pass = ?";
//
//            statement = connect.prepareStatement(sql);
//            statement.setString(1, username.getText());
//            statement.setString(2, password.getText());
//            result = statement.executeQuery();

            if (username.getText().equals("")  && password.getText().equals("")) {
                //Show the dictionary after successful login
                // javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loginButton.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();


                Image icon = new Image(getClass().getResource("image/logo.png").toString());
                stage.getIcons().add(icon);

                stage.setTitle("My application");

                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                stage.setX((screen.getWidth() - 1200) / 2);
                stage.setY((screen.getHeight() - 700) / 2);

                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch(Exception event) {
            event.printStackTrace();
        }
    }



    public void login_enter(KeyEvent e) {
        //connect = connectDB();
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {

                        String sql = "SELECT * FROM userinformation WHERE username = ? AND pass = ?";
            //
            //            statement = connect.prepareStatement(sql);
            //            statement.setString(1, username.getText());
            //            statement.setString(2, password.getText());
            //            result = statement.executeQuery();

                        if (username.getText().equals("admin")  && password.getText().equals("123")) {
                            //Show the dictionary after successful login
                            javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            loginButton.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                            //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();


                            Image icon = new Image(getClass().getResource("image/logo.png").toString());
                            stage.getIcons().add(icon);

                            stage.setTitle("My application");

                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                            stage.setX((screen.getWidth() - 1200) / 2);
                            stage.setY((screen.getHeight() - 700) / 2);

                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                    } catch(Exception ev) {
                        ev.printStackTrace();
                    }
                }
            }
        });
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {

                        String sql = "SELECT * FROM userinformation WHERE username = ? AND pass = ?";
                        //
                        //            statement = connect.prepareStatement(sql);
                        //            statement.setString(1, username.getText());
                        //            statement.setString(2, password.getText());
                        //            result = statement.executeQuery();

                        if (username.getText().equals("admin")  && password.getText().equals("123")) {
                            //Show the dictionary after successful login
                            javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            loginButton.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                            //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();


                            Image icon = new Image(getClass().getResource("image/logo.png").toString());
                            stage.getIcons().add(icon);

                            stage.setTitle("My application");

                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                            stage.setX((screen.getWidth() - 1200) / 2);
                            stage.setY((screen.getHeight() - 700) / 2);

                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                    } catch(Exception ev) {
                        ev.printStackTrace();
                    }
                }
            }
        });
    }


    public void signup(ActionEvent e) {
        //connect = connectDB();
        try {

//            String sql = "INSERT INTO userinformation VALUES (?, ?, ?)";
//            statement = connect.prepareStatement(sql);
//            statement.setString(1, su_username.getText());
//            statement.setString(2, su_pass.getText());
//            statement.setString(3, su_email.getText());
//            statement.execute();

            javax.swing.JOptionPane.showMessageDialog(null, "Sign up successfully, please sign in now!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            signup_form.setVisible(false);
            login_form.setVisible(true);

        } catch (Exception event) {
            event.printStackTrace();
        }
    }

    public void changeForm(ActionEvent event) throws InterruptedException {
        if (event.getSource() == createAcc) {
//            waiting.setVisible(true);
//            waiting1.setVisible(true);
//            waiting2.setVisible(true);
            //Thread.sleep(2000);


            signup_form.setVisible(true);
            login_form.setVisible(false);
//            waiting.setVisible(false);
//            waiting1.setVisible(false);
//            waiting2.setVisible(false);
        } else if (event.getSource() == signIn) {
//            waiting.setVisible(true);
//            waiting1.setVisible(true);
//            waiting2.setVisible(true);
            signup_form.setVisible(false);
            login_form.setVisible(true);

//            waiting.setVisible(false);
//            waiting1.setVisible(false);
//            waiting2.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}
