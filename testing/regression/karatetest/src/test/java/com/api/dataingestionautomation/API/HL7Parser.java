package com.api.dataingestionautomation.API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HL7Parser {
    private List<String> formattedMessages = new ArrayList<>();

    public HL7Parser(String filePath) {
        parseHL7Messages(filePath);
    }

    private void parseHL7Messages(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<Map<String, String>> messages = mapper.readValue(new File(filePath), new TypeReference<List<Map<String, String>>>() {});
            for (Map<String, String> message : messages) {
                String hl7Data = message.get("data");
                formattedMessages.addAll(formatHL7(hl7Data));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String> formatHL7(String hl7String) {
        List<String> formattedFields = new ArrayList<>();
        String[] segments = hl7String.split("\r\n|\r|\n");
        for (String segment : segments) {
            String[] fields = segment.split("\\|");
            for (int i = 1; i < fields.length; i++) {
                String[] components = fields[i].split("\\^", -1);
                if (components.length > 1) {
                    for (int j = 0; j < components.length; j++) {
                        formattedFields.add(fields[0] + "." + i + "." + (j + 1) + " - " + components[j]);
                    }
                } else {
                    formattedFields.add(fields[0] + "." + i + " - " + fields[i]);
                }
            }
        }
        return formattedFields;
    }

    public List<String> getFormattedMessages() {
        return formattedMessages;
    }

    public static void main(String[] args) {
        HL7Parser parser = new HL7Parser("src/test/java/com/api/dataingestionautomation/API/try.json");
        List<String> formattedMessages = parser.getFormattedMessages();
        formattedMessages.forEach(System.out::println);
    }
}
