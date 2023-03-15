package com.example.projectxpc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;


public class accountDetailsCtrl implements Initializable {

    @FXML
    private Button backFromAccountDetails;
    @FXML
    private  Label accountHolderLabel;
    @FXML
    private  Label accountIdLabel;
    @FXML
    private  Label addressLabel;
    @FXML
    private  Label emailLabel;
    @FXML
    private  Label contactNumberLabel;
    @FXML
    private  Label balanceLabel;

    @FXML
    private Button closeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {





        backFromAccountDetails.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event, "menu.fxml", "Menu",DBconnection.getLoggedInUser(), 945, 779);

            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

    }


    public  void getAccountDetails(String fullname, String accountId, String address, String email, String number, String balance){

        accountHolderLabel.setText(fullname);
        accountIdLabel.setText(accountId);
        addressLabel.setText(address);
        emailLabel.setText(email);
        contactNumberLabel.setText(number);
        balanceLabel.setText("  "+balance+" TK");

    }






}
