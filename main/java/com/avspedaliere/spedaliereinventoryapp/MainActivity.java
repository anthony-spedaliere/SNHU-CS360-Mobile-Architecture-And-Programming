package com.avspedaliere.spedaliereinventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int USER;
    EditText username, password;
    Button loginButton, newAccountButton;
    DBHelper DB;

    // method that is called when an activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the DBHelper variable
        DB = new DBHelper(MainActivity.this);

        USER = 0;

        // initialize EditText variables
        username = findViewById(R.id.editTextTextPersonName2);
        password = findViewById(R.id.editTextTextPassword);

        // request permission to use SMS
        ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, PackageManager.PERMISSION_GRANTED);

        // button variable pointing to the new account button on login page
        newAccountButton = findViewById(R.id.new_account_button);
        // button variable pointing to the login button on login page
        loginButton = findViewById(R.id.login_button);

        // on click method to handle changing from the login page activity to the inventory page
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();

                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(MainActivity.this, "Please enter in a valid username and password.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserPass = DB.checkUsernamePassword(user, pass);
                    if(checkUserPass == true) {
                        Toast.makeText(MainActivity.this, "Sign in successful.", Toast.LENGTH_SHORT).show();
                        // instantiate an intent variable
                        // set the global USER variable
                        USER = DB.getId(user);
                        System.out.println(USER);
                        Intent intent = new Intent(MainActivity.this, InventoryActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        // on click method to handle changing from the login page activity to the sign up activity
        newAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


}