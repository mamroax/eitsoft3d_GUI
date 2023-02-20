package ru.app.services.builder.structures;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model {
    private String name;
    private Double[][] nodes; // stores coordinates X and Y. First arr - X, second - Y

    @SerializedName("elems")
    private Integer[][] elements;

    @SerializedName("RM")
    private double[][] recMatrix;
    private List<Double> colorsElements;

    public String getName() {
        return name;
    }

    public Integer[][] getElements() {
        return elements;
    }

    public Double[][] getNodes() {
        return nodes;
    }

    public double[][] getRecMatrix() {
        return recMatrix;
    }
}
