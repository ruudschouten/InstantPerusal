package com.blappole.instantperusal;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.blappole.instantperusal.Database.DbHelper;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.Period;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.blappole.instantperusal.Database.DbContract.*;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.lvBooks)
    ListView lvBooks;
    @BindView(R.id.tvBooksEmpty)
    TextView tvEmpty;

    BookArrayAdapter bookArrayAdapter;
    ArrayList<Book> books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        JodaTimeAndroid.init(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBookDialog();
            }
        });
        books = new ArrayList<>();
        Book book = new Book("A Game of Thrones", "George R. R. Martin", "1997", 694);
        book.CoverImgId = R.drawable.agot;
//        addBookDb(book);
        books = getBooksWithChaptersDb();
//        books.add(book);
        bookArrayAdapter = new BookArrayAdapter(this, R.layout.book_layout, books);
        lvBooks.setAdapter(bookArrayAdapter);
        lvBooks.setEmptyView(tvEmpty);
        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book book = (Book) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(MainActivity.this, BookActivity.class);
                intent.putExtra("book", book);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addBookDialog() {
        final Dialog bookDialog = new Dialog(this);
        bookDialog.setContentView(R.layout.add_book_dialog);
        final Button dialogButton = (Button) bookDialog.findViewById(R.id.btnAddBook);
        final EditText bookName = (EditText) bookDialog.findViewById(R.id.editBookName);
        final EditText bookAuthor = (EditText) bookDialog.findViewById(R.id.editBookAuthor);
        final EditText bookYear = (EditText) bookDialog.findViewById(R.id.editBookYear);
        final EditText bookPages = (EditText) bookDialog.findViewById(R.id.editBookPages);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bookName.getText().toString();
                String author = bookAuthor.getText().toString();
                String year = bookYear.getText().toString();
                String pages = bookPages.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(author) || TextUtils.isEmpty(year) || TextUtils.isEmpty(pages)) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Book b = new Book(name, author, year, Integer.valueOf(pages));
                    books.add(b);
                    addBookDb(b);
                    bookDialog.dismiss();
                    bookArrayAdapter.notifyDataSetChanged();
                }
            }
        });
        bookDialog.show();
    }

    public void addBookDb(Book b) {
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BookEntry.COLUMN_NAME_NAME, b.Name);
        values.put(BookEntry.COLUMN_NAME_AUTHOR, b.Author);
        values.put(BookEntry.COLUMN_NAME_YEAR, b.Year);
        values.put(BookEntry.COLUMN_NAME_PAGES, b.Pages);
        values.put(BookEntry.COLUMN_NAME_COVER, b.CoverImgId);

        db.insert(BookEntry.TABLE_NAME, null, values);
    }

    public ArrayList<Book> getBooksWithChaptersDb() {
        ArrayList<Book> books = new ArrayList<>();
        DbHelper dbHelper = new DbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(BookEntry.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(BookEntry._ID));
            String name = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_NAME_NAME));
            String author = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_NAME_AUTHOR));
            String year = cursor.getString(cursor.getColumnIndex(BookEntry.COLUMN_NAME_YEAR));
            int pages = cursor.getInt(cursor.getColumnIndex(BookEntry.COLUMN_NAME_PAGES));
            int cover = cursor.getInt(cursor.getColumnIndex(BookEntry.COLUMN_NAME_COVER));
            Book b = new Book(name, author, year, pages);
            b.Id = id;
            b.CoverImgId = cover;
            books.add(b);
        }
        cursor.close();
        for (Book b : books) {
            if (!b.Chapters.isEmpty()) {
                String query = "SELECT ? FROM ? INNER JOIN ? ON ? = ? INNER JOIN ? ON ? = ? WHERE ? = ?";
                String[] data = {
                        String.format("%s, %s, %s, %s", ChapterEntry._ID, ChapterEntry.COLUMN_NAME_NAME, ChapterEntry.COLUMN_NAME_PAGES, ChapterEntry.COLUMN_NAME_SPENT),
                        BookChaptersEntry.TABLE_NAME,
                        BookEntry.TABLE_NAME,
                        BookChaptersEntry.COLUMN_NAME_BOOK,
                        BookEntry._ID,
                        ChapterEntry.TABLE_NAME,
                        BookChaptersEntry.COLUMN_NAME_CHAPTER,
                        ChapterEntry._ID,
                        BookEntry.COLUMN_NAME_NAME,
                        b.Name
                };
                cursor = db.rawQuery(query, data);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ChapterEntry._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ChapterEntry.COLUMN_NAME_NAME));
                    int pages = cursor.getInt(cursor.getColumnIndex(ChapterEntry.COLUMN_NAME_PAGES));
                    long spent = cursor.getLong(cursor.getColumnIndex(ChapterEntry.COLUMN_NAME_SPENT));
                    Chapter c = new Chapter(name, pages);
                    c.Id = id;
                    if (spent != 0) {
                        c.TimeSpent = new Period(spent);
                    }
                    b.addChapter(c);
                }
                cursor.close();
            }
        }
        return books;
    }
}
