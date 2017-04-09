package com.blappole.instantperusal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blappole.instantperusal.Database.DbAccess;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    DbAccess db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        JodaTimeAndroid.init(this);
        setSupportActionBar(toolbar);
        db = new DbAccess(this);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newBook();
            }
        });
        books = db.getBooksWithChapters();
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

    public void newBook(){
        Book book = new Book();
        db.addBook(book);
        Intent intent = new Intent(MainActivity.this, BookActivity.class);
        intent.putExtra("book", book);
        startActivity(intent);
    }
}
