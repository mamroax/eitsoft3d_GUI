package ru.app.form.monitoring3D.shapes;

import javafx.geometry.Point3D;
import javafx.scene.shape.TriangleMesh;

public class TrianglePrism { // треугольная призма для чего?
    public final static int STANDART_HEIGHT = 30; // final означает, что наследовать от этого класса ничего нельзя
    public static TriangleMesh createTriangularPrism(Point3D firstVertex, Point3D secondVertex, Point3D thirdVertex, float heightPrism) {
        TriangleMesh meshTriangularPrism = new TriangleMesh();
        meshTriangularPrism.getTexCoords().addAll(0, 0);
        meshTriangularPrism.getPoints().addAll(
                (float) firstVertex.getX(), (float) firstVertex.getY(), (float) firstVertex.getZ(),
                (float) secondVertex.getX(), (float) secondVertex.getY(), (float) secondVertex.getZ(),
                (float) thirdVertex.getX(), (float) thirdVertex.getY(), (float) thirdVertex.getZ(),
                (float) firstVertex.getX(), (float) firstVertex.getY(), (float) firstVertex.getZ() - heightPrism,
                (float) secondVertex.getX(), (float) secondVertex.getY(), (float) secondVertex.getZ() - heightPrism,
                (float) thirdVertex.getX(), (float) thirdVertex.getY(), (float) thirdVertex.getZ() - heightPrism
        );
        meshTriangularPrism.getFaces().addAll(

                1, 0, 0, 0, 2, 0,
                1, 0, 2, 0, 4, 0,
                1, 0, 4, 0, 0, 0,
                4, 0, 5, 0, 3, 0,
                0, 0, 4, 0, 3, 0,
                0, 0, 3, 0, 5, 0,
                2, 0, 0, 0, 5, 0,
                5, 0, 4, 0, 2, 0
        );
        return meshTriangularPrism;
    }
}
