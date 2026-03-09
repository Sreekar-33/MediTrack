package com.airtribe.meditrack.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {

    private static final Properties properties = new Properties();

    static {

        try (FileInputStream fis = new FileInputStream("config/application.properties")) {

            properties.load(fis);

        } catch (IOException e) {

            System.out.println("Failed to load configuration: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {

        return properties.getProperty(key);

    }
}