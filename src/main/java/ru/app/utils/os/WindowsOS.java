package ru.app.utils.os;

import com.github.sarxos.winreg.HKey;
import com.github.sarxos.winreg.RegistryException;
import com.github.sarxos.winreg.WindowsRegistry;
import ru.app.properties.PropertiesManager;
import ru.app.resources.ResourceManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WindowsOS implements OperationSystem {

    @Override
    public String getComPort() {
        String friendlyName = getFriendlyName(getWindowsKeyPath() + PropertiesManager.getString("device_number"));

        if (friendlyName != null && friendlyName.contains("COM")) {
            String subStrCom = friendlyName.substring(friendlyName.indexOf("COM"));
            Matcher matcherComPort = Pattern.compile("\\d+").matcher(subStrCom);

            return "COM" + getComPort(matcherComPort);
//            return  "COM3";
        }
        return null;
    }

    private String getWindowsKeyPath() {
        return ResourceManager.getString("key_path").replace("key_vendor", PropertiesManager.getString("id_vendor"))
                .replace("key_product", PropertiesManager.getString("id_product"));
    }

    private String getFriendlyName(String key) {
        try {
            return WindowsRegistry.getInstance().readString(HKey.HKLM, key, "FriendlyName");
        } catch (RegistryException e) {
            e.printStackTrace();
        }

        return "";
    }

    private String getComPort(Matcher matcherComPort) {
        if(matcherComPort.find()) {
            return matcherComPort.group();
        }

        return "";
    }

}
