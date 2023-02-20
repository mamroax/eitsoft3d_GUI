package ru.app.services.builder;

import ru.app.services.builder.structures.Colormap;
import ru.app.services.builder.structures.Model;
import ru.app.services.builder.structures.Triangle;

import java.util.List;

public interface ModelBuilding {
    void buildModel(String colorMapPath, String modelPath, double typeIncreasing);
    void setTrianglesColorsIfExist(List<Double> colors);

    Model getModel();
    Colormap getColormap();
    List<Triangle> getTriangles();
}
