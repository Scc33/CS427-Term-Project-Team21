package edu.uiuc.cs427app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NewCityActivity  extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcity);

        EditText cityInput = findViewById(R.id.cityName);
        EditText longitudeInput = findViewById(R.id.longitude);
        EditText latitudeInput = findViewById(R.id.latitude);
        Button createCity = findViewById(R.id.createCity);

        cityInput.setOnClickListener(this);
        longitudeInput.setOnClickListener(this);
        latitudeInput.setOnClickListener(this);
        createCity.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Implement this (create an Intent that goes to a new Activity, which shows the map)
    }
}
