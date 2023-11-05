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
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Login implements Initializable {


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


    public void login(ActionEvent e) throws SQLException {
        //connect = connectDB();
        DataLite dataLite = new DataLite();
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


                Image icon = new Image(getClass().getResource("image/logo.png").toString());
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

        } catch(Exception event) {
            event.printStackTrace();
        }
    }



    public void login_enter(KeyEvent e) throws SQLException {
        //connect = connectDB();
        password.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        DataLite dataLite = new DataLite();
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


                            Image icon = new Image(getClass().getResource("image/logo.png").toString());
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
                        DataLite dataLite = new DataLite();
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


                            Image icon = new Image(getClass().getResource("image/logo.png").toString());
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
            signup_form.setVisible(true);
            login_form.setVisible(false);

        } else if (event.getSource() == signIn) {
            signup_form.setVisible(false);
            login_form.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
}