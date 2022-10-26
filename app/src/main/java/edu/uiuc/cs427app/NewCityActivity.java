package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class NewCityActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "NewCity";

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
                try {
                    Log.i(TAG, "Writing to file");
                    File file = new File(getFilesDir(), "cityList.txt");
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(cityList);
                    oos.close();
                    Log.i(TAG, "Wrote to file");
                } catch (Exception e2) {
                    Log.i(TAG, "Could not write to file");
                    Log.e(TAG, e2.toString());
                }
                finish();
            } else {
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Fill out all the fields", Snackbar.LENGTH_LONG)
                        .show();
            }
        }
    }
}
