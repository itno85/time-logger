package com.timelogger.storage;

import com.timelogger.config.ApplicationConstants;
import com.timelogger.model.LogDay;
import com.timelogger.model.LogDays;
import com.timelogger.model.LogEntry;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FileBackend {


    public static void readLogs(String filePath) throws IOException {

        for (String line : Files.readAllLines(Paths.get(filePath))) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(": ", 2);
            if (parts.length == 2) {
                LocalDateTime time = LocalDateTime.parse(parts[0], ApplicationConstants.FORMATTER);
                LogDays.getInstance().addLogEntry(new LogEntry(time, parts[1]));
            }
        }
    }

    public static void writeLogs(String filePath) throws IOException {
        List<String> lines = new ArrayList<>();
        List<LogDay> sortedLogDays = LogDays.getInstance().getAllLogDays().stream()
                .sorted(Comparator.comparing(LogDay::getDate))
                .collect(Collectors.toList());
        for (LogDay logDay: sortedLogDays ) {

            logDay.getEntries().forEach(logEntry -> lines.add(logEntry.getTimestamp().format(ApplicationConstants.FORMATTER) + ": " + logEntry.getDescription()));
            lines.add("");
        }

        Files.write(Paths.get(filePath), lines);
    }

}
