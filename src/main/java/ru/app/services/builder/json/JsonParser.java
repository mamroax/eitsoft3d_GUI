package ru.app.services.builder.json;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import ru.app.resources.ResourceManager;
import ru.app.services.builder.structures.Colormap;
import ru.app.services.builder.structures.Model;

import java.io.File;
import java.io.IOException;

public class JsonParser {
    private String filePathToObject;

    public JsonParser(String filePathToObject) {
        this.filePathToObject = filePathToObject;
    }

    public Model parseJsonModel() {
        return new Gson().fromJson(readJson(), Model.class);
    }

    public Colormap parseJsonColormap() {
        return new Gson().fromJson(readJson(), Colormap.class);
    }

    private String readJson()  {
        try {
            return IOUtils.toString(new ResourceManager().getInputStreamResource(filePathToObject), "utf-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
