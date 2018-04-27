package com.udacity.aseelalawadh.newsapp;

/**
 * Created by aseelalawadh on 26/04/2018.
 */

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.net.URL;
import java.util.List;

public class NewsLoader  extends AsyncTaskLoader<List<News>>  {


        /** Tag for log messages */
        private static final String LOG_TAG = NewsLoader.class.getName();

        /** Query URL */
        private String mUrl;


    public NewsLoader(Context context, String mUrl) {
        super(context);
        this.mUrl = mUrl;
    }

    @Override
        protected void onStartLoading() {
            forceLoad();
        }

        /**
         * This is on a background thread.
         */
        @Override
        public List<News> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            // Perform the network request, parse the response, and extract a list of earthquakes.
            List<News> news = MainActivity.fetchNewsData(mUrl);
            return news;
        }
    }

