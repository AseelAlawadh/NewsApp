package com.udacity.aseelalawadh.newsapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    ArrayList<HashMap<String, String>> newsList;
    private String TAG = MainActivity.class.getSimpleName();
    private ListView list_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newsList = new ArrayList<>();
        list_view = (ListView) findViewById(R.id.list);

        new GetNews().execute();
    }

    private class GetNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // TODO: make a request to the URL
            String url = "https://content.guardianapis.com/search?q=debates&api-key=test";
            String jsonString = null;
            try {
                jsonString = sh.makeHttpRequset(createUrl(url));
                Log.v(TAG, "json string " + jsonString);
                return parsingJSON(jsonString);
            } catch (IOException e) {
                Log.v(TAG, e.fillInStackTrace().toString());
                return null;
            }
        }


        protected Void parsingJSON(String jsonString) {

            Log.e(TAG, "Response from url 111: " + jsonString);
            if (jsonString == null) {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server.",
                                Toast.LENGTH_LONG).show();
                    }
                });
                return null;
            }

            try {
                //TODO: Create a new JSONObject
                JSONObject jsonObj = new JSONObject(jsonString);

                // TODO: Get the JSON Array node
                JSONArray news = jsonObj.getJSONArray("news");

                // looping through all Contacts
                for (int i = 0; i < news.length(); i++) {
                    //TODO: get the JSONObject
                    JSONObject c = news.getJSONObject(i);
                    String id = c.getString("id");
                    String name = c.getString("name");


                    // tmp hash map for a single pokemon
                    HashMap<String, String> newses = new HashMap<>();

                    // add each child node to HashMap key => value
                    newses.put("id", id);
                    newses.put("name", name);


                    // adding a news to our news list
                    newsList.add(newses);
                }
            } catch (final JSONException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                return null;
            }
            return url;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this, newsList,
                    R.layout.list_item, new String[]{"id", "name"},
                    new int[]{R.id.id, R.id.name});
            list_view.setAdapter(adapter);
        }
    }
}
