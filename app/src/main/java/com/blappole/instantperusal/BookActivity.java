package com.blappole.instantperusal;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

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
        setSupportActionBar(toolbar);
        bookPagerAdapter = new BookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bookPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0) {
                    fabAdd.setVisibility(View.GONE);
                    fabEdit.setVisibility(View.VISIBLE);
                    setTitle("Details");
                } else {
                    setTitle("Chapters");
                    fabAdd.setVisibility(View.VISIBLE);
                    fabEdit.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
