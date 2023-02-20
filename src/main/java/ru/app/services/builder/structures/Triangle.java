package ru.app.services.builder.structures;

import java.util.List;

public class Triangle {
    private List<Double> coordinate;
    private double color;
    private double meas;

    public Triangle(List<Double> coordinate) {
        this.coordinate = coordinate;
    }

    public List<Double> getCoordinate() {
        return coordinate;
    }

    public void setColor(double color) {
        this.color = color;
    }

    public double getColor() {
        return color;
    }

    public double getMeas() {
        return meas;
    }

    public void setMeas(double meas) {
        this.meas = meas;
    }
}
