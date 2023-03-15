package com.example.projectxpc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public  class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("login");



        primaryStage.setScene(new Scene(root,945, 778));

        //primaryStage.resizableProperty().set(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);

        primaryStage.show();
    }

    //launch method inherited from Application class
    //we need this for launch our application

    public static void main(String[] args) {
        launch(args);
    }
}

