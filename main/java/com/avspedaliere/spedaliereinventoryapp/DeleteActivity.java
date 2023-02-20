package com.avspedaliere.spedaliereinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    // declare variables
    EditText id;
    Button deleteBtn;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        // instantiate variables
        id = findViewById(R.id.editTextId_delete);
        deleteBtn = findViewById(R.id.button_delete);
        myDB = new DBHelper(DeleteActivity.this);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inventoryId = id.getText().toString();
                Boolean result = myDB.checkInventoryId(inventoryId);

                if(result) {
                    Boolean deleteResult = myDB.deleteCourse(inventoryId);
                    if(deleteResult) {
                        Toast.makeText(DeleteActivity.this, "Successfully deleted inventory item.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DeleteActivity.this, InventoryActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(DeleteActivity.this, "Deletion failed.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DeleteActivity.this, "The inventory ID you selected does not exist.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}