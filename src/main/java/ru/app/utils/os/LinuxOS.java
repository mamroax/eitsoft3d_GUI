package ru.app.utils.os;

import ru.app.properties.PropertiesManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LinuxOS implements OperationSystem {

    @Override
    public String getComPort() {
        String connectedDevicesByVendorAndProductIdInfo = executeCommand(createBashCommand("dmesg | grep " +
                "'idVendor=" + PropertiesManager.getString("id_vendor") + "," +
                " idProduct=" + PropertiesManager.getString("id_product") + "'"));


        String lastConnectedDeviceInfo = getLastConnectedDeviceInfo(connectedDevicesByVendorAndProductIdInfo);
        String numberCOMPortDevice = executeCommand(createBashCommand("ls /sys/bus/usb/devices/usb*/" +
                getNumberUSB(lastConnectedDeviceInfo) + "/*/tty"));

        return "/dev/" + numberCOMPortDevice.trim();
    }

    private  String getLastConnectedDeviceInfo(String line) {
        StringTokenizer tokens = new StringTokenizer(line, "\n");
        String driverMessage = null;
        while (tokens.hasMoreTokens()) {
            driverMessage = tokens.nextToken();
        }

        return driverMessage;
    }

    private  String getNumberUSB(String line) {
        Pattern pattern = Pattern.compile("usb (.*):");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find())
            return matcher.group(1);

        return "";
    }

    private  String[] createBashCommand(String command) {
        return new String[]{"/bin/sh", "-c", command};
    }

    private  String executeCommand(String[] command) {

        StringBuilder output = new StringBuilder();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();

    }
}
