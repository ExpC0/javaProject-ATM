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


public class changePassMenuCtrl implements Initializable {
    @FXML
    private Button backChangePasswordFromMenu;

    @FXML
    private  Button changePasswordFromMenuButton;

    @FXML
    private PasswordField passwordChangeFromMenuField;
    @FXML
    private PasswordField retypePasswordChangeFromMenuField;


    @FXML
    private Button closeButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backChangePasswordFromMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event, "menu.fxml", "Menu",DBconnection.getLoggedInUser(), 945, 779);

            }
        });

        changePasswordFromMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!passwordChangeFromMenuField.getText().isEmpty() && !retypePasswordChangeFromMenuField.getText().isEmpty()) {
                    DBconnection.changePassword(event, passwordChangeFromMenuField.getText(), retypePasswordChangeFromMenuField.getText());
                }
                else{
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


    }
}
