package com.udacity.aseelalawadh.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
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


public class MainActivity extends AppCompatActivity {

    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    // URL to query the GURDIAN data set for news information
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=science&api-key=test";
    private ListView list_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NewsAsyncTask task = new NewsAsyncTask();
        task.execute();
    }

    private void updateUi(ArrayList<News> news) {

        MainAdapter adapter = new MainAdapter(this, news);
        list_view = findViewById(R.id.list);
        list_view.setAdapter(adapter);
        Log.v(LOG_TAG, String.valueOf(news));
    }

    private class NewsAsyncTask extends AsyncTask<URL, Void, ArrayList<News>> {

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
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        private String makeHttpRequest(URL url) throws IOException {
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

        private String readFromStream(InputStream inputStream) throws IOException {
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

        private ArrayList<News> extractFeatureFromJson(String newsJSON) {

            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(newsJSON)) {
                return null;
            }
            try {
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
                    News newsItem = new News(section, date, webTitle, webUrl);
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
}