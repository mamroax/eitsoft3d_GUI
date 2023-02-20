package ru.app.form.monitoring3D.view;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import java.awt.*;
import ru.app.utils.DeviceBuilder;

public class GUI extends Application{

    //public static void main(String[] args) {

     //   launch(args);
    //}
//
    @Override
    public void start(Stage stage) {

        Button btn = new Button();
        btn.setText("Click!");

        Label lbl = new Label();
        TextField textField = new TextField();
        textField.setPrefColumnCount(11);

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10.0, 10.0, (Node) textField, (Node) btn);
        Scene scene = new Scene(root, 250, 200);

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Main3D kek = new Main3D();
                Stage stage1 = new Stage();
                kek.start(stage1);
                stage1.setTitle(root.getAccessibleText());
            }
        });

        /*Group root = new Group(btn);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.setTitle("Hello JavaFX");
        stage.setWidth(250);
        stage.setHeight(200);

        stage.show();*/



        stage.setScene(scene);
        stage.setTitle("TextField in JavaFX");
        stage.show();
    }
}