package com.mubaracktahir.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

/**
 * Mubarack tahir Mubaracks-PC on 15/07/2019.
 */

public class ShowNewsActivity extends AppCompatActivity {

    WebView webView;
    ProgressBar loader;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = new Intent();
        url = intent.getStringExtra("url");
        loader = findViewById(R.id.loader);
        webView = findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().getMediaPlaybackRequiresUserGesture();
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.loadUrl(url);


       webView.setWebViewClient(new WebViewClient() {

            public void onProgressChanged(WebView view, int progress) {
                if (progress == 60) {

                    Toast.makeText(getApplicationContext(),"am done loading ",Toast.LENGTH_SHORT).show();
                    loader.setVisibility(View.GONE);
                } else {
                    loader.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}
