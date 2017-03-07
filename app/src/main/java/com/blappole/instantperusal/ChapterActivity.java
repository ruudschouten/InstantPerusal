package com.blappole.instantperusal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import butterknife.ButterKnife;

public class ChapterActivity extends AppCompatActivity {
    Book book;
    Chapter chapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        book = savedInstanceState.getParcelable("book");
        chapter = savedInstanceState.getParcelable("chapter");
        assert book != null;
        assert chapter != null;
        setTitle(String.format("%s - %s", book.Name, chapter.Name));
    }
}
