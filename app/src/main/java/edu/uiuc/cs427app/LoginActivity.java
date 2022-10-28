package edu.uiuc.cs427app;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences myPref;

    // UI reference
    private static final String TAG = "Login";
    private EditText usernameView;
    private EditText passwordView;
    private Button login_button;
    private Button signup_button;
    private Button defaultTheme;
    private Button tealTheme;
    private Button orangeTheme;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AtomicInteger user_theme = new AtomicInteger();

        myPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        // link everything
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.userpassword);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);
        defaultTheme = findViewById(R.id.defaultThemeBtn);
        tealTheme = findViewById(R.id.tealThemeBtn);
        orangeTheme = findViewById(R.id.orangeThemeBtn);


        login_button.setOnClickListener(view -> {
            String userName = usernameView.getText().toString();
            String password = passwordView.getText().toString();

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
            } else {
                Utils.onActivityCreateSetTheme(TAG, getFilesDir(), this, userName);
                switch (user_theme.get()){
                    case 1:
                        Utils.changeToTheme(TAG, getFilesDir(),this, Utils.THEME_TEAL, userName);
                        break;
                    case 2:
                        Utils.changeToTheme(TAG, getFilesDir(), this, Utils.THEME_ORANGE, userName);
                        break;
                    default:
                        Utils.changeToTheme(TAG, getFilesDir(), this, Utils.THEME_DEFAULT, userName);
                }
                loginAttempt(userName, password);
            }
        });

        signup_button.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, SignupActivity.class);
            startActivityForResult(i, SECOND_ACTIVITY_REQUEST_CODE);
        });

        defaultTheme.setOnClickListener(view -> {
            defaultTheme.setBackgroundColor(Color.RED);
            tealTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            orangeTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            user_theme.set(0);
        });

        tealTheme.setOnClickListener(view -> {
            tealTheme.setBackgroundColor(Color.RED);
            defaultTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            orangeTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            user_theme.set(1);
        });

        orangeTheme.setOnClickListener(view -> {
            orangeTheme.setBackgroundColor(Color.RED);
            defaultTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            tealTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            user_theme.set(2);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SECOND_ACTIVITY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                String result = data.getStringExtra("new_user");
                String toast = "Please Sign in as: " + result;
                Toast.makeText(LoginActivity.this, toast, Toast.LENGTH_LONG).show();
            }
            if (resultCode == Activity.RESULT_CANCELED){
                Toast.makeText(LoginActivity.this, "Sign Up Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }



    private void loginAttempt(String userName, String password){

        String attempt_password = myPref.getString(userName, "");
        if(password.equals(attempt_password)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            i.putExtra("username", userName);
            startActivity(i);
            finish();
        }
        else {
            Toast.makeText(this, "Incorrect user name or password", Toast.LENGTH_SHORT).show();
        }
    }

}