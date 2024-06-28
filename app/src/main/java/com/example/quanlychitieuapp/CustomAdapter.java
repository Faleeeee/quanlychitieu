package com.example.quanlychitieuapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final ArrayList<String> values;
    private final int[] icons;

    public CustomAdapter(Context context, ArrayList<String> values, int[] icons) {
        super(context, R.layout.list_item_with_icon, values);
        this.context = context;
        this.values = values;
        this.icons = icons;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_item_with_icon, parent, false);

        TextView textView = rowView.findViewById(R.id.text_view);
        ImageView imageView = rowView.findViewById(R.id.icon);

        textView.setText(values.get(position));
        imageView.setImageResource(icons[position]);

        return rowView;
    }
}
