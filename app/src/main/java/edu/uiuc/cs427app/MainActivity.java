package edu.uiuc.cs427app;

import android.content.Intent;
import android.graphics.Color;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import edu.uiuc.cs427app.databinding.ActivityMainBinding;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.Spinner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main";
    private String username;
    private String filename;
    private static final String TEAM = "Team 21";
    private ArrayList<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        username = getIntent().getStringExtra("username");
        filename = username + "-cityList.txt";

        try {
            Log.i(TAG, "Setting up city list");
            File file = new File(getFilesDir(), filename);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityList = (ArrayList<City>) ois.readObject();
            ois.close();
            Log.i(TAG, "Read from file");
        } catch (Exception e1) {
            Log.i(TAG, "Could not read from file");
            cityList = new ArrayList<>();

            try {
                File file = new File(getFilesDir(), filename);
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
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        setTitle(TEAM+"-"+username);

        for (int i = 0; i < cityList.size(); i++) {
            Button myButton = new Button(this, null, getApplicationInfo().theme);
            myButton.setId(i);
            myButton.setText(cityList.get(i).cityName);
            LinearLayout ll = (LinearLayout) findViewById(R.id.cityListLayout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(this);
        }

        Button buttonNew = findViewById(R.id.createCity);
        Button buttonRemove = findViewById(R.id.removeCity);

        buttonNew.setOnClickListener(this);
        buttonRemove.setOnClickListener(this);

        findViewById(R.id.defaultThemeBtn).setOnClickListener(this);
        findViewById(R.id.tealThemeBtn).setOnClickListener(this);
        findViewById(R.id.orangeThemeBtn).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();

        username = getIntent().getStringExtra("username");
        filename = username + "-cityList.txt";

        try {
            Log.i(TAG, "Setting up city list");
            File file = new File(getFilesDir(), filename);
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.logout: {
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view.getId() == R.id.createCity) {
            intent = new Intent(this, NewCityActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) cityList);
            intent.putExtra("cities", args);
            intent.putExtra("username", username);
            String themeResId = getResources().getResourceName(getApplicationInfo().theme);
            Log.i(TAG, "****** themeResId- "+ themeResId);
            startActivity(intent);
        } else if (view.getId() == R.id.removeCity) {
            intent = new Intent(this, RemoveCitiesActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST", (Serializable) cityList);
            intent.putExtra("cities", args);
            intent.putExtra("username", username);
            String themeResId = getResources().getResourceName(getApplicationInfo().theme);
            Log.i(TAG, "****** themeResId- "+ themeResId);
            startActivity(intent);
        } else if (view.getId() == R.id.defaultThemeBtn) {
            Log.i(TAG, "Setting Default Theme");
            Utils.changeToTheme(this, Utils.THEME_DEFAULT, username);
        } else if (view.getId() == R.id.tealThemeBtn) {
            Log.i(TAG, "Setting Teal Theme");
            Utils.changeToTheme(this, Utils.THEME_TEAL, username);
        } else if (view.getId() == R.id.orangeThemeBtn) {
            Log.i(TAG, "Setting Orange Theme");
            Utils.changeToTheme(this, Utils.THEME_ORANGE, username);
        } else {
            intent = new Intent(this, DetailsActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable) cityList);
            intent.putExtra("cities", args);
            intent.putExtra("cityIdx", view.getId());
            String themeResId = getResources().getResourceName(getApplicationInfo().theme);
            Log.i(TAG, "****** themeResId- "+ themeResId);
            intent.putExtra("themeIdx", themeResId);
            startActivity(intent);
        }
    }
}

