package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<City> cityList;
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityList = new ArrayList<City>();
        cityList.add(new City("Champaign", 40.1164, 88.2434));
        cityList.add(new City("Chicago", 41.8781, 87.6298));
        cityList.add(new City("Los Angeles", 34.0522, 118.2437));

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

        buttonNew.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.createCity) {
            intent = new Intent(this, NewCityActivity.class);
            startActivity(intent);
        } else {
            intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("city", cityList.get(view.getId()).cityName);
            intent.putExtra("latitude", cityList.get(view.getId()).latitude);
            intent.putExtra("longitude", cityList.get(view.getId()).longitude);
            startActivity(intent);
        }
    }
}

