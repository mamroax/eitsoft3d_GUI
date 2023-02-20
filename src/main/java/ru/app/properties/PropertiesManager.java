package ru.app.properties;

import java.io.*;
import java.util.Properties;

public class PropertiesManager {

    private static final String PATH_TO_STRINGS_XML = "/properties/values/strings.xml";

    public static String getString(String key) {
       return getParameter(PATH_TO_STRINGS_XML, key);
    }

    private static String getParameter(String path, String key) {
        Properties properties = loadXml(new PropertiesManager()
                .getFileProperties(path));

        assert properties != null;
        return properties.getProperty(key);
    }

    private static void setParameter(String path, String key, String value) {
        Properties properties = loadXml(new PropertiesManager().getFileProperties(path));

        assert properties != null;
        properties.setProperty(key, value);

        File propertiesFile = new PropertiesManager().getFileProperties(path);
        try(OutputStream fos = new FileOutputStream(propertiesFile)) {
            properties.storeToXML(fos, "no comment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static Properties loadXml(File fileProperties) {
        FileInputStream inputStream = getFileStream(fileProperties);
        Properties properties = new Properties();

        assert inputStream != null;
        try {
            properties.loadFromXML(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeInputStream(inputStream);
        }

        return properties;
    }

    private static FileInputStream getFileStream(File fileProperties) {
        try {
            return new FileInputStream(fileProperties);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static void closeInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFileProperties(String filePath) {
        return new File(System.getProperty("user.dir") + filePath);
    }

}
