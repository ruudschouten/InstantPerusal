package com.blappole.instantperusal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

class ChapterArrayAdapter extends ArrayAdapter<Chapter> {
    ChapterArrayAdapter(Context context, int resource, List<Chapter> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.chapter_layout, null);
        }

        Chapter c = getItem(position);

        if (c != null) {
            TextView title = (TextView) v.findViewById(R.id.tvChapterTitle);
            TextView page = (TextView) v.findViewById(R.id.tvChapterPages);
            TextView time = (TextView) v.findViewById(R.id.tvChapterTime);
            title.setText(c.Name);
            page.setText(String.format("%s pages",c.Pages));
            if (time != null) {
                if (c.TimeSpent == null) {
                    time.setText("Not started");
                } else {
                    time.setText(c.TimeSpent.toString());
                }
            }
        }
        return v;
    }
}
