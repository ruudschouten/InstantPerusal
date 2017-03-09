package com.blappole.instantperusal;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blappole.instantperusal.Database.DbContract;
import com.blappole.instantperusal.Database.DbHelper;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookChaptersFragment extends Fragment {
    @BindView(R.id.fabAdd) FloatingActionButton fabAdd;
    @BindView(R.id.frChaptersEmpty) TextView tvEmpty;
    @BindView(R.id.frChapterContainer) ListView lvChapters;
    Book book;
    ChapterArrayAdapter adapter;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_book_chapters, container, false);
        ButterKnife.bind(this, v);

        if(savedInstanceState == null) {
            savedInstanceState = getArguments();
        }

        book = savedInstanceState.getParcelable("book");
        assert book != null;

        adapter = new ChapterArrayAdapter(v.getContext(), R.layout.chapter_layout, book.Chapters);
        lvChapters.setAdapter(adapter);
        lvChapters.setEmptyView(tvEmpty);
        lvChapters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //TODO: Open new Chapter intent
                Chapter chapter = (Chapter) lvChapters.getItemAtPosition(i);
                Intent intent = new Intent(v.getContext(), ChapterActivity.class);
                intent.putExtra("book", book);
                intent.putExtra("chapter", chapter);
                startActivity(intent);
            }
        });

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
                addChapterDb(book, c);
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


    public void addChapterDb(Book b, Chapter c){
        DbHelper dbHelper = new DbHelper(getContext());
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbContract.ChapterEntry.COLUMN_NAME_NAME, c.Name);
        values.put(DbContract.ChapterEntry.COLUMN_NAME_PAGES, c.Pages);
        values.put(DbContract.ChapterEntry.COLUMN_NAME_BOOK, b.Id);

        c.Id = db.insert(DbContract.ChapterEntry.TABLE_NAME, null, values);

        values = new ContentValues();

        db.insert(DbContract.ChapterEntry.TABLE_NAME, null, values);
    }
}
