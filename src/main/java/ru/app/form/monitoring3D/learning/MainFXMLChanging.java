package ru.app.form.monitoring3D.learning;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFXMLChanging extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Scene sceneOne = new Scene(FXMLLoader.load(getClass().getResource("viewOne.fxml")));
        Scene sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("viewTwo.fxml")));

        stage.setScene(sceneOne);
        stage.show();

        sceneOne.setOnMouseClicked(e -> {
            stage.setScene(sceneTwo);
        });

        sceneTwo.setOnMouseClicked(e -> {
            stage.setScene(sceneOne);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
