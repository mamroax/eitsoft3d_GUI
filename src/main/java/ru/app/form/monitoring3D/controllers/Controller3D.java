package ru.app.form.monitoring3D.controllers;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import ru.app.factories.ServicesFactory;
import ru.app.form.monitoring3D.model.Drawer;
import ru.app.properties.PropertiesManagerModel;
import ru.app.properties.PropertiesModel;
import ru.app.resources.ResourceManager;
import ru.app.services.builder.ModelBuilding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

public class Controller3D {
    private Drawer drawer;
    private PropertiesModel propertiesModel;
    private ModelBuilding modelBuilding;
    public Group groupPolygons;
    private AnchorPane groups;
    public Controller3D(AnchorPane groups) {
        this.groups = groups;
        buildModel();
        drawer = new Drawer(this);
        try {
            TimeUnit.SECONDS.sleep(0); // стояло значение 7 вместо 0
            startDrawing();
        }
        catch (Exception e) {

        }
    }
    public void startDrawing() throws NoSuchMethodException {
        startDaemonThread(drawer.getClass().getMethod("startDraw"));
    }
//
//    public void startDrawing() throws NoSuchMethodException {
//        controller.startDrawing();
//        stopButtonActive();
//    }
//
//    public void stopDrawing() throws NoSuchMethodException {
//        controller.stopDrawing();
//        startButtonActive();
//    }
//
//    private void stopButtonActive() {
//        turnOffAndTurnOnButtons(stopButton, startButton);
//        updateModelButton.setDisable(true);
//        updateModelButton.setOpacity(0.5);
//    }
//
//
//    private void startButtonActive() {
//        turnOffAndTurnOnButtons(startButton, stopButton);
//        updateModelButton.setDisable(false);
//        updateModelButton.setOpacity(1);
//    }
//
//    private void turnOffAndTurnOnButtons(Node turnOn, Node turnOff) {
//        turnOn.setDisable(false);
//        turnOn.setOpacity(1);
//
//        turnOff.setDisable(true);
//        turnOff.setOpacity(0.5);
//    }
//
//    public void updateParameters() {
//        controller.updateParams();
//    }
//    public void setStage(Stage stage) {
//        this.stage = stage;
//        controller.setStage(stage);
//    }

    public ModelBuilding getBuiltStructure() {
        return modelBuilding;
    }


    private void startDaemonThread(Method method) {
        Thread drawingThread = new Thread(() -> {
            try {
                method.invoke(drawer);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        });

        drawingThread.setDaemon(true);
        drawingThread.start();
    }

    private void buildModel() {
        propertiesModel = new PropertiesManagerModel().getPropertiesModel();
        modelBuilding = ServicesFactory.getBuilder();

        modelBuilding.buildModel(propertiesModel.getColormapPath(),
                propertiesModel.getModelPath(), Double.parseDouble(propertiesModel.getValueIncrease()));
    }
    private void rebuildModel() {
        if (!propertiesModel.equals(new PropertiesManagerModel().getPropertiesModel())) {
            groupPolygons.getChildren().clear();
            buildModel();
            drawer = new Drawer(this);

        } else {
            notificationBuild();
        }

    }
    private void notificationBuild() {
        Notifications.create()
                .title(ResourceManager.getString("error_update_model"))
                .text(ResourceManager.getString("not_found_new_params"))
                .hideAfter(Duration.seconds(3))
                .position(Pos.BOTTOM_RIGHT)
                .darkStyle()
                .show();
    }
    public Drawer getDrawer(){
        return drawer;
    }

    public void setGroups(AnchorPane groups) {
        this.groups = groups;
    }

    public AnchorPane getGroups() {
        return groups;
    }
}
