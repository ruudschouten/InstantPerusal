package com.blappole.instantperusal;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BookDetailsFragment extends Fragment {

    public BookDetailsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_details, container, false);

        TextView bookName = (TextView) v.findViewById(R.id.frBookName);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }

        Book book = savedInstanceState.getParcelable("book");
        assert book != null;
        bookName.setText(Utils.GetStyledString(String.format("Full title: %s", book.Name), "Full title:"));

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
