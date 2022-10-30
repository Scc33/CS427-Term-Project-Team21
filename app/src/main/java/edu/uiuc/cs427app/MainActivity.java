package edu.uiuc.cs427app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The MainActivity class handles the details of the main page including the list of cities and user details 
 * 
 * Implements View.onClickListener for navigation functionality on the app  
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Main";
    private String username;
    private String cityListFile;
    private static final String TEAM = "Team 21";
    private ArrayList<City> cityList;

    /**
     * Gathers the list of cities and creates/updates the user's own city file for display
     * @param savedInstanceState is the current action the user is trying to take
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        username = getIntent().getStringExtra("username");
        cityListFile = username + "-cityList.txt";

        Utils.onActivityCreateSetTheme(TAG, getFilesDir(), this, username);
        setContentView(R.layout.activity_main);

        try {
            Log.i(TAG, "Setting up city list");
            File file = new File(getFilesDir(), cityListFile);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            cityList = (ArrayList<City>) ois.readObject();
            ois.close();
            Log.i(TAG, "Read from file");
        } catch (Exception e1) {
            Log.i(TAG, "Could not read from file");
            cityList = new ArrayList<>();
            File fileDir = getFilesDir();

            Utils.writeToFile(TAG, fileDir, cityListFile, cityList);
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

//        findViewById(R.id.defaultThemeBtn).setOnClickListener(this);
//        findViewById(R.id.tealThemeBtn).setOnClickListener(this);
//        findViewById(R.id.orangeThemeBtn).setOnClickListener(this);
    }

    /**
     * Reads cities from user's city file and displays them on the app
     */
    @Override
    public void onResume() {
        super.onResume();

        username = getIntent().getStringExtra("username");
        cityListFile = username + "-cityList.txt";

        try {
            Log.i(TAG, "Reading files");
            File file = new File(getFilesDir(), cityListFile);
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

    /**
     * Displays an options menu
     * @param menu is a list of actions to choose from 
     * @return true if a menu can be created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions, menu);
        return true;
    }

    /**
     * Executes the action the user has chosen from the options menu and logs them out if chosen    
     * @param item is the action the user chose
     * @return true if an action from the menu was selected
     */
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

    /**
     * Executes user's desired action on the main page screen 
     * @param view stores the current view on the screen
     */
    @Override
    public void onClick(View view) {
        File fileDir = getFilesDir();

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
//        } else if (view.getId() == R.id.defaultThemeBtn) {
//            Utils.changeToTheme(TAG, fileDir, this, Utils.THEME_DEFAULT, username);
//        } else if (view.getId() == R.id.tealThemeBtn) {
//            Utils.changeToTheme(TAG, fileDir,this, Utils.THEME_TEAL, username);
//        } else if (view.getId() == R.id.orangeThemeBtn) {
//            Utils.changeToTheme(TAG, fileDir, this, Utils.THEME_ORANGE, username);
        } else {
            intent = new Intent(this, DetailsActivity.class);
            Bundle args = new Bundle();
            args.putSerializable("ARRAYLIST",(Serializable) cityList);
            intent.putExtra("cities", args);
            intent.putExtra("cityIdx", view.getId());
            intent.putExtra("username", username);
            String themeResId = getResources().getResourceName(getApplicationInfo().theme);
            Log.i(TAG, "****** themeResId- "+ themeResId);
            intent.putExtra("themeIdx", themeResId);
            startActivity(intent);
        }
    }
}

