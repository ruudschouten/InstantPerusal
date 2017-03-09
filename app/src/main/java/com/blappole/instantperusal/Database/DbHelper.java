package com.blappole.instantperusal.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.blappole.instantperusal.Database.DbContract.*;

public class DbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "InstantPerusal.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOK_ENTRIES);
        db.execSQL(SQL_CREATE_CHAPTER_ENTRIES);
        db.execSQL(SQL_CREATE_BOOKCHAPTERS_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_BOOK_ENTRIES);
        db.execSQL(SQL_DELETE_CHAPTER_ENTRIES);
        db.execSQL(SQL_DELETE_BOOKCHAPTERS_ENTRIES);
        onCreate(db);
    }
}
