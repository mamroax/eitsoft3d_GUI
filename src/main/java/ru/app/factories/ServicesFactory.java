package ru.app.factories;

import ru.app.services.builder.ModelBuilder;
import ru.app.services.builder.ModelBuilding;
import ru.app.services.device.Connectible;
import ru.app.services.device.SerialPortConnection;
import ru.app.services.reconstruction.Reconstructed;
import ru.app.services.reconstruction.Reconstruction;

public class ServicesFactory {
    private ServicesFactory() {}

    public static Connectible getConnection() {
        return new SerialPortConnection();
    }

    public static ModelBuilding getBuilder() {
        return new ModelBuilder();
    }

    public static Reconstructed getReconstruction() {
        return new Reconstruction();
    }

}
