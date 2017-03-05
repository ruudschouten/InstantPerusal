package com.blappole.instantperusal;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookActivity extends AppCompatActivity {
    @BindView(R.id.fabAdd) FloatingActionButton fabAdd;
    @BindView(R.id.fabEdit) FloatingActionButton fabEdit;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.container) ViewPager viewPager;

    private BookPagerAdapter bookPagerAdapter;
    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        if(savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        book = savedInstanceState.getParcelable("book");
        assert book != null;
        setTitle(book.Name);
        setSupportActionBar(toolbar);
        bookPagerAdapter = new BookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bookPagerAdapter);
    }

    private class BookPagerAdapter extends FragmentPagerAdapter {
        BookPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return BookDetailsFragment.newInstance(book);
                case 1: return BookChaptersFragment.newInstance(book);
            }
            return new Fragment();
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
