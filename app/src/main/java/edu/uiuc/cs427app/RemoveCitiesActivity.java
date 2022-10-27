package edu.uiuc.cs427app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class RemoveCitiesActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "RemoveCity";
    private String username;
    private ArrayList<City> cityList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_remove);

        Intent intent = getIntent();
        username = getIntent().getStringExtra("username");
        Bundle args = intent.getBundleExtra("cities");
        cityList = (ArrayList<City>) args.getSerializable("ARRAYLIST");

        LinearLayout ll = (LinearLayout) findViewById(R.id.removeCitiesList);
        ll.removeAllViews();

        for (int i = 0; i < cityList.size(); i++) {
            Button myButton = new Button(this);
            myButton.setId(i);
            myButton.setText("Remove " + cityList.get(i).cityName);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            ll.addView(myButton, lp);
            myButton.setOnClickListener(this);
        }

        Button removeCities = findViewById(R.id.removeCity);

        removeCities.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.removeCity) {
            try {
                Log.i(TAG, "Writing to file");
                File file = new File(getFilesDir(), username + "-cityList.txt");
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
            Button cityToRemove = findViewById(view.getId());
            cityToRemove.setClickable(false);
            ViewGroup layout = (ViewGroup) cityToRemove.getParent();
            if (layout != null) {
                layout.removeView(cityToRemove);
            }
            cityList.remove(view.getId());
        }
    }
}
