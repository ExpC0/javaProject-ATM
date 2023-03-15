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

public class recentTransactionCtrl implements Initializable {
    @FXML
    private Button backFromRecentTransaction;

    @FXML
    private Button closeButton;
    @FXML
    private Label left1,left2,left3,left4,left5,left6,left7,left8,left9,left10,right1,right2,right3,right4,right5,right6,right7,right8,right9,right10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backFromRecentTransaction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                DBconnection.changeScene(event, "menu.fxml", "Menu", DBconnection.getLoggedInUser(), 945, 779);
            }
        });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });


    }

    public void showRecentTransaction(String l1,String r1,String l2,String r2,String l3,String r3,String l4,String r4,String l5,String r5,String l6,String r6,String l7,String r7,String l8,String r8,String l9,String r9,String l10,String r10)
        {
            if(l1 != null) {
                left1.setText("1. " + l1);
                right1.setText(r1);
            }else {
                left1.setText(null);
                right1.setText(null);
            }
            if(l2 != null) {
                left2.setText("2. " + l2);
                right2.setText(r2);
            }else{
                left2.setText(null);
                right2.setText(null);
            }
            if(l3 != null) {
                left3.setText("3. " + l3);
                right3.setText(r3);
            }else{
                left3.setText(null);
                right3.setText(null);
            }
            if(l4 != null) {
                left4.setText("4. " + l4);
                right4.setText(r4);
            }else{
                left4.setText(null);
                right4.setText(null);
            }
            if(l5 != null) {
                left5.setText("5. " + l5);
                right5.setText(r5);
            }else{
                left5.setText(null);
                right5.setText(null);
            }
            if(l6 != null) {
                left6.setText("6. " + l6);
                right6.setText(r6);
            }else{
                left6.setText(null);
                right6.setText(null);
            }
            if(l7 != null) {
                left7.setText("7. " + l7);
                right7.setText(r7);
            }else{
                left7.setText(null);
                right7.setText(null);
            }
            if(l8 != null) {
                left8.setText("8. " + l8);
                right8.setText(r8);
            }else{
                left8.setText(null);
                right8.setText(null);
            }
            if(l9 != null) {
                left9.setText("9. " + l9);
                right9.setText(r9);
            }else{
                left9.setText(null);
                right9.setText(null);
            }
            if(l10 != null) {
                left10.setText("10. " + l10);
                right10.setText(r10);
            }else{
                left10.setText(null);
                right10.setText(null);
            }


        }
    }

