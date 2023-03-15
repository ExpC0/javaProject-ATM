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
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class transferCtrl implements Initializable {
    @FXML
    private Button transferMoneyButton;
    @FXML
    private Button backFromTransfer;
    @FXML
    private TextField transferAmount;
    @FXML
    private TextField receiverId;
    @FXML
    private PasswordField passwordToTransfer;

    @FXML
    private Button closeButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    backFromTransfer.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            DBconnection.changeScene(event,"menu.fxml","Menu",DBconnection.getLoggedInUser(),945,779);
        }
    });

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });


    transferMoneyButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (!receiverId.getText().isEmpty() && !transferAmount.getText().isEmpty() && !passwordToTransfer.getText().isEmpty()) {

                String balance = null,receiver=null,sender = null,senderPassword=null;
                double moneyTransfer;
                moneyTransfer = Double.parseDouble(transferAmount.getText());
                receiver = receiverId.getText();
                sender = DBconnection.getLoggedInUser();
                Connection connection = null;
                ResultSet resultSet = null;
                PreparedStatement preparedStatement = null;

                //for checking password...
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");

                    preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
                    preparedStatement.setString(1, sender);
                    resultSet = preparedStatement.executeQuery();

                    while(resultSet.next()){
                        if(resultSet.getString("password").equals(passwordToTransfer.getText()))
                        {
                            senderPassword = passwordToTransfer.getText();
                        }
                    }

                    preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
                    preparedStatement.setString(1, receiver);
                    resultSet = preparedStatement.executeQuery();


                    if(!resultSet.isBeforeFirst()){

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Receiver ID");
                        alert.setContentText("Receiver Account doesn't exist!!");
                        alert.showAndWait();
                    }
                    else if (moneyTransfer<500){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Amount");
                        alert.setContentText("You can't transfer less than 500 TK");
                        alert.showAndWait();
                    }
                    else if(moneyTransfer%500 != 0){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Amount");
                        alert.setContentText("You can only transfer multiple of 500 TK");
                        alert.showAndWait();
                    }
                    else if(Objects.equals(receiver, sender)){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Input");
                        alert.setContentText("Please input correct receiver A/C");
                        alert.showAndWait();
                    }
                    else if(Objects.equals(senderPassword, passwordToTransfer.getText())) {
                        double currentBalance = 0;

                        preparedStatement = connection.prepareStatement("select * from userBalanceInfo where accountId = ?");
                        preparedStatement.setString(1, receiver);
                        resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            if (resultSet.getString("accountId").equals(receiver)) {

                                currentBalance = Double.parseDouble(resultSet.getString("balance"));

                            }

                        }

                        currentBalance = currentBalance + moneyTransfer;


                        preparedStatement = connection.prepareStatement("update userBalanceInfo set balance = ? where accountId = ?");
                        preparedStatement.setString(1, String.valueOf(currentBalance));
                        preparedStatement.setString(2, receiver);
                        preparedStatement.executeUpdate();


                        preparedStatement = connection.prepareStatement("select * from userBalanceInfo where accountId = ?");
                        preparedStatement.setString(1, sender);
                        resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {

                            if (resultSet.getString("accountId").equals(sender)) {

                                currentBalance = Double.parseDouble(resultSet.getString("balance"));

                            }

                        }
                        if (currentBalance >= moneyTransfer) {

                            currentBalance = currentBalance - moneyTransfer;

                            preparedStatement = connection.prepareStatement("update userBalanceInfo set balance = ? where accountId = ?");
                            preparedStatement.setString(1, String.valueOf(currentBalance));
                            preparedStatement.setString(2, sender);
                            preparedStatement.executeUpdate();


                            LocalDateTime myDateObj = LocalDateTime.now();
                            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                            String formattedDate = myDateObj.format(myFormatObj);


                            String info = moneyTransfer+" TK transferred to the A/C : "+receiver+" on ";

                            preparedStatement = connection.prepareStatement("insert into "+sender+"(transaction,dateAndTime) values(?,?);");
                            preparedStatement.setString(1, info);
                            preparedStatement.setString(2, formattedDate);

                            preparedStatement.executeUpdate();

                             info = moneyTransfer+" TK received from the A/C : "+sender+" on ";

                            preparedStatement = connection.prepareStatement("insert into "+receiver+"(transaction,dateAndTime) values(?,?);");
                            preparedStatement.setString(1, info);
                            preparedStatement.setString(2, formattedDate);

                            preparedStatement.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Money Sent!");
                            alert.setContentText("Successfully Transfered " + moneyTransfer + " TK" + " to A/C: " + receiver + "\n" + " Back to Menu");
                            alert.showAndWait();

                            DBconnection.changeScene(event,"menu.fxml","Menu",DBconnection.getLoggedInUser(),945,779);


                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Lack of Deposit!");
                            alert.setContentText("You don't have enough balance!!");
                            alert.showAndWait();
                        }
                    }
                    else {

                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Invalid Password");
                        alert.setContentText("You entered a wrong password!!");
                        alert.showAndWait();

                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    if (resultSet != null) {
                        try {
                            resultSet.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (preparedStatement != null) {
                        try {
                            preparedStatement.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        try {
                            connection.close();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
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
