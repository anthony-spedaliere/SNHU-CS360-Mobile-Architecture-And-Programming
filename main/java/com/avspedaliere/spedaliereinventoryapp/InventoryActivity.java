package com.avspedaliere.spedaliereinventoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class InventoryActivity extends AppCompatActivity {

    RecyclerView inventoryRecyclerView;
    ArrayList<String> inventoryName;
    ArrayList<Integer> quantity, inventory_id;
    DBHelper DB;
    MyAdapter adapter;
    // floating action button variable for the add/delete buttons
    FloatingActionButton addBtn, deleteBtn;
    // button to handle updating the recycler view values
    Button updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        // initialize database
        DB = new DBHelper(InventoryActivity.this);
        // initialize array lists to hold data
        inventoryName = new ArrayList<>();
        quantity = new ArrayList<>();
        inventory_id = new ArrayList<>();
        // initialize recycler to handle displaying data
        inventoryRecyclerView = findViewById(R.id.inventoryRecyclerView);
        // initialize adapter to handle transferring data to recycler view
        adapter = new MyAdapter(InventoryActivity.this, inventoryName, quantity, inventory_id);
        // set recycler views adapter
        inventoryRecyclerView.setAdapter(adapter);
        // set recycler view layout manager
        inventoryRecyclerView.setLayoutManager(new LinearLayoutManager(InventoryActivity.this));
        displayData();

        // instantiate floating action button for adding new item function
        addBtn = findViewById(R.id.floatingActionButtonAdd);
        // on click listener to handle activity change
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        // instantiate floating action button for deleting an item from recycler view
        deleteBtn = findViewById(R.id.floatingActionButtonDelete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, DeleteActivity.class);
                startActivity(intent);
            }
        });

        // instantiate button for updating an item from recycler view
        updateBtn = findViewById(R.id.update_button);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InventoryActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });

    }

    // function used to display the data in the recycler view
    private void displayData() {
        Cursor cursor = DB.getData();
        if(cursor.getCount() == 0) {

            return;
        } else {
            while(cursor.moveToNext()) {
                // only return items belonging to the logged in user by checking the user_id column against the global USER variable
                if(cursor.getInt(3) == MainActivity.USER) {
                    inventory_id.add(cursor.getInt(0));
                    inventoryName.add(cursor.getString(1));
                    quantity.add(cursor.getInt(2));
                }
            }

        }
    }

}
