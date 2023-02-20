package ru.app.services.device;

public class Device {
    static final String COMMAND_START = "1\r\n";
    static final String COMMAND_UPDATE = "5\r\n";
    static final String COMMAND_STOP = "4\r\n";
    static final String NUMBER_GOOD_INITIALIZATION = "7";

    static final int BAUD_RATE = 921600;
    static final int COUNT_LINES_ONE_MEAS = 208;

    private String frequencyCurrent;
    private String amplitudeCurrent;
    private String numberOfBelts;
    private String injectedCurrentForm;

    private String port;

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setFrequencyCurrent(String frequencyCurrent) {
        this.frequencyCurrent = frequencyCurrent;
    }

    public void setAmplitudeCurrent(String amplitudeCurrent) {
        this.amplitudeCurrent = amplitudeCurrent;
    }

    public void setNumberOfBelts(String numberOfBelts) {
        this.numberOfBelts = numberOfBelts;
    }

    public void setInjectedCurrentForm(String injectedCurrentForm) {
        this.injectedCurrentForm = injectedCurrentForm;
    }

    public String getNumberOfBelts() {
        return numberOfBelts;
    }

    String getInitializationParams() {
        return String.join(",", numberOfBelts, injectedCurrentForm, frequencyCurrent, amplitudeCurrent);
    }

}
