package com.avspedaliere.spedaliereinventoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText id, item, quantity;
    Button updateBtn;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // instantiate the database
        myDB = new DBHelper(UpdateActivity.this);

        // instantiate edit text variables
        id = findViewById(R.id.editTextId_update);
        item = findViewById(R.id.editTextTextItemName_update);
        quantity = findViewById(R.id.editTextTextQuantity_update);

        // instantiate button
        updateBtn = findViewById(R.id.update_btn_update_activity);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inventoryId = id.getText().toString().trim();
                Boolean result = myDB.checkInventoryId(inventoryId);
                System.out.println(result);

                String inv_id = id.getText().toString().trim();
                String inv_item = item.getText().toString().trim();
                String inv_quantity = quantity.getText().toString().trim();

                // logic to check if all fields are filled out and to update the inventory item
                if (inv_id.equals("") || inv_item.equals("") || inv_quantity.equals("")) {
                    Toast.makeText(UpdateActivity.this, "All text fields must be filled out.", Toast.LENGTH_SHORT).show();
                } else {
                    if (result) {
                        Boolean updateCheck = myDB.updateInventoryItem(inv_id, inv_item, Integer.parseInt(inv_quantity));
                        if(updateCheck) {
                            if(Integer.parseInt(inv_quantity) == 0) {
                                String message = "Inventory is showing a quantity of 0 for item " + inv_item;
                                SmsManager mySmsManager = SmsManager.getDefault();
                                mySmsManager.sendTextMessage("15555215554", null, message, null, null);
                            }
                            Toast.makeText(UpdateActivity.this, "Inventory successfully updated.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateActivity.this, InventoryActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(UpdateActivity.this, "Update unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(UpdateActivity.this, "Inventory ID does not exist.", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}