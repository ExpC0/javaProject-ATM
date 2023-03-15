package com.example.projectxpc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class signupctrl implements Initializable{
    @FXML
    private Button registerSignup;
    @FXML
    private Button loginSignup;
    @FXML
    private TextField accountIdSignup;
    @FXML
    private TextField fullnameSignup;
    @FXML
    private PasswordField passwordSignup;
    @FXML
    private PasswordField retypePasswordSignup;
    @FXML
    private TextField addressSignup;
    @FXML
    private TextField emailSignup;
    @FXML
    private TextField contactSignup;
    @FXML
    private Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    registerSignup.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if(!accountIdSignup.getText().isEmpty() && !passwordSignup.getText().isEmpty() && !retypePasswordSignup.getText().isEmpty() && !fullnameSignup.getText().isEmpty() && !addressSignup.getText().isEmpty() && !emailSignup.getText().isEmpty() && !contactSignup.getText().isEmpty())
            {
                DBconnection.signUpUser(event, accountIdSignup.getText(),passwordSignup.getText(),retypePasswordSignup.getText(),fullnameSignup.getText(),emailSignup.getText(),addressSignup.getText(),contactSignup.getText());
            }
            else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please Fill up all information!");
                alert.setTitle("Invalid Info!!");
                alert.showAndWait();
            }
        }
    });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

    loginSignup.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            DBconnection.changeScene(event,"login.fxml","login",null,945,778);
        }
    });

    }
}
