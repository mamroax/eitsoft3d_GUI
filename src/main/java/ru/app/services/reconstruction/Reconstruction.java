package ru.app.services.reconstruction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Reconstruction implements Reconstructed {

    private List<Double> basicMeas = new ArrayList<>();
    private MeasurementHandler measurementHandler;


    @Override
    public List<Double> reconstructionColormap(double[] reconstructionVector, List<Double> colormapValue) {
        initializeMeasurementHandler();

        double[] normalizedVector = measurementHandler.getNormalizedVector(reconstructionVector);
        List<Double> colorValues = new ArrayList<>();

        Arrays.stream(normalizedVector)
                .forEach(element -> {
                    int indexInColormapArray = (int) (element * (colormapValue.size() - 1));
                    double color = colormapValue.get(indexInColormapArray);
                    colorValues.add(color);
                });

        return colorValues;
    }

    @Override
    public double[] reconstructionModel(List<Double> currentMeas, double[][] reconstructionMatrix) {
        if (basicMeasExist()) {
            return processingMeasToVector(currentMeas, reconstructionMatrix);
        } else {
            initializeMeasurementHandler();
            setBasicMeas(currentMeas);
        }

        return new double[0];
    }

    private void initializeMeasurementHandler() {
        if (measurementHandler == null) {
            measurementHandler = new MeasurementHandler();
        }
    }

    private boolean basicMeasExist() {
        return getBasicMeas().size() != 0;
    }

    private List<Double> getBasicMeas() {
        return basicMeas;
    }

    private double[] processingMeasToVector(List<Double> currentMeas, double[][] reconstructionMatrix) {
        identifyMeasWithMinAverage(currentMeas);

        return measurementHandler.multiplyMatrix(getDifferentFormBasicMeasVector(currentMeas),
                reconstructionMatrix).toArray();
    }

    private void identifyMeasWithMinAverage(List<Double> currentMeas) {
        if (getAverage(currentMeas) < getAverage(getBasicMeas())) {
            setBasicMeas(currentMeas);
        }
    }

    private double getAverage(List<Double> meas) {

        return meas
                .stream()
                .flatMapToDouble(DoubleStream::of)
                .average()
                .getAsDouble();
    }

    private void setBasicMeas(List<Double> basicMeas) {
        this.basicMeas.clear();
        this.basicMeas.addAll(basicMeas);
    }

    private List<Double> getDifferentFormBasicMeasVector(List<Double> currentMeas) {
        AtomicInteger index = new AtomicInteger(-1);
        return currentMeas.stream()
                .map(meas -> meas - getBasicMeas().get(index.incrementAndGet()))
                .collect(Collectors.toList());
    }

}
