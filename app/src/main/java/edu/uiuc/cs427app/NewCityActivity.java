package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;

public class NewCityActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NewCity";
    private String username;
    private ArrayList<City> cityList;

    private EditText cityInput;
    private EditText longitudeInput;
    private EditText latitudeInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_newcity);

        Intent intent = getIntent();
        username = getIntent().getStringExtra("username");
        Bundle args = intent.getBundleExtra("cities");
        cityList = (ArrayList<City>) args.getSerializable("ARRAYLIST");

        cityInput = findViewById(R.id.cityName);
        longitudeInput = findViewById(R.id.longitude);
        latitudeInput = findViewById(R.id.latitude);
        Button createCity = findViewById(R.id.createCity);

        cityInput.setOnClickListener(this);
        longitudeInput.setOnClickListener(this);
        latitudeInput.setOnClickListener(this);
        createCity.setOnClickListener(this);
    }

    /**
     *
     *
     * @param view stores the current view on the screen
     */
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.createCity) {
            if (!cityInput.getText().toString().isEmpty() &&
                    !longitudeInput.getText().toString().isEmpty() &&
                    !latitudeInput.getText().toString().isEmpty()) {
                cityList.add(
                        new City(
                                cityInput.getText().toString(),
                                Double.parseDouble(longitudeInput.getText().toString()),
                                Double.parseDouble(latitudeInput.getText().toString())
                        )
                );
                File fileDir = getFilesDir();
                final String filename = username + "-cityList.txt";
                Utils.writeToFile(TAG, fileDir, filename, cityList);
                finish();
            } else {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Fill out all the fields", Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    }
}
