package com.timelogger.model;

import com.timelogger.config.ApplicationConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogEntry {
    private LocalDateTime timestamp;
    private String description;

    public LogEntry(LocalDateTime timestamp, String description) {
        this.timestamp = timestamp;
        this.description = description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return timestamp.format(ApplicationConstants.FORMATTER) + ": " + description;
    }
}

