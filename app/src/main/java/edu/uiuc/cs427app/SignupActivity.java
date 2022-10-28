package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.AlgorithmConstraints;

/**
 * The SignupActivity class handles the details of signing up a new user and validating details  
 * 
 * Implements View.onClickListener for navigation functionality on the app  
 */
public class SignupActivity extends AppCompatActivity {

    final int SIGNUP_SUCCESS = 1;

    // UI reference
    private EditText usernameView;
    private EditText PasswordView;
    private EditText ConfirmPasswordView;
    final int MIN_PASSWORD_LENGTH = 6;
    private Button signup_button;
    private Button cancel_button;

    /**
     * Reads and stores user's inputted sign up information  
     * @param savedInstanceState is the current action the user is trying to take
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameView = (EditText) findViewById(R.id.username);
        PasswordView = (EditText) findViewById(R.id.userpassword);
        ConfirmPasswordView = (EditText) findViewById(R.id.confirmuserpassword);
        signup_button = findViewById(R.id.signup_button);
        cancel_button = findViewById(R.id.signup_cancel);


        signup_button.setOnClickListener(new View.OnClickListener() {
            /**
            * Signs the user up for the app if the sign up button is clicked
            * @param view stores the current view on the screen
            */
            @Override
            public void onClick(View view) {
                performSignUp();
                finish();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            /**
            * Cancels the user's sign up if the cancel button is clicked
            * @param view stores the current view on the screen
            */
            @Override
            public void onClick(View view) {
                Intent login = new Intent();
                setResult(Activity.RESULT_CANCELED, login);
                finish();
            }
        });


    }
    /**
     * Checking if the input in form is valid
     */
    private boolean validateInput() {
        // Check if the username is empty
        if (usernameView.getText().toString().equals("")) {
            usernameView.setError("Please Enter Username");
            return false;
        }

        // checks whether the password is empty
        if (PasswordView.getText().toString().equals("")) {
            PasswordView.setError("Please Enter Password");
            return false;
        }
        // checks whether the Confirm password is empty
        if (ConfirmPasswordView.getText().toString().equals("")) {
           ConfirmPasswordView.setError("Please Confirm Your Password");
            return false;
        }


        // checking minimum password Length
        if (PasswordView.getText().length() < MIN_PASSWORD_LENGTH) {
            PasswordView.setError("Password Length must be more than " + MIN_PASSWORD_LENGTH + "characters");
            return false;
        }

        // Checking if confirm password is same as password
        if (!PasswordView.getText().toString().equals(ConfirmPasswordView.getText().toString())) {
            ConfirmPasswordView.setError("Password does not match");
            return false;
        }
        return true;
    }

    /**
     * Signs up user and stores login information as a new user if input is valid 
     */
    public void performSignUp () {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String username = usernameView.getText().toString();
            String password = PasswordView.getText().toString();

            SharedPreferences myPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = myPref.edit();
            editor.putString(username, password);
            editor.commit();

            Intent login = new Intent();
            login.putExtra("new_user", username);
            setResult(Activity.RESULT_OK, login);
        }
    }
}