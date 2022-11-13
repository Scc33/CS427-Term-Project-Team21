package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The map activity which shows the city maps for users
 */
public class MapActivity extends AppCompatActivity {

    private WebView webView;
    private final static String TAG ="MapActivity";

    /**
     * Function is used by the Android operating system to create the map activity for users.
     *
     * @param savedInstanceState used by the Android operating system
     */
    @SuppressLint("SetTextI18n")
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
        double latitude = Double.parseDouble(String.valueOf(cityList.get(cityIdx).latitude));
        double longitude = Double.parseDouble(String.valueOf(cityList.get(cityIdx).longitude));

        Log.i(TAG, "City Attribute || cityIdx = " + cityIdx);
        Log.i(TAG, "City Attribute  || cityName = " + cityName);
        Log.i(TAG, "City Attribute  || latitude = " + latitude);
        Log.i(TAG, "City Attribute  || longitude = " + longitude);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        setTitle(cityName);
        TextView latLong = findViewById(R.id.latLong);
        latLong.setText("Latitude: " + latitude + ", Longitude: " + longitude);

        webView = (WebView) findViewById(R.id.webView);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/maps/place/"+cityName+"/@"+latitude+","+longitude);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}