package com.blappole.instantperusal.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.blappole.instantperusal.Book;
import com.blappole.instantperusal.Chapter;

import org.joda.time.Period;

import java.util.ArrayList;

public class DbAccess {
    private DbHelper dbHelper;

    public DbAccess(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void addBook(Book b) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.BookEntry.COLUMN_NAME_NAME, b.Name);
        values.put(DbContract.BookEntry.COLUMN_NAME_AUTHOR, b.Author);
        values.put(DbContract.BookEntry.COLUMN_NAME_YEAR, b.Year);
        values.put(DbContract.BookEntry.COLUMN_NAME_PAGES, b.Pages);
        values.put(DbContract.BookEntry.COLUMN_NAME_COVER, b.CoverImgId);

        b.Id = db.insert(DbContract.BookEntry.TABLE_NAME, null, values);
    }

    public void addChapter(Book b, Chapter c) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.ChapterEntry.COLUMN_NAME_NAME, c.Name);
        values.put(DbContract.ChapterEntry.COLUMN_NAME_PAGES, c.Pages);
        values.put(DbContract.ChapterEntry.COLUMN_NAME_BOOK, b.Id);

        c.Id = db.insert(DbContract.ChapterEntry.TABLE_NAME, null, values);

        values = new ContentValues();

        db.insert(DbContract.ChapterEntry.TABLE_NAME, null, values);
    }

    public void updateBook(Book b) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.BookEntry.COLUMN_NAME_NAME, b.Name);
        values.put(DbContract.BookEntry.COLUMN_NAME_AUTHOR, b.Author);
        values.put(DbContract.BookEntry.COLUMN_NAME_YEAR, b.Year);
        values.put(DbContract.BookEntry.COLUMN_NAME_PAGES, b.Pages);
        values.put(DbContract.BookEntry.COLUMN_NAME_COVER, b.CoverImgId);

        db.update(DbContract.BookEntry.TABLE_NAME, values,  DbContract.BookEntry._ID + "= ?", new String[]{String.valueOf(b.Id)});
    }

    public ArrayList<Book> getBooksWithChapters() {
        ArrayList<Book> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DbContract.BookEntry.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(DbContract.BookEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(DbContract.BookEntry.COLUMN_NAME_NAME));
            String author = cursor.getString(cursor.getColumnIndex(DbContract.BookEntry.COLUMN_NAME_AUTHOR));
            String year = cursor.getString(cursor.getColumnIndex(DbContract.BookEntry.COLUMN_NAME_YEAR));
            int pages = cursor.getInt(cursor.getColumnIndex(DbContract.BookEntry.COLUMN_NAME_PAGES));
            int cover = cursor.getInt(cursor.getColumnIndex(DbContract.BookEntry.COLUMN_NAME_COVER));
            Book b = new Book(name, author, year, pages);
            b.Id = id;
            b.CoverImgId = cover;
            books.add(b);
        }
        cursor.close();
        for (Book b : books) {
            String query = String.format("SELECT * FROM %s WHERE %s=?",
                    DbContract.ChapterEntry.TABLE_NAME, DbContract.ChapterEntry.COLUMN_NAME_BOOK);
            String[] data = {
                    String.valueOf(b.Id)
            };
            cursor = db.rawQuery(query, data);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.ChapterEntry._ID));
                String name = cursor.getString(cursor.getColumnIndex(DbContract.ChapterEntry.COLUMN_NAME_NAME));
                int pages = cursor.getInt(cursor.getColumnIndex(DbContract.ChapterEntry.COLUMN_NAME_PAGES));
                long spent = cursor.getLong(cursor.getColumnIndex(DbContract.ChapterEntry.COLUMN_NAME_SPENT));
                Chapter c = new Chapter(name, pages);
                c.Id = id;
                if (spent != 0) {
                    c.TimeSpent = new Period(spent);
                }
                b.addChapter(c);
            }
            cursor.close();
        }
        return books;
    }
}
