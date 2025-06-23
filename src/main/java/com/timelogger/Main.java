package com.timelogger;
import javax.swing.*;

import com.timelogger.config.TimeLoggerConfig;
import com.timelogger.ui.TimeLoggerView;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        Path configPath;

        if (args.length > 0 && Files.exists(Paths.get(args[0]))) {
            configPath = Paths.get(args[0]);
            TimeLoggerConfig.CONFIG_FILE_PATH = configPath.toString();
            System.out.println("Verwende Konfiguration aus Argument: " + configPath);
        } else {
            Path jarDir = getJarDirectory();
            configPath = jarDir.resolve("classes/timelogger.cfg");
            TimeLoggerConfig.CONFIG_FILE_PATH = configPath.toString();
            System.out.println("Kein valides Argument angegeben. Verwende Standardpfad: " + configPath);
        }

        TimeLoggerConfig.readConfig();
        SwingUtilities.invokeLater(TimeLoggerView::new);
    }

    private static Path getJarDirectory() {
        try {
            return Paths.get(Main.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI())
                    .getParent();
        } catch (Exception e) {
            throw new RuntimeException("Jar-Verzeichnis konnte nicht ermittelt werden.", e);
        }
    }
}
