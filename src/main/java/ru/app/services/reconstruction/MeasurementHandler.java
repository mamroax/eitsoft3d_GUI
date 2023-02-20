package ru.app.services.reconstruction;

import cern.colt.matrix.DoubleMatrix1D;
import cern.colt.matrix.DoubleMatrix2D;
import cern.colt.matrix.impl.DenseDoubleMatrix1D;
import cern.colt.matrix.impl.DenseDoubleMatrix2D;
import cern.colt.matrix.linalg.Algebra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MeasurementHandler {
    private static final int SIZE_ONE_SERIES_VALUES = 200;
    public List<double[]> values = new ArrayList<>();
    private Double maxValue;

    DoubleMatrix1D multiplyMatrix(List<Double> vector, double[][] recMatrix) {
        DoubleMatrix2D matrixRec = new DenseDoubleMatrix2D(recMatrix);
        DoubleMatrix1D matrixVector = new DenseDoubleMatrix1D(getPrimitiveVector(vector));

        return new Algebra().mult(new Algebra().transpose(matrixRec), matrixVector);
    }

    private double[] getPrimitiveVector(List<Double> vector) {
        return vector.stream()
                .mapToDouble(Double::doubleValue)
                .toArray();
    }

    double[] getNormalizedVector(double[] incomingElements) {
        addValues(incomingElements);
        double maxValue = getMaxValue(incomingElements);

        return Arrays.stream(incomingElements)
                .map(element -> (element + maxValue) /
                        (2 * maxValue))
                .toArray();
    }

    private void addValues(double[] value) {
        if (values.size() < SIZE_ONE_SERIES_VALUES) {
            values.add(value);
        } else {
            values.remove(0);
            values.add(value);
        }

    }

    private double getMaxValue(double[] incomingElements) {
        if (maxValue == null || valueNotInList(maxValue)) {
            maxValue = Arrays.stream(incomingElements).summaryStatistics().getMax();
        }

        for (double[] value : values) {
            double max = getMax(value);

            if (maxValue < max) {
                maxValue = max;
            }
        }

        return maxValue;
    }

    private double getMax(double[] incomingElements) {
        double max = 0;

        for (Double element : incomingElements) {
            if (Math.abs(element) > max) {
                max = Math.abs(element);
            }
        }

        return max;
    }

    private boolean valueNotInList(double commonValue) {
        for (double[] value : values) {
            for (Double v : value) {
                if (commonValue == v) {
                    return false;
                }
            }
        }

        return true;
    }

}
