package com.example.projectxpc;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public  class loginctrl implements  Initializable{

    @FXML
    private Button enterButton;
    @FXML
    private TextField accountIdLogin;
    @FXML
    private  TextField passwordLogin;
    @FXML
    private Button createAcButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button forgotPasswordButton;
    @FXML
    private Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        enterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.userLogin(event, accountIdLogin.getText(),passwordLogin.getText());
            }
        });

        createAcButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBconnection.changeScene(event,"signup.fxml","Signup", accountIdLogin.getText(),800,772);
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });


        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Platform.exit();
            }
        });

        forgotPasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"resetPassword.fxml","Reset Password",null,753,728);

            }
        });



    }
}



