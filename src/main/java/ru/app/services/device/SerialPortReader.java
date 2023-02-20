package ru.app.services.device;

import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class SerialPortReader implements SerialPortEventListener {

    private final SerialPortConnection serialPortConnection;
    private byte[] bytes;
    int countBytes = 0;
    private static int SIZE_OF_FLOAT = 4;

    SerialPortReader(SerialPortConnection serialPortConnection) {
        this.serialPortConnection = serialPortConnection;
        bytes = new byte[4];
    }

    public void serialEvent(SerialPortEvent event) {
        System.out.println("111");
        if (event.isRXCHAR() && event.getEventValue() > 0)
        {
            try {
                parseFloatFromControllersOutput(serialPortConnection.getSerialPort().readBytes(event.getEventValue()));
                if (isFullData()) {
                    finishRead();
                }
            } catch (SerialPortException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void finishRead() {
        synchronized (serialPortConnection) {
            serialPortConnection.notify();
        }
    }

    private void parseFloatFromControllersOutput(byte[] data) {
        for (byte b:data
                ) {
            if(countBytes < SIZE_OF_FLOAT)
                bytes[countBytes++] = b;
            if(countBytes == SIZE_OF_FLOAT) {
                countBytes = 0;
                //System.out.println(ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat());
                serialPortConnection.addMeas((double)ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN).getFloat());
            }
        }
    }

    private boolean isFullData() {

        return serialPortConnection.getDataMeasSize() == Device.COUNT_LINES_ONE_MEAS * Integer.parseInt(serialPortConnection.getDevice().getNumberOfBelts());

    }

}
