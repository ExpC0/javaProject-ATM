package com.example.projectxpc;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;


public class DBconnection {


    //changing scene
    public static void changeScene(ActionEvent event, String fxmlFile, String title, String accountId, int width, int height) {
        Parent root = null;


        if (accountId != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBconnection.class.getResource(fxmlFile));
                root = loader.load();

                // for showing msg with username

                if (fxmlFile == "menu.fxml") {

                    String fullname=null;

                    Connection connection = null;
                    ResultSet resultSet = null;
                    PreparedStatement preparedStatement = null;
                    try {

                        connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
                        preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
                        preparedStatement.setString(1, accountId);
                        resultSet = preparedStatement.executeQuery();

                        while (resultSet.next()) {


                            if (resultSet.getString("accountId").equals(accountId)) {

                                fullname=resultSet.getString("full_name");
                            }

                        }

                    } catch (SQLException e){
                        e.printStackTrace();
                    }finally {
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

                    menuctrl menu = loader.getController();
                    menu.setUserInfo(fullname);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }



        } else {
            try {
                root = FXMLLoader.load(DBconnection.class.getResource(fxmlFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //double casting for getting the scene

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        //stage.resizableProperty().setValue(true);
        //stage.initStyle(StageStyle.UTILITY);

        stage.setScene(new Scene(root, width, height));

        stage.show();


    }


    public static void signUpUser(ActionEvent event, String accountId, String password, String retypePassword, String name, String email, String address, String contact) {
        //for interacting with database

        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        //connecting database
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
            psCheckUserExists = connection.prepareStatement("select * from userinfo1 where accountId = ?");
            psCheckUserExists.setString(1, accountId);
            resultSet = psCheckUserExists.executeQuery();

            //if accountId exists previously taken

            if (resultSet.isBeforeFirst()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input!!");
                alert.setContentText("The Account ID has already been taken!!");
                alert.showAndWait();

            } else if (!Objects.equals(password, retypePassword)) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input!!");
                alert.setContentText("Password didn't match!!");
                alert.showAndWait();
            } else {
                psInsert = connection.prepareStatement("insert into userinfo1 (accountId,password,full_name,address,email,contactNumber) values(?,?,?,?,?,?)");
                psInsert.setString(1, accountId);
                psInsert.setString(2, password);
                psInsert.setString(3, name);
                psInsert.setString(4, address);
                psInsert.setString(5, email);
                psInsert.setString(6, contact);
                psInsert.executeUpdate();

                // creating balance table for new user


                psCheckUserExists = connection.prepareStatement("select * from userBalanceInfo where accountId = ?");
                psCheckUserExists.setString(1, accountId);
                psCheckUserExists.executeQuery();

                psInsert = connection.prepareStatement("insert into userBalanceInfo (accountId,balance) values(?,?)");
                psInsert.setString(1, accountId);
                psInsert.setString(2, String.valueOf(0.0));

                psInsert.executeUpdate();

                psInsert = connection.prepareStatement("create table "+accountId+"(transaction varchar(255),dateAndTime varchar(255));");

                psInsert.executeUpdate();




                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Signup");
                alert.setContentText("Registration done!!! Now login..");
                alert.showAndWait();

                changeScene(event, "login.fxml", "login", null, 945, 778);



            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // free database resources....
        finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
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
    private static String loggedInAccountId =null;
    // handling user login
    public static void userLogin(ActionEvent event, String accountId, String password) {
        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
            preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
            preparedStatement.setString(1, accountId);
            resultSet = preparedStatement.executeQuery();

            if(accountId.isEmpty() && password.isEmpty()){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input!!");
                alert.setContentText("Please Enter Account ID & Password!!");

                alert.showAndWait();
            }else if (accountId.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input!!");
                alert.setContentText("Please Enter AccountID!!");
                alert.showAndWait();

            }else if (password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input!!");
                alert.setContentText("Please Enter Password!!");
                alert.showAndWait();

            } else {
                if (!resultSet.isBeforeFirst()) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid Account!!");
                    alert.setContentText("You have no created Account!!");
                    alert.showAndWait();
                } else {
                    while (resultSet.next()) {
                        String retrievedPassword = resultSet.getString("password");

                        if (retrievedPassword.equals(password)) {

                            changeScene(event, "menu.fxml", "Menu", accountId, 945, 779);
                            loggedInAccountId = accountId;
                        } else {


                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Incorrect password!!");
                            alert.setContentText("Enter Correct Password!!");

                            alert.showAndWait();
                        }


                    }
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


    //declaring global variable for saving some values for using later




    public static void verifySecurityQuestion(ActionEvent event, String accountId, String email) {

        Connection connection = null;
        ResultSet resultSet1 = null;
        ResultSet resultSet2 = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
            preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
            preparedStatement.setString(1, accountId);
            resultSet1 = preparedStatement.executeQuery();

            preparedStatement = connection.prepareStatement("select * from userinfo1 where email = ?");
            preparedStatement.setString(1, email);
            resultSet2= preparedStatement.executeQuery();




            if (!resultSet1.isBeforeFirst()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Account!!");
                alert.setContentText("Account NOT FOUND!!");
                alert.showAndWait();
            } else if (!resultSet2.isBeforeFirst()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Account!!");
                alert.setContentText("Invalid E-mail ID!!");
                alert.showAndWait();
            } else {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reset Password");
                alert.setContentText("Verification Done! Now Change Password!!");
                alert.showAndWait();
                // successfull answer of security ques will set the loggedInUsername as that accountId and can able to change password with that
                loggedInAccountId = accountId;

                changeScene(event,"changePassword.fxml", "Reset Password", null, 753, 728);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet1 != null) {
                try {
                    resultSet1.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (resultSet2 != null) {
                try {
                    resultSet2.close();
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





    public static void changePassword(ActionEvent event, String password, String retypePassword) {

        Connection connection = null;

        PreparedStatement updateInfo = null;



        //connecting database
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");


            if(!Objects.equals(password, retypePassword)){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Input!!");
                alert.setContentText("Password didn't match!!");
                alert.showAndWait();
            }
            else {

                updateInfo = connection.prepareStatement("update userinfo1 set password  = ? where accountId = ?");
                updateInfo.setString(1,password);
                updateInfo.setString(2, loggedInAccountId);
                updateInfo.executeUpdate();


                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reset Password");
                alert.setContentText("Password Changed!!! Now login..");
                alert.showAndWait();

                changeScene(event,"login.fxml","login",null,945,778);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        // free database resources....
        finally{

            if(updateInfo != null){
                try{
                    updateInfo.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }

            if(connection != null){
                try{
                    connection.close();
                }
                catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }



    public static String getLoggedInUser() {
       return loggedInAccountId;
    }

    // loading fxml file and calling function to work with button in another class for showing details

    public static void showAccountDetails(ActionEvent event, String fxmlFile, String title, String accountId, int width, int height) {

        Parent root = null;
        String fullname=null,address=null,email=null,number=null,balance=null;


        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {

            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
            preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
            preparedStatement.setString(1, accountId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                if (resultSet.getString("accountId").equals(accountId)) {

                    fullname=resultSet.getString("full_name");
                    address=resultSet.getString("address");
                    email=resultSet.getString("email");
                    number=resultSet.getString("contactNumber");

               }

            }


            preparedStatement = connection.prepareStatement(("select * from userBalanceInfo where accountId = ?"));
            preparedStatement.setString(1,accountId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                if (resultSet.getString("accountId").equals(accountId)) {

                    balance=resultSet.getString("balance");

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



            try {
                FXMLLoader loader = new FXMLLoader(DBconnection.class.getResource(fxmlFile));
                root = loader.load();

                    // getting control of fxml by object of accountDetailsCtrl class

                    accountDetailsCtrl accountDetailsCtrlObj = loader.getController();

                    //passing parameter to that mentioned class function

                    accountDetailsCtrlObj.getAccountDetails(fullname,accountId,address,email,number,balance);


            } catch (IOException e) {
                e.printStackTrace();
            }


        //double casting for getting the scene

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        //stage.resizableProperty().setValue(Boolean.FALSE);

        stage.setScene(new Scene(root, width, height));
        stage.show();


    }

// extracting data from database for showing in balanceInquiry..
    public static void showBalance(ActionEvent event) {

        Parent root = null;

        String fullname=null,balance=null;

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {

            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
            preparedStatement = connection.prepareStatement("select * from userinfo1 where accountId = ?");
            preparedStatement.setString(1, loggedInAccountId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                if (resultSet.getString("accountId").equals(loggedInAccountId)) {

                    fullname=resultSet.getString("full_name");
                }

            }


            preparedStatement = connection.prepareStatement(("select * from userBalanceInfo where accountId = ?"));
            preparedStatement.setString(1,loggedInAccountId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {


                if (resultSet.getString("accountId").equals(loggedInAccountId)) {

                    balance=resultSet.getString("balance");

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
// now updating fxml file with data and change the scene

        try {
            FXMLLoader loader = new FXMLLoader(DBconnection.class.getResource("balanceInquiry.fxml"));
            root = loader.load();

            // getting control of fxml by object of accountDetailsCtrl class

            balanceInquiryCtrl balanceInquiryCtrlObj = loader.getController();

            //passing parameter to that mentioned class function

            balanceInquiryCtrlObj.getBalance(fullname,loggedInAccountId,balance);


        } catch (IOException e) {
            e.printStackTrace();
        }

        //double casting for getting the scene

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Account Balance");
        //stage.resizableProperty().setValue(Boolean.FALSE);

        stage.setScene(new Scene(root, 770,780));
        stage.show();

    }

    public static void getRecentTransaction(ActionEvent event) {

        Parent root = null;
        String[] left  = new String[10];
        Arrays.fill(left,null);
        String[] right = new String[10];
        Arrays.fill(right,null);

        Connection connection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {

            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "exp", "password");
            preparedStatement = connection.prepareStatement("select * from "+getLoggedInUser()+" order by dateAndTime desc limit 10;");
            resultSet = preparedStatement.executeQuery();

            int i = 0;

          while (resultSet.next()) {

               left[i] = resultSet.getString(1);
               right[i] = resultSet.getString(2);
               i++;

            }
                i = 0;

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
// now updating fxml file with data and change the scene

        try {
            FXMLLoader loader = new FXMLLoader(DBconnection.class.getResource("recentTransaction.fxml"));
            root = loader.load();

            // getting control of fxml by object of recentTransactionCtrl class

            recentTransactionCtrl recentTransactionCtrlObj = loader.getController();

            //passing parameter to that mentioned class function

            recentTransactionCtrlObj.showRecentTransaction(left[0],right[0],left[1],right[1],left[2],right[2],left[3],right[3],left[4],right[4],left[5],right[5],left[6],right[6],left[7],right[7],left[8],right[8],left[9],right[9]);


        } catch (IOException e) {
            e.printStackTrace();
        }

        //double casting for getting the scene

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Recent Transaction");
        //stage.resizableProperty().setValue(Boolean.FALSE);

        stage.setScene(new Scene(root, 980,781));
        stage.show();

    }
}

