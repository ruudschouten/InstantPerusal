package com.blappole.instantperusal;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;




public class BookChaptersFragment extends Fragment {
    @BindView(R.id.fabAdd) FloatingActionButton fabAdd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_chapters, container, false);
        ButterKnife.bind(this, v);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }

        Book book = savedInstanceState.getParcelable("book");
        assert book != null;
        return v;
    }

    public static BookChaptersFragment newInstance(Book book) {
        BookChaptersFragment fragment = new BookChaptersFragment();
        Bundle bundle =  new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);
        return fragment;
    }
}
