package com.timelogger.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TimeLoggerConfig {
    public static String CONFIG_FILE_PATH;
    public static String DATABASE_PATH;

    public static void setConfigFilePath(String path) {
        CONFIG_FILE_PATH = path;
    }

    public static void readConfig() {
        try (InputStream input = new FileInputStream(CONFIG_FILE_PATH)) {
                Properties prop = new Properties();
                if (input == null) {
                    System.out.println("Sorry, unable to find " + CONFIG_FILE_PATH);
                    return;
                }
                prop.load(input);
                DATABASE_PATH = prop.getProperty("database_path");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
    }

}
