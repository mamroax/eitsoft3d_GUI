package ru.app.form.monitoring3D.controllers;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import ru.app.form.monitoring3D.shapes.TrianglePrism;
import ru.app.services.builder.ModelBuilding;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageController3D {
    private ModelBuilding modelBuilding;

    private List<MeshView> meshViewList = new ArrayList<>();

    public ImageController3D(Controller3D window, int indexImage) {
        this.modelBuilding = window.getBuiltStructure();

        buildModel((Group) window.getGroups().getChildren().get(indexImage), TrianglePrism.STANDART_HEIGHT);
    }

    private void buildModel(Group group, int height) {
        new Thread(() -> {

            modelBuilding.getTriangles().forEach(triangle -> {
                List<Double> coordinates = triangle.getCoordinate();
                Point3D firstVertex = new Point3D(coordinates.get(0).floatValue(), coordinates.get(1).floatValue(), 0);
                Point3D secondVertex = new Point3D(coordinates.get(2).floatValue(), coordinates.get(3).floatValue(), 0);
                Point3D thirdVertex = new Point3D(coordinates.get(4).floatValue(), coordinates.get(5).floatValue(), 0);

                MeshView meshView = new MeshView(TrianglePrism.createTriangularPrism(firstVertex, secondVertex, thirdVertex, height));
                meshView.setDrawMode(DrawMode.LINE);
                Platform.runLater(() -> {
                    group.getChildren().add(meshView);
                });

                meshViewList.add(meshView);
                waitSecond();
            });

        }).start();

    }

    private void waitSecond() {
        try {
            Thread.sleep(0,1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void drawImage(final List<Paint> dataMeas) {
        AtomicInteger index = new AtomicInteger(-1);
        double red, green, blue;
        for (Paint data: dataMeas
                ) {
            red = ((Color)data).getRed();
            green = ((Color)data).getGreen();
            blue = ((Color)data).getBlue();
//            if(blue > red) {
//                meshViewList.get(index.get() + 1).setMaterial(new PhongMaterial(Color.color(red,green,blue,0.0001)));
//                meshViewList.get(index.incrementAndGet()).setDrawMode(DrawMode.FILL);
//            }
//            else {
//                meshViewList.get(index.get() + 1).setDrawMode(DrawMode.FILL);
//                meshViewList.get(index.get() + 1).setOpacity(0.3);
                meshViewList.get(index.incrementAndGet()).setMaterial(new PhongMaterial(Color.color(red, green, blue, 0.25)));
            //}
        }
    }
}
