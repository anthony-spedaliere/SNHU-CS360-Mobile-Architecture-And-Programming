package com.avspedaliere.spedaliereinventoryapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    Context context;
    // array lists to hold the data
    ArrayList inventoryName, quantity, inventory_id;

    public MyAdapter(Context context, ArrayList inventoryName, ArrayList quantity, ArrayList inventory_id) {
        this.context = context;
        this.inventoryName = inventoryName;
        this.quantity = quantity;
        this.inventory_id = inventory_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // this view will inflate the inventory row xml into java code
        View view = inflater.inflate(R.layout.inventory_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // get the data from the array
        holder.inventoryName.setText(String.valueOf(inventoryName.get(position)));
        holder.quantity.setText(String.valueOf(quantity.get(position)));
        holder.inventory_id.setText((String.valueOf(inventory_id.get(position))));

    }

    @Override
    public int getItemCount() {
        return inventoryName.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView inventoryName, quantity, inventory_id;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            inventoryName = itemView.findViewById(R.id.inventory_name);
            quantity = itemView.findViewById(R.id.quantity);
            inventory_id = itemView.findViewById(R.id.inventory_id);
        }
    }

}
