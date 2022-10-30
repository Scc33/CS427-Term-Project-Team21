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


/**
 * The LoginActivityClass displays and handles the users details of the login screen in the app in order 
 * to store their information
 */
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


    /**
     * Displays login screen and saves and evaluates user login details   
     * @param savedInstanceState is the current action the user is trying to take 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AtomicInteger user_theme = new AtomicInteger();
        user_theme.set(-1);

        myPref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        // link UI with java code
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.userpassword);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);
        defaultTheme = findViewById(R.id.defaultThemeBtn);
        tealTheme = findViewById(R.id.tealThemeBtn);
        orangeTheme = findViewById(R.id.orangeThemeBtn);

        // click login button and perform login
        login_button.setOnClickListener(view -> {
            String userName = usernameView.getText().toString();
            String password = passwordView.getText().toString();

            if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
            } else {
                Utils.onActivityCreateSetTheme(TAG, getFilesDir(), this, userName);
                if (user_theme.get() == 1)  Utils.changeToTheme(TAG, getFilesDir(),this, Utils.THEME_TEAL, userName);
                else if (user_theme.get() == 2)  Utils.changeToTheme(TAG, getFilesDir(), this, Utils.THEME_ORANGE, userName);
                else if (user_theme.get() == 0) Utils.changeToTheme(TAG, getFilesDir(), this, Utils.THEME_DEFAULT, userName);
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

        login_button.setOnClickListener(new View.OnClickListener() {

            /**
            * Validates user login information
            * @param view stores the current view on the screen
            */      
            @Override
            public void onClick(View view) {
                String userName = usernameView.getText().toString();
                String password = passwordView.getText().toString();
                
        // user choose teal theme
        tealTheme.setOnClickListener(view -> {
            tealTheme.setBackgroundColor(Color.RED);
            defaultTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            orangeTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            user_theme.set(1);
        });

        // user choose orange theme
        orangeTheme.setOnClickListener(view -> {
            orangeTheme.setBackgroundColor(Color.RED);
            defaultTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            tealTheme.setBackgroundColor(getResources().getColor(R.color.teal_200));
            user_theme.set(2);
        });
    }

    /**
     * override onActivityResults to get returned data from signup activity.
     * If signup success, make a toast to notice user to login by the username.
     * if signup canceled, make a toast to notice it.
     *
     * @param requestCode request code for start activity
     * @param resultCode result code from launched activity
     * @param data intent object
    * */
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


    /**
     * compare the input username and login, and check if they are matched. If so, start main activity.
     * if not, make a toast to notify that username or passowrd is incorrect
     * @param userName input user name
     * @param password input password
     */
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