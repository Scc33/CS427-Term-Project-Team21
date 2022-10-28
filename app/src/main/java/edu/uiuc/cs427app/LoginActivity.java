package edu.uiuc.cs427app;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * The LoginActivityClass displays and handles the users details of the login screen in the app in order 
 * to store their information
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences myPref;

    // UI reference
    private EditText usernameView;
    private EditText passwordView;
    private Button login_button;
    private Button signup_button;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;


    /**
     * Displays login screen and saves and evaluates user login details   
     * @param savedInstanceState is the current action the user is trying to take 
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = myPref.edit();
//
//        editor.putString("admin", "123");
//        editor.commit();

        // link everything
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.userpassword);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);


        login_button.setOnClickListener(new View.OnClickListener() {

            /**
            * Validates user login information
            * @param view stores the current view on the screen
            */      
            @Override
            public void onClick(View view) {
                String userName = usernameView.getText().toString();
                String password = passwordView.getText().toString();

                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                } else {
                    loginAttempt(userName, password);
                }
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivityForResult(i, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    /**
     * Handles sign up activity for new users    
     * @param requestCode validates the user's action  
     * @param resultCode is the status of the user's sign up attempt  
     * @param data is the users login data 
     */
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
     * Checks user's login information and logs them into the app     
     * @param userName is the users login username  
     * @param password is the users login password  
     */
    private void loginAttempt(String userName, String password) {

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