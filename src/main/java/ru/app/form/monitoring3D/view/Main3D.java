package ru.app.form.monitoring3D.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Date;
import ru.app.form.monitoring3D.controllers.Controller3D;
import static java.lang.System.out;

public class Main3D{
    /*
    @Override
    public void start(Stage primaryStage) {
        long time = System.currentTimeMillis();

        MonitoringWindow3D view = new MonitoringWindow3D(); // создаем объект класса MonitoringWindow3D(что за класс?)
        Controller3D controller3D = new Controller3D(view.getRoot()); //
        Scene scene = new Scene(view.getRoot(), 300, 300, true);
        primaryStage.setScene(scene);
        view.buildScene(scene);
        primaryStage.show();

        System.out.println("Время работы приложения в милисекундах "+(System.currentTimeMillis() - time)); // это я так считаю время работы приложения kekw
    }
    */

    public void kek(){
        Stage primaryStage = new Stage();
        long time = System.currentTimeMillis();

        MonitoringWindow3D view = new MonitoringWindow3D(); // создаем объект класса MonitoringWindow3D(что за класс?)
        Controller3D controller3D = new Controller3D(view.getRoot());
        Scene scene = new Scene(view.getRoot(), 300, 300, true);
        primaryStage.setScene(scene);
        view.buildScene(scene);
        primaryStage.show();

        System.out.println("Время работы приложения в милисекундах "+(System.currentTimeMillis() - time)); // это я так считаю время работы приложения kekw
    }

//    public static void main(String[] args) {
//        launch(args);
//    }

}
