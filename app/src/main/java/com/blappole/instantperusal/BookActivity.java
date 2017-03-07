package com.blappole.instantperusal;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookActivity extends AppCompatActivity {
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.container) ViewPager viewPager;
    @BindView(R.id.tbLayout) TabLayout tabLayout;

    private Book book;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        book = savedInstanceState.getParcelable("book");
        assert book != null;
        setTitle(book.Name);
        setSupportActionBar(toolbar);
        BookPagerAdapter bookPagerAdapter = new BookPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(bookPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private class BookPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"Details", "Chapters"};
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

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
