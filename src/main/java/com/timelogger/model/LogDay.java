package com.timelogger.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class LogDay {
    LocalDate date;
    ArrayList<LogEntry> entries;
    public LogDay() {
        this.entries = new ArrayList<>();
    }

    public List<LogEntry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<LogEntry> entries) {
        this.entries = entries;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void addEntry(LogEntry entry) {
        if (entries == null) {
            entries = new ArrayList<>();
        }
        entries.add(entry);
    }

    public Duration getWorkTime() {
        Duration workTime = Duration.ZERO;
        if (entries.isEmpty() || entries.size() == 1) {
            return workTime;
        }
        entries = entries.stream()
                .sorted(Comparator.comparing(LogEntry::getTimestamp))
                .collect(Collectors.toCollection(ArrayList::new));

        LogEntry previousEntry = entries.getFirst();
        for (LogEntry entry : entries) {
            if (!entry.getDescription().contains("**")) {
                workTime = workTime.plus(Duration.between(previousEntry.getTimestamp(), entry.getTimestamp()));
            }
            previousEntry = entry;
        }
        return workTime;
    }

    public String getWorktimeAsString() {
        String workTimeString = "";
        if (getWorkTime().equals(Duration.ZERO)) {
            workTimeString = "0:00";
        } else {
            long hours = getWorkTime().toHours();
            long minutes = getWorkTime().toMinutes() % 60;
            workTimeString = String.format("%02d:%02d", hours, minutes);
        }
        return workTimeString;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ " + date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + " ]" ).append("\n\n");
        for (LogEntry entry : entries) {
            sb.append(entry).append("\n");
        }
        sb.append("\n");
        sb.append("Worktime: " + getWorktimeAsString()).append("\n");
        return sb.toString();
    }
}
