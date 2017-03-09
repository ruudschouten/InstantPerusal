package com.blappole.instantperusal;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.*;

public class Chapter implements Parcelable {
    public long Id;
    public String Name;
    public int Pages;
    public Period TimeSpent;

    private LocalDateTime startTime;

    //region Parcelable

    protected Chapter(Parcel in) {
        Id = in.readLong();
        Name = in.readString();
        Pages = in.readInt();
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Name);
        parcel.writeInt(Pages);
        parcel.writeLong(Id);
        if(TimeSpent != null) {
            parcel.writeLong(TimeSpent.getMillis());
        }
    }
    //endregion

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
