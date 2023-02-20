package ru.app.services.device;

import javafx.application.Platform;
import jssc.*;

import java.util.ArrayList;
import java.util.List;

public class SerialPortConnection implements Connectible {
    private Device device;

    private static SerialPort serialPort;

    private List<Double> dataMeas;
    private SerialPortInitialization serialPortInitialization = new SerialPortInitialization(this);
    private int initializeCode = 0;


    @Override
    public int start(Device device) {
        setDevice(device);
        initializationDataMeasList();
        initializationSerialPortIfNull();
        removeEventListerIfExist();
        sentInitializationCommandAndSetEvenListener();

        waitReadingMeas();
        return getInitializeCode();
    }

    private void setDevice(Device device) {
        this.device = device;
    }

    private int getInitializeCode() {
        return initializeCode;
    }

    void setInitializeCode(int initializeCode) {
        this.initializeCode = initializeCode;
    }

    private void initializationDataMeasList() {
        dataMeas = new ArrayList<>();
    }

    private void initializationSerialPortIfNull() {
        if (serialPort == null) {
            serialPort = new SerialPort(device.getPort());

            try {
                serialPort.openPort();
                serialPort.setParams(Device.BAUD_RATE,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                        SerialPort.FLOWCONTROL_RTSCTS_OUT);
            } catch (SerialPortException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void removeEventListerIfExist() {
        try {
            serialPort.removeEventListener();
        } catch (SerialPortException e) {

        }
    }

    private void sentInitializationCommandAndSetEvenListener() {
        try {
            serialPort.addEventListener(serialPortInitialization, SerialPort.MASK_RXCHAR);
            serialPort.writeString(Device.COMMAND_START);

        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<List<Double>> updateMeas() {
        clearDataListIfHasData();

        try {
            serialPort.writeString(Device.COMMAND_UPDATE);
        } catch (SerialPortException e) {

        }

        waitReadingMeas();

        //System.out.println(dataMeas.size());
        List<List<Double>> listDataMeas= new ArrayList<>();
        for (int countMeas = 0; countMeas < Device.COUNT_LINES_ONE_MEAS * Integer.parseInt(device.getNumberOfBelts()); countMeas += Device.COUNT_LINES_ONE_MEAS) {
             listDataMeas.add(dataMeas.subList(countMeas, countMeas + Device.COUNT_LINES_ONE_MEAS));
        }

        return listDataMeas;
    }

    private void clearDataListIfHasData() {
        if (dataMeas.size() > 0) {
            dataMeas.clear();
        }
    }

    private void waitReadingMeas() {
        synchronized (this) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        try {
            serialPort.writeString(Device.COMMAND_STOP);
        } catch (SerialPortException e) {

        }
    }


    @Override
    public void closePort() {
        try {

            if (serialPort != null) {
                serialPort.closePort();
                serialPort = null;
            }
        } catch (SerialPortException e) {

        }
    }

    void addMeas(double meas) {
        dataMeas.add(meas);
    }

    int getDataMeasSize() {
        return dataMeas.size();
    }

    SerialPort getSerialPort() {
        return serialPort;
    }

    public Device getDevice() {
        return device;
    }
}
