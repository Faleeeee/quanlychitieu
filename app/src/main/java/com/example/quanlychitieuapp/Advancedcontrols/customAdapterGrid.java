package com.example.quanlychitieuapp.Advancedcontrols;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.quanlychitieuapp.R;

public class customAdapterGrid extends BaseAdapter {
    Context context;
    int logo[];
    LayoutInflater inflter;

    public customAdapterGrid(Context context, int[] logo) {
        super();
        this.context = context;
        this.logo = logo;
        inflter = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return logo.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup arg2) {

        convertView = inflter.inflate(R.layout.activity_grid_view, null);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        icon.setImageResource(logo[position]);

        return convertView;
    }
}
