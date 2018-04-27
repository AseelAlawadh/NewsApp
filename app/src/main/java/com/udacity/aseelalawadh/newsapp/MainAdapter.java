package com.udacity.aseelalawadh.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

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

        TextView newsDate = listItemView.findViewById(R.id.date);
        newsDate.setText(item.getDate());

        TextView newsDetails = listItemView.findViewById(R.id.news);
        newsDetails.setText(item.getNewsDetails());

        TextView newsUrl = listItemView.findViewById(R.id.newsUrl);
        newsUrl.setText(item.getNewsUrl());

        TextView pillarName = listItemView.findViewById(R.id.pillarName);
        pillarName.setText(item.getPillarName());

        TextView authorName = listItemView.findViewById(R.id.authorName);
        authorName.setText(item.getAuthorName());

        return listItemView;
    }
}
