package com.blappole.instantperusal;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.Period;
import org.joda.time.format.PeriodFormat;

import java.util.ArrayList;

class Book implements Parcelable {
    String Name;
    String Author;
    String Year;
    int Pages;

    private ArrayList<Chapter> Chapters;

    //region Parcelable
    protected Book(Parcel in) {
        Name = in.readString();
        Author = in.readString();
        Year = in.readString();
        Pages = in.readInt();
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
        parcel.writeString(Name);
        parcel.writeString(Author);
        parcel.writeString(Year);
        parcel.writeInt(Pages);
        parcel.writeTypedList(Chapters);
    }
    //endregion

    Book(String name) {
        Name = name;
        Author = "Unknown";
        Year = "Unknown";
        Chapters = new ArrayList<>();
    }

    void addChapter(Chapter c) {
        Chapters.add(c);
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
