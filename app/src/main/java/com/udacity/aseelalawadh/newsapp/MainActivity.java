package com.udacity.aseelalawadh.newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    // URL to query the GURDIAN data set for news information
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=science&api-key=test";

   /**
      * Constant value for the earthquake loader ID. We can choose any integer.
      * This really only comes into play if you're using multiple loaders.
    */
   private static final int NEWS_LOADER_ID = 1;
    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    private ListView list_view;
    private MainAdapter mAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new MainAdapter(this, new ArrayList<News>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentNews.getNewsUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(NEWS_LOADER_ID, null, this);
       /* LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        list_view.setEmptyView(mEmptyStateTextView);*/







       /*

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
             getSystemService(Context.CONNECTIVITY_SERVICE);
          // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


// If there is a network connection, fetch data
            if (networkInfo != null && networkInfo.isConnected()) {
     // Get a reference to the LoaderManager, in order to interact with loaders.
                 LoaderManager loaderManager = getLoaderManager();

             // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                // because this activity implements the LoaderCallbacks interface).
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
              } else {
                   // Otherwise, display error
                     // First, hide loading indicator so error message will be visible
                           View loadingIndicator = findViewById(R.id.loading_indicator);
              loadingIndicator.setVisibility(View.GONE);
                   // Update empty state with no connection error message
                mEmptyStateTextView.setText(R.string.no_internet_connection);
                   }


       *//* NewsAsyncTask task = new NewsAsyncTask();
        task.execute();*/
    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        // TODO: Create a new loader for the given URL
        return  new NewsLoader(this , GUARDIAN_REQUEST_URL) ;
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newses) {
        // TODO: Update the UI with the result
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_news);
        mAdapter .clear();

        if(newses != null && !newses.isEmpty()){
            mAdapter.addAll(newses);

        }
        if (newses == null) {
            return;
        }
       // updateUi(newses);

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // TODO: Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
    private void updateUi(ArrayList<News> news) {

        MainAdapter adapter = new MainAdapter(this, news);
        list_view = findViewById(R.id.list);
        list_view.setAdapter(adapter);
        Log.v(LOG_TAG, String.valueOf(news));
    }

   /* private class NewsAsyncTask extends AsyncTask<URL, Void, ArrayList<News>> {

        @Override
        protected ArrayList<News> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(GUARDIAN_REQUEST_URL);
            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }
            ArrayList<News> news = extractFeatureFromJson(jsonResponse);
            return news;
        }

        @Override
        protected void onPostExecute(ArrayList<News> news) {
            if (news == null) {
                return;
            }
            updateUi(news);
        }*/

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    /**
     * Query the USGS dataset and return a list of {@link } objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of {@link Earthquake}s
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of {@link Earthquake}s
        return news;
    }




        public static String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";
            // If the URL is null, then return early.
            if (url == null) {
                return jsonResponse;
            }
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.v(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.v(LOG_TAG, e.getMessage());
                Log.v(LOG_TAG, "Problem retrieving the News JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private static String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        public static ArrayList<News> extractFeatureFromJson(String newsJSON) {

            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(newsJSON)) {
                return null;
            }
            try {
                /*try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                JSONObject baseJsonResponse = new JSONObject(newsJSON);
                JSONObject response = baseJsonResponse.getJSONObject("response");

                JSONArray resultsArray = response.getJSONArray("results");
                ArrayList<News> newsList = new ArrayList<>();
                for (int i = 0; i < resultsArray.length(); i++) {

                    JSONObject item = resultsArray.getJSONObject(i);
                    String section = item.getString("sectionId");
                    String date = item.getString("webPublicationDate");
                    String webTitle = item.getString("webTitle");
                    String webUrl = item.getString("webUrl");
                    String pillarName = item.getString("pillarName");
                    News newsItem = new News(section, date, webTitle, webUrl, pillarName);
                    newsList.add(newsItem);
                }

                Log.v(LOG_TAG, String.valueOf(newsList));
                return newsList;
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the News JSON results", e);
            }
            return null;
        }
    }
