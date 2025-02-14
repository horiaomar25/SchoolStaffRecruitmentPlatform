package com.example.SchoolStaffRecrutimentPlatform.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {

    // Utility method for converting Java objects to JSON
    public static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert object to JSON", e);
        }
    }
}
