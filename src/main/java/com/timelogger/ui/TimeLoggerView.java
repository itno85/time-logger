package com.timelogger.ui;

import com.timelogger.config.TimeLoggerConfig;
import com.timelogger.model.LogDay;
import com.timelogger.model.LogDays;
import com.timelogger.model.LogEntry;
import com.timelogger.storage.FileBackend;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimeLoggerView extends JFrame {
    private JTextArea logArea;
    private JButton prevButton, nextButton, homeButton, saveButton;
    private LocalDate currentDay = LocalDate.now();
    private LogDays logDays = LogDays.getInstance();

    public TimeLoggerView() {
        setTitle("Time-Logger");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JPanel inputPanel = new JPanel();

        JTextField entryField = new JTextField(50);
        JButton addEntryButton = new JButton("Add Entry");

        prevButton = new JButton("←");
        nextButton = new JButton("→");
        homeButton = new JButton("Home");

        inputPanel.add(entryField, BorderLayout.WEST);
        inputPanel.add(addEntryButton, BorderLayout.EAST);

        buttonPanel.add(prevButton);
        buttonPanel.add(homeButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.SOUTH);

        try {

            FileBackend.readLogs(TimeLoggerConfig.DATABASE_PATH);
            showLogs();
        } catch (IOException e) {
            logArea.setText("Error loading logs.");
            System.out.println(e.getMessage());
        }

        prevButton.addActionListener(e -> {
            this.currentDay = this.currentDay.minusDays(1);
            showLogs();
        });

        nextButton.addActionListener(e -> {
            if (this.currentDay.isBefore(LocalDate.now())) {
                this.currentDay = this.currentDay.plusDays(1);
            }
            showLogs();
        });

        homeButton.addActionListener(e -> {
            this.currentDay = LocalDate.now();
            showLogs();
        });

        addEntryButton.addActionListener(e -> enterEntry(entryField));

        entryField.addActionListener(e ->  enterEntry(entryField));

        setVisible(true);
    }

    private void enterEntry(JTextField entryField){
        if (Objects.isNull(entryField.getText()) || entryField.getText().isEmpty()) {
            return;
        }
        logDays.addLogEntry(new LogEntry(LocalDateTime.now(), entryField.getText()));
        entryField.setText("");
        showLogs();
        save();
    }

    private void save(){
        try {
            FileBackend.writeLogs(TimeLoggerConfig.DATABASE_PATH);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void showLogs() {
        if (logDays.getAllLogDays().isEmpty()) {
            logArea.setText("No logs available.");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(logDays.findByDate(this.currentDay));
        logArea.setText(sb.toString());
    }
}
