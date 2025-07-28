package com.example.hotel.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Path;

public final class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private JsonUtils() {}

    public static <T> T read(Path path, TypeReference<T> typeReference) throws IOException {
        if (!path.toFile().exists()) {
            path.toFile().getParentFile().mkdirs();
            path.toFile().createNewFile();
            // initialize with empty list if needed
            if (typeReference.getType().getTypeName().contains("java.util.List")) {
                MAPPER.writeValue(path.toFile(), java.util.List.of());
            } else {
                MAPPER.writeValue(path.toFile(), null);
            }
        }
        return MAPPER.readValue(path.toFile(), typeReference);
    }

    public static void write(Path path, Object value) throws IOException {
        MAPPER.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), value);
    }
}
