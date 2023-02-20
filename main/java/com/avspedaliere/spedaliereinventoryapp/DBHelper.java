package com.avspedaliere.spedaliereinventoryapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // database name and version number
    public static final String DATABASE_NAME = "inventory_app.db";
    public static final int VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    // onCreate method to instantiate database tables
    @Override
    public void onCreate(SQLiteDatabase myDB) {
        // create the database tables
        myDB.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, firstname TEXT, lastname TEXT, username TEXT, password TEXT)");
        myDB.execSQL("CREATE TABLE inventory(inv_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity INT, user_id INTEGER, FOREIGN KEY(user_id) REFERENCES users(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop Table if exists users");
        myDB.execSQL("drop Table if exists inventory");
        onCreate(myDB);
    }

    // method to insert new users into the user table
    public Boolean insertNewUser(String firstname, String lastname, String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("firstname", firstname);
        contentValues.put("lastname", lastname);
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = myDB.insert("users", null, contentValues);

        if (result == -1) {
            myDB.close();
            return false;
        } else {
            myDB.close();
            return true;
        }
    }

    // method to insert new inventory items into the inventory table
    public Boolean insertNewInventoryItem(String name, int quantity, int user_id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("quantity", quantity);
        contentValues.put("user_id", user_id);

        long result = myDB.insert("inventory", null, contentValues);

        if (result == -1) {
            myDB.close();
            return false;
        } else {
            myDB.close();
            return true;
        }
    }

    // method to update the inventor table
    public Boolean updateInventoryItem(String id, String name, int quantity) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("quantity", quantity);

        long result = myDB.update("inventory", contentValues, "inv_id = ?", new String[] {id});
        if (result > 0) {
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }

    }

    // function to check username exists in database
    public Boolean checkUsername(String username) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ?", new String[] {username});
        if(cursor.getCount() > 0){
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }
    }

    // function to check if an inventory id exists in the inventory table
    public Boolean checkInventoryId(String id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from inventory where inv_id = ?", new String[] {id});
        if(cursor.getCount() > 0){
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }

    }

    // public method to check if an inventory name already exists for the user
    public Boolean checkInventoryNameExists(String name, String id) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from inventory where name = ? and user_id = ?", new String[] {name, id});
        if(cursor.getCount() > 0){
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }
    }

    // function to check if username and password exist in database
    public Boolean checkUsernamePassword(String username, String password) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from users where username = ? and password = ?", new String[] {username, password});

        if(cursor.getCount() > 0) {
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }
    }

    // function to get all the data from the inventory table
    public Cursor getData() {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from inventory", null);
        return cursor;
    }


    // function to get the id from the users table
    public int getId(String username) {
        ArrayList<String> columns = new ArrayList<>();
        columns.add("id");
        int result;

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("Select id from users where username = ?", new String[] {username});

        if(cursor.getCount() == 0) {
            result = 0;
            myDB.close();
            return result;
        } else {
            while(cursor.moveToNext()) {
                result = cursor.getInt(0);
                myDB.close();
                return result;
            }
        }
        myDB.close();
        return 0;
    }

    // function to delete a course form the inventory table
    public Boolean deleteCourse(String id) {

        SQLiteDatabase myDB = this.getWritableDatabase();
        int result = myDB.delete("inventory", "inv_id = ?", new String[] {id});

        if (result > 0) {
            myDB.close();
            return true;
        } else {
            myDB.close();
            return false;
        }
    }
}
