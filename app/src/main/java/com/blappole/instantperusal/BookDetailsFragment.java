package com.blappole.instantperusal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailsFragment extends Fragment {
    @BindView(R.id.frBookName) TextView bookName;
    @BindView(R.id.frBookAuthor) TextView bookAuthor;
    @BindView(R.id.frBookYear) TextView bookYear;
    @BindView(R.id.frBookPages) TextView bookPages;
    @BindView(R.id.frBookTimeSpent) TextView bookTimeSpent;
    @BindView(R.id.frBookAverageTime) TextView bookAverageTime;

    public BookDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        ButterKnife.bind(this, v);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }

        Book book = savedInstanceState.getParcelable("book");
        assert book != null;
        bookName.setText(Utils.GetStyledString(String.format("Full title: %s", book.Name), "Full title:"));
        bookAuthor.setText(Utils.GetStyledString(String.format("Author: %s", book.Author), "Author:"));
        bookYear.setText(Utils.GetStyledString(String.format("Year: %s", book.Year), "Year:"));
        bookPages.setText(Utils.GetStyledString(String.format("Pages: %s", book.Pages), "Pages:"));
        bookTimeSpent.setText(Utils.GetStyledString(String.format("Time spent: %s", book.getTimeRead()), "Time spent:"));
        bookAverageTime.setText(Utils.GetStyledString(String.format("Average time per chapter: %s", book.getAverageTimePerChapter()), "Average time per chapter:"));

        return v;
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);
        return fragment;
    }
}
