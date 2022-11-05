package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {

    private WebView webView;
    private final static String TAG ="MapActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.i(TAG, "****** Inside MapActivity!");

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("cities");
        ArrayList<City> cityList = (ArrayList<City>) args.getSerializable("ARRAYLIST");
        int cityIdx = intent.getIntExtra("cityIdx", 0);
        String cityName = cityList.get(cityIdx).cityName;
        Double latitude = Double.parseDouble(String.valueOf(cityList.get(cityIdx).latitude));
        Double longitude = Double.parseDouble(String.valueOf(cityList.get(cityIdx).longitude));

        Log.i(TAG, "City Attribute || cityIdx = " + cityIdx);
        Log.i(TAG, "City Attribute  || cityName = " + cityName);
        Log.i(TAG, "City Attribute  || latitude = " + latitude);
        Log.i(TAG, "City Attribute  || longitude = " + longitude);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://maps.google.com/maps?q=41.881832,-87.623177");
//        webView.loadUrl("https://www.google.com/maps/place/Chicago/@41.881832,-87.623177");
        webView.loadUrl("https://www.google.com/maps/place/"+cityName+"/@"+latitude+","+longitude);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }
}