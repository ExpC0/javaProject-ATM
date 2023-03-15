package com.example.projectxpc;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class menuctrl implements Initializable {
    @FXML
    private Button logoutButton;
    @FXML
    private Label menuWcMsg;
    @FXML
    private Button accountDetailsButton;

    @FXML
    private Button changePasswordMenuButton;
    @FXML
    private Button balanceInquiryButton;
    @FXML
    private Button depositButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button transferMenuButton;

   @FXML
   private  Button recentTransactionButton;
    @FXML
    private Button closeButton;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            DBconnection.changeScene(event,"login.fxml","login",null,945,778);
            }
        });

        accountDetailsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                //for getting account details from database and pass those data into another controller

                DBconnection.showAccountDetails(event,"accountDetails.fxml","Account Information",DBconnection.getLoggedInUser(),1050,770);
            }
        });

        changePasswordMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"changePasswordFromMenu.fxml","Reset Password",null,753,728);
            }
        });

        balanceInquiryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.showBalance(event);
            }
        });

        depositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"deposit.fxml","Deposit Money",null,750,690);
            }
        });

        withdrawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"moneyWithdraw.fxml","Withdraw Money",null,685,690);
            }
        });

        transferMenuButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.changeScene(event,"transfer.fxml","Money Transfer",null,770,798);
            }
        });


        recentTransactionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DBconnection.getRecentTransaction(event);
            }
        });
    }

    public void setUserInfo(String fullname) {
        menuWcMsg.setText(" Hi " + fullname + " , Welcome to Fast & Secure ATM! ");

    }





    }






