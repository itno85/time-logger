package com.timelogger.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LogDays {
    private static LogDays instance;
    private Map<LocalDate, LogDay> logDays;

    private LogDays() {
        logDays = new HashMap<>();
    }

    public static LogDays getInstance() {
        if (instance == null) {
            instance = new LogDays();
        }
        return instance;
    }

    public void dropInstance() {
        logDays = new HashMap<>();
    }

    public void addLogEntry(LogEntry entry) {
        LocalDate date = entry.getTimestamp().toLocalDate();
        LogDay logDay = logDays.getOrDefault(date, new LogDay());
        logDay.setDate(date);
        logDay.addEntry(entry);
        logDays.put(date, logDay);
    }

    public ArrayList<LogDay> getAllLogDays() {
        return new ArrayList<>(logDays.values());
    }

    public LogDay findByDate(LocalDate date) {
        LogDay logDay = logDays.get(date);
        if (logDay == null) {
            logDay = new LogDay();
            logDay.setDate(date);
        }
        return logDay;
    }

}

