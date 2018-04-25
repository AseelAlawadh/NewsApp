package com.udacity.aseelalawadh.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aseelalawadh on 25/04/2018.
 */

public class MainAdapter extends ArrayAdapter<News> {

    public MainAdapter(MainActivity context, ArrayList<News> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News item = getItem(position);


        TextView newsSection = listItemView.findViewById(R.id.section);
        newsSection.setText(item.getSection());

//        TextView newsId = listItemView.findViewById(R.id.id);
//        newsId.setText(item.getId());
        TextView newsDate = listItemView.findViewById(R.id.date);
        newsDate.setText(item.getDate());

        TextView newsDetails = listItemView.findViewById(R.id.news);
        newsDetails.setText(item.getNewsDetails());


        return listItemView;
    }
}
