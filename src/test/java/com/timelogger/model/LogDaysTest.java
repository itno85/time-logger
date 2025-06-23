package com.timelogger.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class LogDaysTest {
    private LogDays logDays;

    @BeforeEach
    void setUp() {

        logDays = LogDays.getInstance();
        logDays.dropInstance();
    }

    @Test
    void addLogEntry_assignsEntryToCorrectLogDay() {
        LocalDateTime timestamp = LocalDateTime.of(2023, 10, 1, 10, 0);
        LogEntry entry = new LogEntry(timestamp, "Test entry");

        logDays.addLogEntry(entry);

        LogDay logDay = logDays.findByDate(timestamp.toLocalDate());
        assertNotNull(logDay);
        assertTrue(logDay.getEntries().contains(entry));
    }

    @Test
    void findByDate_returnsCorrectLogDay() {
        LocalDate date = LocalDate.of(2023, 10, 1);
        LogEntry entry = new LogEntry(date.atStartOfDay(), "Test entry");

        logDays.addLogEntry(entry);

        LogDay logDay = logDays.findByDate(date);
        assertNotNull(logDay);
        assertEquals(date, logDay.getDate());
    }

    @Test
    void findByDate_returnsLogDayWithNoEntriesForNonExistentDate() {
        LocalDate date = LocalDate.of(2023, 10, 1);

        LogDay logDay = logDays.findByDate(date);
        assertNotNull(logDay);
        assertTrue(logDay.getEntries().isEmpty());
    }

    @Test
    void getAllLogDays_returnsAllLogDays() {
        LocalDate date1 = LocalDate.of(2023, 10, 1);
        LocalDate date2 = LocalDate.of(2023, 10, 2);
        LogEntry entry1 = new LogEntry(date1.atStartOfDay(), "Test entry 1");
        LogEntry entry2 = new LogEntry(date2.atStartOfDay(), "Test entry 2");

        logDays.addLogEntry(entry1);
        logDays.addLogEntry(entry2);

        assertEquals(2, logDays.getAllLogDays().size());
    }

    @Test
    void addLogEntry_createsNewLogDayIfNotExists() {
        LocalDateTime timestamp = LocalDateTime.of(2023, 10, 3, 10, 0);
        LogEntry entry = new LogEntry(timestamp, "New entry");

        logDays.addLogEntry(entry);

        LogDay logDay = logDays.findByDate(timestamp.toLocalDate());
        assertNotNull(logDay);
        assertEquals(timestamp.toLocalDate(), logDay.getDate());
    }

    @Test
    void addLogEntry_addsEntryToExistingLogDay() {
        LocalDateTime timestamp = LocalDateTime.of(2023, 10, 1, 10, 0);
        LogEntry entry1 = new LogEntry(timestamp, "First entry");
        LogEntry entry2 = new LogEntry(timestamp.plusHours(1), "Second entry");

        logDays.addLogEntry(entry1);
        logDays.addLogEntry(entry2);

        LogDay logDay = logDays.findByDate(timestamp.toLocalDate());
        assertNotNull(logDay);
        assertTrue(logDay.getEntries().contains(entry1));
        assertTrue(logDay.getEntries().contains(entry2));
    }
}