package com.example.projectxpc;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.net.URL;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;

public class balanceInquiryCtrl implements Initializable {
    @FXML
    private Button backFromBalanceInquiry;
    @FXML
    private Label fullnameBalanceInquiry;
    @FXML
    private Label accountIdBalanceInquiry;
    @FXML
    private Label balanceBalanceInquiry;
    @FXML
    private Label timeLabel;
    @FXML
    private Label dateLabel;

    @FXML
    private Button closeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //live time..

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            timeLabel.setText(" "+currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        //current date

        DateFormat dateFormat = new SimpleDateFormat("E, MMM dd yyyy");
        Date date = new Date();
        dateLabel.setText("  "+dateFormat.format(date));


        backFromBalanceInquiry.setOnAction(new EventHandler<ActionEvent>() {
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

    public  void getBalance(String fullname,String accountId,String balance){

        fullnameBalanceInquiry.setText(fullname);
        accountIdBalanceInquiry.setText(accountId);
        balanceBalanceInquiry.setText("  "+balance+" TK");

    }

}
