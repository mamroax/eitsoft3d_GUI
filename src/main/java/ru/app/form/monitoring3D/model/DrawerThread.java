package ru.app.form.monitoring3D.model;

import javafx.application.Platform;
import javafx.scene.paint.Paint;
import ru.app.factories.ServicesFactory;
import ru.app.form.monitoring3D.controllers.Controller3D;
import ru.app.form.monitoring3D.controllers.ImageController3D;
import ru.app.services.builder.ModelBuilding;
import ru.app.services.reconstruction.Reconstructed;
import ru.app.utils.ColorSupplier;

import java.util.List;
import java.util.stream.Collectors;

public class DrawerThread extends Thread {
    private Reconstructed reconstructed;

    private ModelBuilding modelBuilding;

    private ImageController3D imageController;

    private List<Double> dataList;

    public DrawerThread(Controller3D controller3D, int indexImage) {
        reconstructed = ServicesFactory.getReconstruction();
        modelBuilding = controller3D.getBuiltStructure();
        imageController = new ImageController3D(controller3D, indexImage);
    }

    @Override
    public void run() {
        super.run();
        List<Double> data = decreaseValues(dataList);
        updateImage(getColorsPaint(data));
        waitDrawingImageAndChart();
    }

    private List<Double> decreaseValues(List<Double> data) {
        return data.stream().map(meas -> meas / Math.pow(10, 6)).collect(Collectors.toList());
    }

    private List<Paint> getColorsPaint(List<Double> data) {
        return getColorsValue(data).stream().map(data1 -> ColorSupplier.getColor(data1.intValue())).collect(Collectors.toList());
    }

    private List<Double> getColorsValue(List<Double> dataMeas) {
        double[] recData = reconstructed.reconstructionModel(dataMeas,
                modelBuilding.getModel().getRecMatrix());

        return reconstructed.reconstructionColormap(recData, modelBuilding.getColormap().getValuesColormap());
    }

    private void updateImage(List<Paint> measData) {
        Platform.runLater(() -> {
            imageController.drawImage(measData);
            continueUpdateDataMeas();
        });
    }

    private synchronized void continueUpdateDataMeas() {
        this.notify();
    }

    private synchronized void waitDrawingImageAndChart() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setDataList(List<Double> dataList) {
        this.dataList = dataList;
    }

}
