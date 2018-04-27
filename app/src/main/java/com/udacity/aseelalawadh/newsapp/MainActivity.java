package com.udacity.aseelalawadh.newsapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    // URL to query the GURDIAN data set for news information
    private static final String GUARDIAN_REQUEST_URL =
            "http://content.guardianapis.com/search?order-by=newest&show-references=author&show-tags=contributor&q=Android&api-key=test";
    private static final int NEWS_LOADER_ID = 1;

    private TextView mEmptyStateTextView;
    private ListView list_view;
    private MainAdapter mAdapter;
    private NewsLoader loader;
    private View loadingIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the adapter on the  ListView so the list can be populated in the user interface
        mEmptyStateTextView = findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.no_news);
        loadingIndicator = findViewById(R.id.loading_indicator);
        this.updateUi(new ArrayList<News>());
        // Set an item click listener on the ListView, which sends an intent to a web browser
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News currentNews = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(currentNews.getNewsUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(websiteIntent);
            }
        });
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loader = new NewsLoader(this, GUARDIAN_REQUEST_URL);
            loader.forceLoad();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // hide loading indicator so error message will be visible
            loadingIndicator.setVisibility(View.GONE);
            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        loadingIndicator.setVisibility(View.VISIBLE);
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        // Set empty state text to display "No news found."
        loadingIndicator.setVisibility(View.GONE);
        if (newses != null && !newses.isEmpty()) {
            mEmptyStateTextView.setVisibility(View.GONE);
            mAdapter.addAll(newses);
            mAdapter.notifyDataSetChanged();
        } else if (newses == null) {
            mEmptyStateTextView.setText(R.string.no_news);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mAdapter.clear();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }

    private void updateUi(ArrayList<News> news) {
        mAdapter = new MainAdapter(this, news);
        list_view = findViewById(R.id.list);
        list_view.setAdapter(mAdapter);
        Log.v(LOG_TAG, String.valueOf(news));
    }
}
