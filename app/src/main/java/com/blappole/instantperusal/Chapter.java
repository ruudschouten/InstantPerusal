package com.blappole.instantperusal;

import org.joda.time.*;

class Chapter {
    String Name;
    int Pages;
    Period TimeSpent;

    private LocalDateTime startTime;

    Chapter(String name, int pages) {
        Name = name;
        Pages = pages;
    }

    void StartReading() {
        startTime = LocalDateTime.now();
    }

    void StopReading() {
        LocalDateTime endTime = LocalDateTime.now();
        TimeSpent = getTimeBetween(startTime, endTime);
    }

    private Period getTimeBetween(LocalDateTime start, LocalDateTime end) {
        return Period.fieldDifference(start, end);
    }
}
