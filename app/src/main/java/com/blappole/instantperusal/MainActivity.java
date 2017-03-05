package com.blappole.instantperusal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.lvBooks) ListView lvBooks;
    @BindView(R.id.tvBooksEmpty) TextView tvEmpty;

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
        bookArrayAdapter = new BookArrayAdapter(this, R.layout.book_layout, books);
        lvBooks.setAdapter(bookArrayAdapter);
        lvBooks.setEmptyView(tvEmpty);
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
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Book b = new Book(bookName.getText().toString());
                books.add(b);
                bookDialog.dismiss();
                bookArrayAdapter.notifyDataSetChanged();
            }
        });
        bookDialog.show();
    }
}
