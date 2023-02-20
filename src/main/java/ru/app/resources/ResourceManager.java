package ru.app.resources;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceManager {
    private static final String PATH_TO_STRINGS = "values/strings.xml";

    public static String getString(String key) {
        return getParameter(PATH_TO_STRINGS, key);
    }

    private static String getParameter(String path, String key) {
        Properties properties = loadXml(new ResourceManager()
                .getInputStreamResource(path));

        assert properties != null;
        return properties.getProperty(key);
    }

    private static Properties loadXml(InputStream inputStream) {
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

    private static void closeInputStream(InputStream inputStream) {
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public InputStream getInputStreamResource(String filePath) {
        return getClass().getClassLoader().getResourceAsStream(filePath);
    }

    public File getFileResource(String filePath) {
        return new File(getClass().getClassLoader().getResource(filePath).getFile());
    }

}
