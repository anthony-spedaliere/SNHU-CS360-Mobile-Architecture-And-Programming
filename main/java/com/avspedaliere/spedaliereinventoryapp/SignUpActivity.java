package com.avspedaliere.spedaliereinventoryapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {
    // database
    DBHelper DB;
    // button
    Button submitBtn;
    // EditText variables
    EditText firstname, lastname, username, password, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // instantiate database
        DB = new DBHelper(SignUpActivity.this);
        // get EditText's
        firstname = findViewById(R.id.editTextFirstName);
        lastname = findViewById(R.id.editTextLastName);
        username = findViewById(R.id.editTextTextUsername);
        password = findViewById(R.id.editTextPassword);
        confirmPassword = findViewById((R.id.editTextTextConfirmPassword));
        // instantiate button
        submitBtn = findViewById(R.id.submit_button);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String first = firstname.getText().toString().trim();
                String last = lastname.getText().toString().trim();
                String user = username.getText().toString().trim();
                String pass = password.getText().toString().trim();
                String confirmPass = confirmPassword.getText().toString().trim();

                if (first.equals("") || last.equals("") || user.equals("") || pass.equals("")) {
                    Toast.makeText(SignUpActivity.this, "Please enter all the fields to sign up.", Toast.LENGTH_SHORT).show();
                } else {
                    if (pass.equals(confirmPass)) {
                        Boolean checkuser = DB.checkUsername(user);
                        if (checkuser == false) {
                            Boolean insert = DB.insertNewUser(first, last, user, pass);
                            if (insert == true) {
                                Toast.makeText(SignUpActivity.this, "Registered successfully.", Toast.LENGTH_SHORT).show();
                                // move back to sign in page
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SignUpActivity.this, "Registration failed.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(SignUpActivity.this, "Username already exists. Please choose another username or log in.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignUpActivity.this, "Passwords not matching.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}
