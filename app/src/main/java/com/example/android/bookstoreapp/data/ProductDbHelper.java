package com.example.android.bookstoreapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.bookstoreapp.data.ProductContract.ProductEntry;

public class ProductDbHelper extends SQLiteOpenHelper {
    // database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "products.db";

    public ProductDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        Log.v("table","table created");
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + ProductEntry.TABLE_NAME + " ("
                + ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ProductEntry.COLUMN_PRODUCT_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_QUANTITY + " INTEGER DEFAULT 0, "
                + ProductEntry.COLUMN_PRODUCT_PRICE + " REAL NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_SUPPLIER_NAME + " TEXT NOT NULL, "
                + ProductEntry.COLUMN_PRODUCT_SUPPLIER_PHONE + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }




}
