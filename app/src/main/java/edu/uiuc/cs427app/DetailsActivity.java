package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The DetailsActivity class displays the details of the current City the user is viewing
 * 
 * Implements View.onClickListener for navigation functionality on the app  
 */
public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "DetailsActivity";
    private String username;
    private ArrayList<City> cityList;
    private int cityIdx;

    /**
     * Creates the instance and gathers city information to be displayed 
     * @param savedInstanceState is the current action the user is trying to take 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File fileDir = getFilesDir();
        setContentView(R.layout.activity_details);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("cities");
        cityList = (ArrayList<City>) args.getSerializable("ARRAYLIST");
        cityIdx = intent.getIntExtra("cityIdx", 0);
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

        Button buttonWeather = findViewById(R.id.weatherButton);
        buttonWeather.setOnClickListener(this);

    }

    /**
     * Sends the user to their desired page
     * @param view stores the current view on the screen
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.backButton:
                finish();
                break;
            case R.id.mapButton:
                intent = new Intent(this, MapActivity.class);

                Bundle args = new Bundle();
                args.putSerializable("ARRAYLIST",(Serializable) cityList);
                intent.putExtra("cities", args);
                intent.putExtra("cityIdx", cityIdx);
                intent.putExtra("username", username);

                Log.i(TAG, "****** Starting MapActivity!");
                startActivity(intent);
                break;
            case R.id.weatherButton:
                intent = new Intent(this, WeatherActivity.class);
                Bundle argsForWeather = new Bundle();
                argsForWeather.putSerializable("ARRAYLIST",(Serializable) cityList);
                intent.putExtra("cities", argsForWeather);
                intent.putExtra("cityIdx", cityIdx);
                Log.i(TAG, "****** Starting WeatherActivity!");
                startActivity(intent);
        }
    }
}

