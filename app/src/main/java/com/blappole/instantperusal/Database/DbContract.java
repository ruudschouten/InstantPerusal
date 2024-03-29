package com.blappole.instantperusal.Database;

import android.app.Activity;
import android.provider.BaseColumns;

public final class DbContract {
    private DbContract() {
    }

    //region Queries

    public static final String SQL_CREATE_BOOK_ENTRIES =
            "CREATE TABLE " + BookEntry.TABLE_NAME + " (" +
                    BookEntry._ID + " INTEGER PRIMARY KEY," +
                    BookEntry.COLUMN_NAME_NAME + " TEXT NULL," +
                    BookEntry.COLUMN_NAME_AUTHOR + " TEXT NULL," +
                    BookEntry.COLUMN_NAME_YEAR + " TEXT NULL," +
                    BookEntry.COLUMN_NAME_PAGES + " INTEGER NULL," +
                    BookEntry.COLUMN_NAME_COVER + " INTEGER NULL)";

    public static final String SQL_CREATE_CHAPTER_ENTRIES =
            "CREATE TABLE " + ChapterEntry.TABLE_NAME + " (" +
                    ChapterEntry._ID + " INTEGER PRIMARY KEY," +
                    ChapterEntry.COLUMN_NAME_BOOK + " INT NOT NULL," +
                    ChapterEntry.COLUMN_NAME_NAME + " TEXT NOT NULL," +
                    ChapterEntry.COLUMN_NAME_PAGES + " INTEGER NOT NULL," +
                    ChapterEntry.COLUMN_NAME_SPENT + " INTEGER NULL)";

    public static final String SQL_DELETE_BOOK_ENTRIES =
            "DROP TABLE IF EXISTS " + BookEntry.TABLE_NAME;

    public static final String SQL_DELETE_CHAPTER_ENTRIES =
            "DROP TABLE IF EXISTS " + ChapterEntry.TABLE_NAME;
    //endregion

    //region Tables
    public static class BookEntry implements BaseColumns {
        public static final String TABLE_NAME = "Books";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_AUTHOR = "Author";
        public static final String COLUMN_NAME_YEAR = "Year";
        public static final String COLUMN_NAME_PAGES = "Pages";
        public static final String COLUMN_NAME_COVER = "Cover";
    }

    public static class ChapterEntry implements BaseColumns {
        public static final String TABLE_NAME = "Chapters";
        public static final String COLUMN_NAME_BOOK = "BookId";
        public static final String COLUMN_NAME_NAME = "Name";
        public static final String COLUMN_NAME_PAGES = "Pages";
        public static final String COLUMN_NAME_SPENT = "TimeSpent";
    }

    //endregion
}
