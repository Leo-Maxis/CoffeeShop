package org.example.coffeeshop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ApplicationMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginLayout.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Leo Coffee Shop");
        stage.setMinHeight(410);
        stage.setMinWidth(610);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}