package ru.app.services.device;


import java.util.List;

public interface Connectible {
    int start(Device device);
    List<List<Double>> updateMeas();
    void stop();
    void closePort();
}
