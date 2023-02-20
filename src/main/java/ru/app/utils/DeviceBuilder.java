package ru.app.utils;

import ru.app.properties.PropertiesManager;
import ru.app.services.device.Device;
import ru.app.utils.os.OSDetector;
import ru.app.utils.os.OperationSystem;

public class DeviceBuilder {
    public static Device buildDevice() {
        Device device = new Device();

        device.setInjectedCurrentForm("1"); //установка формы импульса
        device.setNumberOfBelts("1"); //установка количества поясов
        device.setFrequencyCurrent(PropertiesManager.getString("frequency_current")); //установка частоты тока
        device.setAmplitudeCurrent(PropertiesManager.getString("amplitude_current")); //установка силы тока
        device.setPort(detectComPort()); //установка порта

        return device;
    }
    /* нужно вызвать следующий метод так, чтобы в него передались данные из формы XML, по нажатию кнопочки*/
    public static Device buildDevice(String numberOfBelts){
        Device device = new Device();

        device.setInjectedCurrentForm("1");
        device.setNumberOfBelts(numberOfBelts);
        device.setFrequencyCurrent(PropertiesManager.getString("frequency_current"));
        device.setAmplitudeCurrent(PropertiesManager.getString("amplitude_current"));
        device.setPort(detectComPort());

        return device;
    }

    private static String detectComPort() {
        OSDetector detector = new OSDetector();
        OperationSystem operationSystem = detector.detectedOS();

        return operationSystem.getComPort();
    }
}
