package ru.app.utils.os;

public class OSDetector {
    public OperationSystem detectedOS() {
        String nameSystem = System.getProperty("os.name").toLowerCase();

        if (nameSystem.contains("windows")) {
            return new WindowsOS();
        } else if (nameSystem.contains("linux")) {
            return new LinuxOS();
        }

        return null;
    }
}
