package ru.app.services.builder.structures;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Colormap {
    @SerializedName("colormap")
    private String nameColormap;
    @SerializedName("value")
    private List<Double> valueColormap;

    public String getNameColormap() {
        return nameColormap;
    }

    public List<Double> getValuesColormap() {
        return valueColormap;
    }
}
