package com.udacity.aseelalawadh.newsapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
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

    private SharedPreferences mSharedPref;
    private Context mContext;
    private TextView _authorName;
    private TextView _newsSection;
    private TextView _newsDate;
    private TextView _newsDetails;

    public MainAdapter(Context context, ArrayList<News> items) {
        super(context, 0, items);
        mSharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        mContext = context;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        News item = getItem(position);

        _newsSection = listItemView.findViewById(R.id.section);
        _newsSection.setText(String.valueOf(item == null ? "" : item.getSection()));

        _newsDate = listItemView.findViewById(R.id.date);
        _newsDate.setText(String.valueOf(""));

        _newsDetails = listItemView.findViewById(R.id.news);
        _newsDetails.setText(String.valueOf(item == null ? "" : item.getNewsDetails()));

        _authorName = listItemView.findViewById(R.id.authorName);
        _authorName.setText(String.valueOf(""));

        if (item != null && item.getAuthorName() != null) {
            _authorName.setVisibility(View.VISIBLE);
            _authorName.setText(String.valueOf(item.getAuthorName()));
        }
        if (item != null && item.getDate() != null) {
            _newsDate.setVisibility(View.VISIBLE);
            _newsDate.setText(String.valueOf(item.getDate()));
        }

        validateSettings();
        return listItemView;
    }

    private void validateSettings() {
        if (!mSharedPref.getBoolean(mContext.getString(R.string.pref_show_author), true)) {
            _authorName.setVisibility(View.GONE);
        }
        if (!mSharedPref.getBoolean(mContext.getString(R.string.pref_show_date), true)) {
            _newsDate.setVisibility(View.GONE);
        }
    }
}
