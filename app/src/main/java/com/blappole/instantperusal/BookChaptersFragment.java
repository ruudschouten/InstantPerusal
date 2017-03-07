package com.blappole.instantperusal;

import android.app.Dialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookChaptersFragment extends Fragment {
    @BindView(R.id.fabAdd) FloatingActionButton fabAdd;
    @BindView(R.id.frChaptersEmpty) TextView tvEmpty;
    @BindView(R.id.frChapterContainer) ListView lvChapters;
    Book book;
    ChapterArrayAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_book_chapters, container, false);
        ButterKnife.bind(this, v);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }

        book = savedInstanceState.getParcelable("book");
        assert book != null;

        adapter = new ChapterArrayAdapter(v.getContext(), R.layout.chapter_layout, book.Chapters);
        lvChapters.setAdapter(adapter);
        lvChapters.setEmptyView(tvEmpty);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddChapterDialog(view);
            }
        });
        return v;
    }

    public void showAddChapterDialog(View v) {
        final Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.add_chapter_dialog);
        final Button dialogButton = (Button) dialog.findViewById(R.id.btnAddChapter);
        final EditText bookName = (EditText) dialog.findViewById(R.id.editChapterName);
        final EditText bookAuthor = (EditText) dialog.findViewById(R.id.editChapterPages);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = bookName.getText().toString();
                String pages = bookAuthor.getText().toString();

                Chapter c = new Chapter(name, Integer.valueOf(pages));
                book.addChapter(c);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static BookChaptersFragment newInstance(Book book) {
        BookChaptersFragment fragment = new BookChaptersFragment();
        Bundle bundle =  new Bundle();
        bundle.putParcelable("book", book);
        fragment.setArguments(bundle);
        return fragment;
    }
}
