package ru.app.properties;

public class PropertiesManagerModel extends PropertiesManager {

    private PropertiesModel propertiesModel;

    public PropertiesModel getPropertiesModel() {
        propertiesModel = new PropertiesModel();
        getResources();

        return propertiesModel;
    }

    private  void getResources() {
        propertiesModel.setColormapPath(getString("colormap_model_path"));
        propertiesModel.setModelPath(getString("model_path"));
        propertiesModel.setValueIncrease(getString("model_increasing_value"));
    }
}
