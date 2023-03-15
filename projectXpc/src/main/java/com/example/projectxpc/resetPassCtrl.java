package com.example.projectxpc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class resetPassCtrl implements Initializable {
    @FXML
    private Button BackFromSecurityQues;
    @FXML
    private Button proceedToChangePassword;
    @FXML
    private TextField accountIdQuery;
    @FXML
    private TextField emailQuery;
    @FXML
    private Button closeButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BackFromSecurityQues.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"login.fxml","login",null,945,778);
            }
        });
        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });



        //verifying security question for changing the password

        proceedToChangePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(!accountIdQuery.getText().isEmpty() && !emailQuery.getText().isEmpty()) {
                    DBconnection.verifySecurityQuestion(event, accountIdQuery.getText(), emailQuery.getText());
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Please Fill up all information!");
                    alert.setTitle("Invalid Info!!");
                    alert.showAndWait();
                }


            }
        });
    }
}
