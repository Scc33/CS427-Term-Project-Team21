package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailsActivity";
    private String username;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File fileDir = getFilesDir();
        setContentView(R.layout.activity_details);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("cities");
        ArrayList<City> cityList = (ArrayList<City>) args.getSerializable("ARRAYLIST");
        int cityIdx = intent.getIntExtra("cityIdx", 0);
        username = getIntent().getStringExtra("username");

        Utils.onActivityCreateSetTheme(TAG, getFilesDir(), this, username);

        String cityName = cityList.get(cityIdx).cityName;
        String welcome = "Welcome to the " + cityName;
        String cityWeatherInfo = "Detailed information about the weather of " + cityName;

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);
        // Get the weather information from a Service that connects to a weather server and show the results

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        Button buttonMap = findViewById(R.id.mapButton);
        buttonMap.setOnClickListener(this);

    }

    /**
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.mapButton:
                // TODO implement this (create an Intent that goes to a new Activity, which shows the map)
                break;
        }
    }
}

