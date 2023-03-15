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
;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class depositCtrl implements Initializable {

    @FXML
    private Button backFromDepositButton;
    @FXML
    private Button confirmDepositButton;
    @FXML
    private PasswordField passwordDeposit;
    @FXML
    private TextField amountDepositField;

    @FXML
    private Button closeButton;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        backFromDepositButton.setOnAction(new EventHandler<ActionEvent>() {
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


        confirmDepositButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!passwordDeposit.getText().isEmpty() && !amountDepositField.getText().isEmpty()) {

                    String balance = null;
                    double acBalance,depositAmount;
                    depositAmount = Double.parseDouble(amountDepositField.getText());

                    String accountId = DBconnection.getLoggedInUser();
                    Connection connection = null;
                    ResultSet resultSet = null;
                    PreparedStatement preparedStatement = null;

                    //for checking password...
                    try {
                        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
                        preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
                        preparedStatement.setString(1, accountId);
                        resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {
                            String retrievedPassword = resultSet.getString("password");

                            if (retrievedPassword.equals(passwordDeposit.getText())) {

                                // here for deposit money after verify password

                                preparedStatement = connection.prepareStatement(("select * from userBalanceInfo where accountId = ?"));
                                preparedStatement.setString(1,DBconnection.getLoggedInUser());
                                resultSet = preparedStatement.executeQuery();

                                while (resultSet.next()) {


                                    if (resultSet.getString("accountId").equals(accountId)) {

                                        balance=resultSet.getString("balance");

                                    }

                                }


                                acBalance = Double.parseDouble(balance);
                                acBalance = acBalance + depositAmount;

                                if(depositAmount<500){

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Deposit Less Money!");
                                    alert.setContentText("You can't deposit less than 500 TK...");
                                    alert.showAndWait();
                                }
                                else if(depositAmount%500 != 0){
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Invalid Amount");
                                    alert.setContentText("You can only deposit multiple of 500 TK");
                                    alert.showAndWait();
                                }
                                else if(depositAmount>=500) {

                                    preparedStatement = connection.prepareStatement("update userBalanceInfo set balance = ? where accountId = ?");
                                    preparedStatement.setString(1, String.valueOf(acBalance));
                                    preparedStatement.setString(2, accountId);

                                    preparedStatement.executeUpdate();

                                    // for making recent transaction..

                                    LocalDateTime myDateObj = LocalDateTime.now();
                                    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                                    String formattedDate = myDateObj.format(myFormatObj);



                                    String info = depositAmount+" TK deposited to your A/C : "+accountId+" on ";

                                    preparedStatement = connection.prepareStatement("insert into "+accountId+"(transaction,dateAndTime) values(?,?);");
                                    preparedStatement.setString(1, info);
                                    preparedStatement.setString(2, formattedDate);

                                    preparedStatement.executeUpdate();

                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Money Deposited!");
                                    alert.setContentText("Deposited Successfully!! Go back to main Menu..");
                                    alert.showAndWait();

                                    DBconnection.changeScene(event, "menu.fxml", "Menu", accountId, 945, 779);
                                }

                            } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Incorrect password!!");
                                alert.setContentText("Enter Correct Password!!");
                                alert.showAndWait();
                            }


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

