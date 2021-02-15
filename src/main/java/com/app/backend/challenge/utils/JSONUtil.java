package com.app.backend.challenge.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.StringWriter;

/**
 * JSON facilities
 */
public class JSONUtil {

    /**
     * Serialize a object
     * @param data object to be serialized
     * @return object serialized as json
     */
    public static String dataToJson(Object data) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, data);
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException("IOException while mapping object (" + data + ") to JSON");
        }
    }


    /**
     * Deserialize a json
     *
     * @param json to be deserialized
     * @param clazz target class
     * @return a clazz instance
     */
    public static Object jsonToData(final String json, final Class clazz) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException("IOException while mapping JSON (" + json + ") to Object");
        }
    }

}
