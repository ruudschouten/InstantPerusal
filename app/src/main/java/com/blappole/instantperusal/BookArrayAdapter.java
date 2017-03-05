package com.blappole.instantperusal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Objects;

class BookArrayAdapter extends ArrayAdapter<Book> {
    BookArrayAdapter(Context context, int resource, List<Book> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.book_layout, null);
        }

        Book b = getItem(position);

        if (b != null) {
            TextView title = (TextView) v.findViewById(R.id.tvBookTitle);
            TextView time = (TextView) v.findViewById(R.id.tvAverageTime);
            title.setText(b.Name);
            String avgTime = b.getAverageTimePerChapter();
            if(!Objects.equals(avgTime, "0 milliseconds")) {
                time.setText(b.getAverageTimePerChapter());
            }
        }
        return v;
    }
}
