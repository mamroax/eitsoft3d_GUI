package ru.app.services.reconstruction;

import java.util.List;

public interface Reconstructed {
    
    List<Double> reconstructionColormap(double[] reconstructionVector, List<Double> colormapValue);
    double[] reconstructionModel(List<Double> currentMeas, double[][] reconstructionMatrix);
}
