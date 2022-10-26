package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main";
    private ArrayList<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            Log.i(TAG, "Setting up city list");
            File file = new File(getFilesDir(), "cityList.txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityList = (ArrayList<City>) ois.readObject();
            ois.close();
            Log.i(TAG, "Read from file");
        } catch (Exception e1) {
            Log.i(TAG, "Could not read from file");
            cityList = new ArrayList<>();
            cityList.add(new City("Champaign", 40.1164, 88.2434));
            cityList.add(new City("Chicago", 41.8781, 87.6298));
            cityList.add(new City("Los Angeles", 34.0522, 118.2437));
            try {
                File file = new File(getFilesDir(), "cityList.txt");
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(cityList);
                oos.close();
                Log.i(TAG, "Wrote to file");
            } catch (Exception e2) {
                Log.i(TAG, "Could not write to file");
                Log.e(TAG, e1.toString());
                Log.e(TAG, e2.toString());
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String username = getIntent().getStringExtra("username").toString();
        setTitle("Team 21-" + username);

        for (int i = 0; i < cityList.size(); i++) {
            Button myButton = new Button(this);
            myButton.setId(i);
            myButton.setText(cityList.get(i).cityName);
            LinearLayout ll = (LinearLayout)findViewById(R.id.cityListLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(this);
        }

        Button buttonNew = findViewById(R.id.createCity);
        Button buttonRemove = findViewById(R.id.removeCity);

        buttonNew.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            Log.i(TAG, "Setting up city list");
            File file = new File(getFilesDir(), "cityList.txt");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityList = (ArrayList<City>) ois.readObject();
            ois.close();
            Log.i(TAG, "Read from file");
        } catch (Exception e1) {
            Log.i(TAG, "Could not read from file");
        }

        LinearLayout ll = (LinearLayout) findViewById(R.id.cityListLayout);
        ll.removeAllViews();

        for (int i = 0; i < cityList.size(); i++) {
            Button myButton = new Button(this);
            myButton.setId(i);
            myButton.setText(cityList.get(i).cityName);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.createCity) {
            intent = new Intent(this, NewCityActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) cityList);
            intent.putExtra("cities", args);
            startActivity(intent);
        } else if (view.getId() == R.id.removeCity) {
            intent = new Intent(this, RemoveCitiesActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) cityList);
            intent.putExtra("cities", args);
            startActivity(intent);
        } else {
            intent = new Intent(this, DetailsActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable) cityList);
            intent.putExtra("cities", args);
            intent.putExtra("cityIdx", view.getId());
            startActivity(intent);
        }
    }
}

