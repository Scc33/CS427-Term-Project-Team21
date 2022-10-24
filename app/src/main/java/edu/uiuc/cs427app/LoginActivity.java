package edu.uiuc.cs427app;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private AccountManager accountManager;

    // Values for username and password at the time of the login attempt
    private String username;
    private String password;

    // UI reference
    private EditText usernameView;
    private EditText passwordView;
    private Button login_button;
    private Button signup_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountManager = AccountManager.get(this);// initialize

        // link everything
        usernameView = (EditText) findViewById(R.id.username);
        passwordView = (EditText) findViewById(R.id.userpassword);
        login_button = (Button) findViewById(R.id.login_button);
        signup_button = (Button) findViewById(R.id.signup_button);


        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = usernameView.getText().toString();
                String password = passwordView.getText().toString();

                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)){
                    Toast.makeText(LoginActivity.this, "Please enter user name and password", Toast.LENGTH_SHORT).show();
                }
                else{
                    loginAttempt(userName, password);
                }
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = usernameView.getText().toString();
                String password = passwordView.getText().toString();
                //createAccount(userName, password);
                /*** Start signup activity**/
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
                finish();
            }
        });

    }


    private void loginAttempt(String userName, String password){
        //TODO implement login with account manager
        if(userName.equals("admin") && password.equals("admin")){
            //TODO change the if statement
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            Toast.makeText(this, "Incorrect user name or password", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(String username, String password){
        //TODO implement create account action
        // store the new user information into the account manager, and make a toast says sign up  successful
    }
}