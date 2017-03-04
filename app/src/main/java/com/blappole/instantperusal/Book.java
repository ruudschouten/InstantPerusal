package com.blappole.instantperusal;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

import java.util.ArrayList;

class Book {
    String Name;
    ArrayList<Chapter> Chapters;

    Book(String name) {
        Name = name;
        Chapters = new ArrayList<>();
    }

    void addChapter(Chapter c) {
        Chapters.add(c);
    }

    int getPages() {
        int pages = 0;
        for (Chapter c : Chapters) {
            pages += c.Pages;
        }
        return pages;
    }

    String getTimeRead() {
        long millis = 0;
        for (Chapter c : Chapters) {
            millis += c.TimeSpent.getMillis();
        }
        return PeriodFormat.getDefault().print(new Period(millis));
    }

    String getAverageTimePerChapter() {
        long millis = 0;
        for (Chapter c : Chapters) {
            millis += (c.TimeSpent.getMillis());
        }
        return PeriodFormat.getDefault().print(new Period(millis));
    }

    String getAverageTimePerPage() {
        long millis = 0;
        for (Chapter c : Chapters) {
            millis += (c.TimeSpent.getMillis() / c.Pages);
        }
        return PeriodFormat.getDefault().print(new Period(millis));
    }
}
