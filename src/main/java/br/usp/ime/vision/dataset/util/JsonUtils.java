package br.usp.ime.vision.dataset.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonUtils {

    public static Gson getJsonConverter() {
        final Gson jsonConverter = new GsonBuilder().setPrettyPrinting()
                .create();
        return jsonConverter;
    }

}
