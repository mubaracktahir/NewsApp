package com.mubaracktahir.newsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    static final String KEY_AUTHOR = "author";
    static final String KEY_TITLE = "title";
    static final String KEY_DESCRIPTION = "description";
    static final String KEY_URL = "url";
    static final String KEY_URLTOIMAGE = "urlToImage";
    static final String KEY_PUBLISHEDAT = "publishedAt";
    String API_KEY = "46c3fe92f4ad4d9d957161671d120429";
    String NEWS_SOURCE = "bbc-news";
    ListView listNews;
    ProgressBar loader;
    ArrayList<HashMap<String, String>> dataList = new ArrayList<>();
    String author;
    String title;
    String description;
    String url;
    String keyUrltoimage;
    String publishDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = findViewById(R.id.listNews);
        loader = findViewById(R.id.loader);
        listNews.setEmptyView(loader);


        if (Function.isNetworkAvailable(getApplicationContext())) {
            DownloadNews newsTask = new DownloadNews();
            newsTask.execute();
        } else {

            Toast.makeText(getApplicationContext(), "No Internet Connection,\nturn on mobile data or connect to WiFi", Toast.LENGTH_LONG).show();
        }

    }

    public void sendTabedNews() {
        MyAdapter adapter = new MyAdapter(MainActivity.this, dataList);

        listNews.setAdapter(adapter);

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent i = new Intent(MainActivity.this, ShowNewsActivity.class);
                i.putExtra("url", dataList.get(position).get(MainActivity.KEY_URL));
                startActivity(i);
            }
        });

    }

    class DownloadNews extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        protected String doInBackground(String... args) {
            String xml = "";

            String urlParameters = "";
            xml = Function.getNews("https://newsapi.org/v1/articles?source=" + NEWS_SOURCE + "&sortBy=top&apiKey=" + API_KEY);
            return xml;
        }

        @Override
        protected void onPostExecute(String xml) {

            if (xml.length() > 10) {

                // Just checking if not empty

                try {
                    JSONObject jsonResponse = new JSONObject(xml);
                    JSONArray jsonArray = jsonResponse.optJSONArray("articles");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<>();
                        author = jsonObject.optString(KEY_AUTHOR);
                        title = jsonObject.optString(KEY_TITLE);
                        description = jsonObject.optString(KEY_DESCRIPTION);
                        url = jsonObject.optString(KEY_URL);
                        keyUrltoimage = jsonObject.optString(KEY_URLTOIMAGE);
                        publishDate = jsonObject.optString(KEY_PUBLISHEDAT);

                        map.put(KEY_AUTHOR, author);
                        map.put(KEY_TITLE, title);
                        map.put(KEY_DESCRIPTION, description);
                        map.put(KEY_URL, url);
                        map.put(KEY_URLTOIMAGE, keyUrltoimage);
                        map.put(KEY_PUBLISHEDAT, publishDate);

                        dataList.add(map);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Unexpected error, reload.", Toast.LENGTH_SHORT).show();
                }

                MyAdapter adapter = new MyAdapter(MainActivity.this, dataList);

                listNews.setAdapter(adapter);

                listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent i = new Intent(MainActivity.this, ShowNewsActivity.class);
                        i.putExtra("url", dataList.get(position).get(KEY_URL));
                        startActivity(i);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Oops! no news found.", Toast.LENGTH_SHORT).show();
            }
        }


    }


}