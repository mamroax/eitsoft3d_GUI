package ru.app.utils;

import javafx.scene.paint.Paint;

public class ColorSupplier {

    public static Paint getColor(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        return javafx.scene.paint.Color.rgb(red, green, blue);
    }

}
