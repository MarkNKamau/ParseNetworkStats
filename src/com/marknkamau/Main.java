package com.marknkamau;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("Parse Net Stats");
        primaryStage.setScene(new Scene(root, 650, 550));
        primaryStage.setMinWidth(650);
        primaryStage.setMinHeight(450);
        primaryStage.setMaxWidth(800);
        primaryStage.setMaxHeight(750);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
