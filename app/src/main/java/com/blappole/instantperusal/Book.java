package com.blappole.instantperusal;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

import java.util.ArrayList;

public class Book implements Parcelable {
    public long Id;
    public String Name;
    public String Author;
    public String Year;
    public int Pages;
    public int CoverImgId;
    public ArrayList<Chapter> Chapters;

    private Chapter longestChapter;
    private Chapter shortestChapter;

    //region Parcelable
    protected Book(Parcel in) {
        Id = in.readLong();
        Name = in.readString();
        Author = in.readString();
        Year = in.readString();
        Pages = in.readInt();
        CoverImgId = in.readInt();
        Chapters = in.createTypedArrayList(Chapter.CREATOR);
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(Id);
        parcel.writeString(Name);
        parcel.writeString(Author);
        parcel.writeString(Year);
        parcel.writeInt(Pages);
        parcel.writeInt(CoverImgId);
        parcel.writeTypedList(Chapters);
    }
    //endregion

    public Book(String name, String author, String year, int pages) {
        Name = name;
        Author = author;
        Year = year;
        Pages = pages;
        Chapters = new ArrayList<>();
    }

    Book(String name, String author) {
        Name = name;
        Author = author;
        Chapters = new ArrayList<>();
    }

    Book() {
        Chapters = new ArrayList<>();
    }

    public void addChapter(Chapter c) {
        Chapters.add(c);
    }

    String getTimeRead() {
        long millis = 0;
        for (Chapter c : Chapters) {
            if (c.TimeSpent != null)
                millis += c.TimeSpent.getMillis();
        }
        return PeriodFormat.getDefault().print(new Period(millis));
    }

    String getAverageTimePerChapter() {
        long millis = 0;
        for (Chapter c : Chapters) {
            if (c.TimeSpent != null)
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
