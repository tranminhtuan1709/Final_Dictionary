package com.example.final_dictionary;

import Database.DataLite;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Login implements Initializable {

    @FXML
    public Button leak;
    @FXML
    public PasswordField su_pass;
    @FXML
    public Button signUpButton;
    @FXML
    public TextField su_email;
    @FXML
    public TextField su_username;
    @FXML
    private Hyperlink createAcc;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink signIn;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private AnchorPane login_form;

    private final DataLite dataLite = new DataLite();
    public static String userName;

    public Login() throws SQLException {
    }

    public void login() throws SQLException {
        //connect = connectDB();
        try {
            String user = username.getText();
            String pass = password.getText();
            if (dataLite.checkLogin(user, pass)) {
                userName = user;
                //Show the dictionary after successful login
                //javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                loginButton.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                Scene scene = new Scene(root);
                Stage stage = new Stage();


                Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                stage.getIcons().add(icon);

                stage.setTitle("English - Vietnamese Learner's Dictionary");

                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                stage.setX((screen.getWidth() - 1200) / 2);
                stage.setY((screen.getHeight() - 700) / 2);

                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();

            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception event) {
            event.printStackTrace(new PrintStream(System.out));
        }
    }


    public void login_enter() {
        //connect = connectDB();
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        String user = username.getText();
                        String pass = password.getText();
                        if (dataLite.checkLogin(user, pass)) {
                            //Show the dictionary after successful login
                            //javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            loginButton.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                            //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();


                            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                            stage.getIcons().add(icon);

                            stage.setTitle("English - Vietnamese Learner's Dictionary");

                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                            stage.setX((screen.getWidth() - 1200) / 2);
                            stage.setY((screen.getHeight() - 700) / 2);

                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ev) {
                        ev.printStackTrace(new PrintStream(System.out));
                    }
                }
            }
        });
        username.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        String user = username.getText();
                        String pass = password.getText();
                        if (dataLite.checkLogin(user, pass)) {
                            //Show the dictionary after successful login
                            //javax.swing.JOptionPane.showMessageDialog(null, "Login Successfully!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                            loginButton.getScene().getWindow().hide();

                            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));

                            //Parent root = FXMLLoader.load(getClass().getResource("fxml/DictionaryScene1.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();


                            Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                            stage.getIcons().add(icon);

                            stage.setTitle("English - Vietnamese Learner's Dictionary");

                            Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                            stage.setX((screen.getWidth() - 1200) / 2);
                            stage.setY((screen.getHeight() - 700) / 2);

                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.show();

                        } else {
                            javax.swing.JOptionPane.showMessageDialog(null, "Wrong Username of Password. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (Exception ev) {
                        ev.printStackTrace(new PrintStream(System.out));
                    }
                }
            }
        });
    }
/*
**********************************************************************************************************************
* SIGN UP
 */
    public boolean patternMatches(String emailAddress) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }
    @FXML
    public void signup() {
        //connect = connectDB();
        try {
            String user = su_username.getText();
            String pass = su_pass.getText();
            String email = su_email.getText();
            if(patternMatches(email)) {
                if(dataLite.isExistAccount(email, user, pass)) {
                    javax.swing.JOptionPane.showMessageDialog(null, "Account is exist!", "System Alert", JOptionPane.ERROR_MESSAGE);
                } else {
                    dataLite.signUp(user, pass, email);
                    javax.swing.JOptionPane.showMessageDialog(null, "Sign up successfully, please sign in now!", "System Message", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                javax.swing.JOptionPane.showMessageDialog(null, "Wrong email format. Please try again!", "System Alert", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
            signup_form.setVisible(false);
            login_form.setVisible(true);

        } catch (Exception event) {
            event.printStackTrace(new PrintStream(System.out));
        }
    }

    @FXML
    public void changeForm(ActionEvent event) {
        if (event.getSource() == createAcc) {
            signup_form.setVisible(true);
            login_form.setVisible(false);

        } else if (event.getSource() == signIn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
        }
    }

    @FXML
    public void quickStart() {
        leak.setOnMouseClicked(mouseEvent -> {
            try {
                userName = "guest";
                loginButton.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/Menu.fxml")));
                Scene scene = new Scene(root);
                Stage stage = new Stage();

                Image icon = new Image(Objects.requireNonNull(getClass().getResource("image/logo.png")).toString());
                stage.getIcons().add(icon);

                stage.setTitle("English - Vietnamese Learner's Dictionary");

                Rectangle2D screen = Screen.getPrimary().getVisualBounds();
                stage.setX((screen.getWidth() - 1200) / 2);
                stage.setY((screen.getHeight() - 700) / 2);

                stage.setResizable(false);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        quickStart();
    }
}