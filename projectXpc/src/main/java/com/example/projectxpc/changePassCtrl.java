package com.example.projectxpc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class changePassCtrl implements Initializable {
    @FXML
    private Button backChangePassword;
    @FXML
    private PasswordField passwordChangeField;
    @FXML
    private PasswordField retypePasswordChangeField;
    @FXML
    private Button changePasswordButton;

    @FXML
    private Button closeButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backChangePassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"resetPassword.fxml","Reset Password",null,753,728);
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });


        changePasswordButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!passwordChangeField.getText().isEmpty() && !retypePasswordChangeField.getText().isEmpty()) {
                    DBconnection.changePassword(event, passwordChangeField.getText(), retypePasswordChangeField.getText());
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
