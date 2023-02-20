package ru.app.services.builder;

import ru.app.services.builder.json.JsonParser;
import ru.app.services.builder.structures.Colormap;
import ru.app.services.builder.structures.Model;
import ru.app.services.builder.structures.Triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream; // в дальнейшем хочу попробовать распарраллелить все вычисления по имеющимся ядрам
import java.util.concurrent.atomic.AtomicInteger;

public class ModelBuilder implements ModelBuilding {
    public static final double CIRCLE_MODEL = 200;
    public static final double TORSO_MODEL = 0.2;

    private Model model;
    private Colormap colormap;
    private List<Triangle> triangles;

    /**
     * @param typeIncreasing - Model.TORSO_MODEL or Model.CIRCLE_MODEL
     */
    @Override
    public void buildModel(String colorMapPath, String modelPath, double typeIncreasing)  {
        setModel(new JsonParser(modelPath).parseJsonModel());
        setColormap(new JsonParser(colorMapPath).parseJsonColormap());
        setTriangles(shapingTriangles(typeIncreasing));
    }

    private List<Double> getTrianglesListWithCoordinates(double typeIncreasing, Model model, int curTriangle) {
        List<Double> triangleCoordinate = new ArrayList<>();
        for (int i = 0; i < model.getElements().length; i++) {
            for (int j = 0; j < model.getNodes().length; j++) {
                if (j == 0) // Зеркалим по x
                    triangleCoordinate.add(model.getNodes()[j][model.getElements()[i][curTriangle] - 1] * typeIncreasing * -1);
                else
                    triangleCoordinate.add(model.getNodes()[j][model.getElements()[i][curTriangle] - 1] * typeIncreasing);
            }
        }


        return triangleCoordinate;
    }

    private List<Triangle> shapingTriangles(double typeIncreasing) {
        List<Triangle> triangles = new ArrayList<>();


            int countTriangles = model.getElements()[0].length;
            for (int i = 0; i < countTriangles; i++) {
                triangles.add(new Triangle(getTrianglesListWithCoordinates(typeIncreasing, getModel(), i)));
            }


        return triangles;
    }

    @Override
    public void setTrianglesColorsIfExist(List<Double> colors) {
//        if (colors.size() > 0) {
//            AtomicInteger index = new AtomicInteger(-1);
//            getTriangles().forEach(triangle -> triangle.setColor(colors.get(index.incrementAndGet())));
//        }
    }

    private void setModel(Model model) {
        this.model = model;
    }

    private void setColormap(Colormap colormap) {
        this.colormap = colormap;
    }

    private void setTriangles(List<Triangle> triangles) {
        this.triangles = triangles;
    }

    @Override
    public Model getModel() {
        return model;
    }

    @Override
    public Colormap getColormap() {
        return colormap;
    }

    @Override
    public List<Triangle> getTriangles() {
        return triangles;
    }

}
