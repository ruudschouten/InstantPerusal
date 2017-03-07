package com.blappole.instantperusal;

import android.app.Dialog;
import android.content.Intent;
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
        books.add(book);
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

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(author) || TextUtils.isEmpty(year) || TextUtils.isEmpty(pages)) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Book b = new Book(name, author, year, Integer.valueOf(pages));
                    books.add(b);
                    bookDialog.dismiss();
                    bookArrayAdapter.notifyDataSetChanged();
                }
            }
        });
        bookDialog.show();
    }
}
