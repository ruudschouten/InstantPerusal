package com.blappole.instantperusal;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.blappole.instantperusal.Database.DbAccess;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailsFragment extends Fragment {

    @BindView(R.id.frBookName)
    EditText bookName;
    @BindView(R.id.frBookAuthor)
    EditText bookAuthor;
    @BindView(R.id.frBookYear)
    EditText bookYear;
    @BindView(R.id.frBookPages)
    EditText bookPages;
    @BindView(R.id.frBookTimeSpent)
    EditText bookTimeSpent;
    @BindView(R.id.frBookAverageTime)
    EditText bookAverageTime;
    @BindView(R.id.fabEdit)
    FloatingActionButton fabEdit;
    @BindView(R.id.imgBookCover)
    ImageButton btnBookCover;

    private boolean editing = false;
    DbAccess db;
    Book book;

    public BookDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);
        ButterKnife.bind(this, v);
        db = new DbAccess(getContext());
        if (savedInstanceState == null) {
            savedInstanceState = getArguments();
        }

        Book book = savedInstanceState.getParcelable("book");
        assert book != null;
        if (book.Name != null) {
            bookName.setText(book.Name);
            bookAuthor.setText(book.Author);
            bookYear.setText(book.Year);
            bookPages.setText(String.valueOf(book.Pages));
            bookTimeSpent.setText(book.getTimeRead());
            bookAverageTime.setText(book.getAverageTimePerChapter());
            if (book.CoverImgId != 0) {
                btnBookCover.setImageDrawable(getResources().getDrawable(book.CoverImgId));
            }
        } else {
            enableEditing();
            editing = true;
        }
        this.book = book;

        btnBookCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Show dialog where user can upload poster
            }
        });

        fabEdit.setVisibility(View.VISIBLE);
        fabEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editing) {
                    enableEditing();
                } else {
                    disableEditing();
                    saveBookChanges();
                }
                editing = !editing;
            }
        });

        return v;
    }

    public void enableEditing() {
        bookName.setEnabled(true);
        bookAuthor.setEnabled(true);
        bookYear.setEnabled(true);
        bookPages.setEnabled(true);
        fabEdit.setImageResource(R.drawable.ic_check_white_24dp);
    }

    public void disableEditing() {
        bookName.setEnabled(false);
        bookAuthor.setEnabled(false);
        bookYear.setEnabled(false);
        bookPages.setEnabled(false);
        fabEdit.setImageResource(R.drawable.ic_edit_white_24dp);
    }

    public void saveBookChanges() {
        book.Name = bookName.getText().toString();
        book.Author = bookAuthor.getText().toString();
        book.Year = bookYear.getText().toString();
        String pages = bookPages.getText().toString();
        if (!Objects.equals(pages, "")) {
            book.Pages = Integer.valueOf(pages);
        } else {
            book.Pages = 0;
        }
        db.updateBook(book);
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);
        return fragment;
    }
}
