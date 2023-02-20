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
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.awt.*;
import ru.app.utils.DeviceBuilder;

public class GUI extends Application{

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Button btn = new Button();
        btn.setText("Нажми!");

        // хочу присрать сюда лейблы и присру(никто меня не остановит)
        Label label1 = new Label("Количество поясов");
        Label label2 = new Label("Форма модели грудной клетки");
        Label label3 = new Label("Тип модели");
        Label label4 = new Label("ID продукта");
        Label label5 = new Label("Номер устройства");
        Label label6 = new Label("Размер модели(масштабный коэффициент)");
        Label label7 = new Label("Частота тока");
        Label label8 = new Label("Цветовая карта");
        Label label9 = new Label("ID вендора");
        Label label10 = new Label("амплитуда тока");

        ObservableList<Integer> numberOfBelts = FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,
                10,11,12,13,14,15,16,17,18,19,20);
        ComboBox<Integer> numberOfBeltsBox = new ComboBox<Integer>(numberOfBelts);
        numberOfBeltsBox.setValue(1); // устанавливаем выбранный элемент по умолчанию

        ObservableList<String> chestShape = FXCollections.observableArrayList("a2c.json", "a2t2.json",
                "c2c.json", "c2t2.json", "d2c.json", "d2t2.json", "f2c.json", "f2t2.json", "j2c.json");
        ComboBox<String> chestShapeBox = new ComboBox<String>(chestShape);
        chestShapeBox.setValue("thorax");

        ObservableList<String> modelType = FXCollections.observableArrayList("thorax", "circle", "square");
        ComboBox<String> modelTypeBox = new ComboBox<String>(modelType);
        modelTypeBox.setValue("thorax");

        ObservableList<String> productID = FXCollections.observableArrayList("374b");
        ComboBox<String> productIDBox = new ComboBox<String>(productID);
        productIDBox.setValue("374b");

        ObservableList<String> deviceNumber = FXCollections.observableArrayList("6&amp;19C2CE46&amp;2&amp;0002");
        ComboBox<String> deviceNumberBox = new ComboBox<String>(deviceNumber);
        deviceNumberBox.setValue("6&amp;19C2CE46&amp;2&amp;0002");

        ObservableList<Double> modelSize = FXCollections.observableArrayList(0.8, 0.85, 0.9, 0.95, 1.0, 1.05,
        1.1, 1.15, 1.2);
        ComboBox<Double> modelSizeBox = new ComboBox<Double>(modelSize);
        modelSizeBox.setValue(1.0); // устанавливаем выбранный элемент по умолчанию

        ObservableList<Integer> frequencyCurrent = FXCollections.observableArrayList(50, 100, 150, 200, 250,
                300, 350, 400, 500, 600, 700, 800, 900, 1000);
        ComboBox<Integer> frequencyCurrentBox = new ComboBox<Integer>(frequencyCurrent);
        frequencyCurrentBox.setValue(200); // устанавливаем выбранный элемент по умолчанию

        ObservableList<String> colorMap = FXCollections.observableArrayList("black_red", "blue_black_red",
                "blue_red", "blue_white_red", "blue_yellow", "copper", "draeger", "draeger-2009",
                "draeger-difference", "draeger-tidal", "grayscale", "grayscale-inverse", "jet", "jetair",
                "polar_colours", "swisstom", "timpel");
        ComboBox<String> colorMapBox = new ComboBox<String>(colorMap);
        colorMapBox.setValue("draeger-2009");

        ObservableList<String> vendorID = FXCollections.observableArrayList("0483");
        ComboBox<String> vendorIDBox = new ComboBox<String>(vendorID);
        vendorIDBox.setValue("0483"); // устанавливаем выбранный элемент по умолчанию

        ObservableList<Double> amplitudeCurrent = FXCollections.observableArrayList(0.8, 0.85, 0.9, 0.95, 1.0,
                1.05, 1.1, 1.15, 1.2);
        ComboBox<Double> amplitudeCurrentBox = new ComboBox<Double>(amplitudeCurrent);
        amplitudeCurrentBox.setValue(1.0); // устанавливаем выбранный элемент по умолчанию

//        TextField textField = new TextField();
//        textField.setPrefColumnCount(20);
//        textField.setText("Количество поясов");
//
//        TextField textField1 = new TextField();
//        textField1.setPrefColumnCount(20);
//        textField1.setText("Форма модели грудной клетки");
//
//        TextField textField2 = new TextField();
//        textField2.setPrefColumnCount(20);
//        textField2.setText("Тип модели");
//
//        TextField textField3 = new TextField();
//        textField3.setPrefColumnCount(20);
//        textField3.setText("Айди продукта");
//
//        TextField textField4 = new TextField();
//        textField4.setPrefColumnCount(20);
//        textField4.setText("Номер устройства");
//
//        TextField textField5 = new TextField();
//        textField5.setPrefColumnCount(20);
//        textField5.setText("Размер модели");
//
//        TextField textField6 = new TextField();
//        textField6.setPrefColumnCount(20);
//        textField6.setText("Частота тока");
//
//        TextField textField7 = new TextField();
//        textField7.setPrefColumnCount(20);
//        textField7.setText("Цветовая карта");
//
//        TextField textField8 = new TextField();
//        textField8.setPrefColumnCount(20);
//        textField8.setText("айди вендора");
//
//        TextField textField9 = new TextField();
//        textField9.setPrefColumnCount(20);
//        textField9.setText("амплитуда тока");

        FlowPane root = new FlowPane(Orientation.VERTICAL,10.0, 10.0, (Node) label1, numberOfBeltsBox,
                label2, chestShapeBox, label3, modelTypeBox, label4, productIDBox, label5, deviceNumberBox,
                label6, modelSizeBox, label7, frequencyCurrentBox, label8, colorMapBox, label9, vendorIDBox,
                label10, amplitudeCurrentBox, btn);
        //FlowPane root = new FlowPane(Orientation.HORIZONTAL, 10.0, 10.0, (Node) textField, textField1,
        //        textField2, textField3, textField4, textField5, textField6, textField7, textField8, textField9,
        //        numberOfBeltsBox, (Node) btn);
        Scene scene = new Scene(root, 400, 600);

        btn.setOnAction(new EventHandler<ActionEvent>() { // вся магия будет тут происходить
            // моя идея состоит в том, чтобы создать несколько выпадающих списков с параметрами модели
            // (заданых в статическом классе) и при выборе из графического интерфейса брать один из них
            // и параметры из него передавать в класс, который рисует модель

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