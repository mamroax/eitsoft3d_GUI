package ru.app.properties;

import java.util.Objects;

public class PropertiesModel {
    private String modelPath;
    private String colormapPath;
    private String valueIncrease;

    public String getModelPath() {
        return modelPath;
    }

    public void setModelPath(String modelPath) {
        this.modelPath = modelPath;
    }

    public String getColormapPath() {
        return colormapPath;
    }

    public void setColormapPath(String colormapPath) {
        this.colormapPath = colormapPath;
    }

    public String getValueIncrease() {
        return valueIncrease;
    }

    public void setValueIncrease(String valueIncrease) {
        this.valueIncrease = valueIncrease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropertiesModel that = (PropertiesModel) o;

        return Objects.equals(modelPath, that.modelPath) &&
                Objects.equals(colormapPath, that.colormapPath) &&
                Objects.equals(valueIncrease, that.valueIncrease);
    }

}
