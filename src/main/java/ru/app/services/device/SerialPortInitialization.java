package ru.app.services.device;

import javafx.application.Platform;
import javafx.stage.Stage;
import jssc.*;
import org.apache.log4j.Logger;

public class SerialPortInitialization implements SerialPortEventListener {
    private static final int MAX_NUMBER_RECOVERY_ATTEMPTS = 50;

    private final SerialPortConnection serialPortConnection;
    private int numberRecoveryAttempts = 0;

    private static Logger logger = Logger.getLogger(SerialPortConnection.class);

    SerialPortInitialization(SerialPortConnection serialPortConnection) {
        this.serialPortConnection = serialPortConnection;
    }

    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            String deviceMsg = "";
            try {
                deviceMsg = serialPortConnection.getSerialPort().readString(event.getEventValue());
                checkInitialization(deviceMsg);
                changeEventListener();

                serialPortConnection.setInitializeCode(0); // initialize success
                serialPortConnection.getSerialPort().writeString(serialPortConnection.getDevice()
                        .getInitializationParams());

            } catch (SerialPortException | InitializationException e) {
                identifyErrorType(deviceMsg);

            } finally {
                synchronized (serialPortConnection) {
                    serialPortConnection.notify();
                }
            }
        }
    }

    private void checkInitialization(String deviceMsg) throws InitializationException {
        if (!deviceMsg.trim().equals(Device.NUMBER_GOOD_INITIALIZATION)) {
            throw new InitializationException();
        }
    }


    private void identifyErrorType(String deviceMsg) {
        String[] arrayErrors = deviceMsg.trim().replaceAll("1,", "").split("\r\n");

        if (isElectrodes(arrayErrors)) {
            showErrorElectrodesWindow(arrayErrors);
            serialPortConnection.setInitializeCode(-1); // initialize error
        } else {
            recoveryAttempt();
        }
    }

    private boolean isElectrodes(String[] arrErrorsElectrodes) {
        for (String arrErrorsElectrode : arrErrorsElectrodes) {
            try {
                int value = Integer.parseInt(arrErrorsElectrode);
                if (!(value > 0 && value < 17)) {
                    return false;
                }
            } catch (NumberFormatException ex) {
                return false;
            }
        }

        return true;
    }

    private void showErrorElectrodesWindow(String[] arrErrorsElectrodes) {

    }

    private void recoveryAttempt() {
        if (numberRecoveryAttempts < MAX_NUMBER_RECOVERY_ATTEMPTS) {
            logger.warn("recovery attempt - " + numberRecoveryAttempts);

            serialPortConnection.stop();
            serialPortConnection.start(serialPortConnection.getDevice());
            numberRecoveryAttempts++;
        }
    }

    private void changeEventListener() throws SerialPortException {
        serialPortConnection.getSerialPort().removeEventListener();
        serialPortConnection.getSerialPort().addEventListener(new SerialPortReader(serialPortConnection));
    }

}
