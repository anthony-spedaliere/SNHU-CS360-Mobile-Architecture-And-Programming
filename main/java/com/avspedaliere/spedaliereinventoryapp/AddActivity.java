package com.avspedaliere.spedaliereinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    Button addNewItemBtn;
    EditText itemName, quantity;

    DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // instantiate button and edit text variables
        addNewItemBtn = findViewById(R.id.add_new_item_btn);
        itemName = findViewById(R.id.editTextTextItemName);
        quantity = findViewById(R.id.editTextTextQuantity);

        // instantiate database
        myDB = new DBHelper(AddActivity.this);

        // handle database submission when button clicked
        addNewItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the values of the inventory name and quantity entered by the user
                String name = itemName.getText().toString();
                String quantityOfItem = quantity.getText().toString();

                if(name.equals("") || quantityOfItem.equals("")) {
                    Toast.makeText(AddActivity.this, "You must enter values for all fields.", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean result = myDB.checkInventoryNameExists(name, Integer.toString(MainActivity.USER));
                    if(result) {
                        Toast.makeText(AddActivity.this, "This item already exists. Please use the update option to modify it.", Toast.LENGTH_SHORT).show();
                    } else {
                        // insert new inventory item into database
                        myDB.insertNewInventoryItem(name, Integer.parseInt(quantityOfItem), MainActivity.USER);
                        Toast.makeText(AddActivity.this, "Item successfully added.", Toast.LENGTH_SHORT).show();
                        // send user back to inventory page after adding item to the database
                        Intent intent = new Intent(AddActivity.this, InventoryActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

    }
}