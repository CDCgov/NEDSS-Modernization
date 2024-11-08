package com.example.save_dedupe_configuration.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

import com.example.save_dedupe_configuration.model.Method;
import com.example.save_dedupe_configuration.model.MethodType;

public class MethodDeserializer extends JsonDeserializer<Method> {

    @Override
    public Method deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(jsonParser);

        // Check for the "method" field
        String methodTypeString;
        MethodType methodType;

        if (node.has("method")) {
            if (node.get("method").isTextual()) {
                methodTypeString = node.get("method").asText();
            } else if (node.get("method").isObject()) {
                // Adjust based on your JSON structure if needed
                methodTypeString = node.get("method").get("value").asText();
            } else {
                throw new IOException("Unsupported method type: " + node.get("method").toString());
            }
        } else {
            throw new IOException("Missing required field 'method' in JSON: " + node.toString());
        }

        // Map the method type string to MethodType enum
        try {
            methodType = MethodType.valueOf(methodTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IOException("Unknown method type: " + methodTypeString + " in JSON: " + node.toString());
        }

        // Get the "name" and "value" fields
        String name = node.has("name") ? node.get("name").asText() : null;
        String value = node.has("value") ? node.get("value").asText() : null;

        // Create and return a new Method instance
        return new Method(methodType, name, value);
    }
}
