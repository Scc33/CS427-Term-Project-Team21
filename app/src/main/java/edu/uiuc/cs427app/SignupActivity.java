package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

//    private String username;
//    private String password;

    // UI reference
    private EditText usernameView;
    private EditText PasswordView;
    private EditText ConfirmPasswordView;
    private EditText nicknameView;
    final int MIN_PASSWORD_LENGTH = 6;
    private Button signup_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        viewInitializations();
    }
    void viewInitializations() {
        usernameView = (EditText) findViewById(R.id.username);
        nicknameView = (EditText) findViewById(R.id.nickname);
        PasswordView = (EditText) findViewById(R.id.userpassword);
        ConfirmPasswordView = (EditText) findViewById(R.id.confirmuserpassword);

        // To show back button in actionbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    // Checking if the input in form is valid
    boolean validateInput() {
        // Check if the username is empty
        if (usernameView.getText().toString().equals("")) {
            usernameView.setError("Please Enter Username");
            return false;
        }
        // checks whether the nickname is empty
        if (nicknameView.getText().toString().equals("")) {
           nicknameView.setError("Please Enter Nickname");
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
    public void performSignUp (View v) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            String username = usernameView.getText().toString();
            String nickname = nicknameView.getText().toString();
            String password = PasswordView.getText().toString();
            String repeatPassword = ConfirmPasswordView.getText().toString();

            Toast.makeText(this,"Sign Up Success",Toast.LENGTH_SHORT).show();
            // call  API

        }
    }
}