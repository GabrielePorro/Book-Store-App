package com.example.android.bookstoreapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.ProductContract.ProductEntry;
import com.example.android.bookstoreapp.data.ProductProvider;


public class ProductCursorAdapter extends CursorAdapter {
    // Tag for the log messages
    public static final String LOG_TAG = "adapter";
    public ProductCursorAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }
       @Override
    public void bindView(View view, final Context context, final Cursor cursor) {

        Button button = view.findViewById(R.id.button_sub);
        final int position = cursor.getPosition();
        final TextView quantityTextView =  view.findViewById(R.id.quantity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v(LOG_TAG,"called"+position);
                int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
                int quantity = cursor.getInt(quantityColumnIndex);

               // ((ListView) parent).performItemClick(v, position, 0); // Let the event be handled in onItemClick()
                if (quantity > 0) {
                    quantity--;
                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, String.valueOf(quantity));
                    int idColumnIndex = cursor.getColumnIndex(ProductEntry._ID);
                    String id = cursor.getString(idColumnIndex);
                    Uri currentProductUri = cursor.getNotificationUri();
                    String selection = ProductEntry._ID + "=?";
                    String[] selectionArgs = new String[] { id };

                    int newUpdate = context.getContentResolver().update(currentProductUri, values, selection, selectionArgs);

                    quantityTextView.setText(String.valueOf(quantity));
                }
                else {

                    Toast.makeText(context.getApplicationContext(), context.getString(R.string.editor_negative_quantity),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });



        // Find individual views that we want to modify in the list item layout
        TextView nameTextView =  view.findViewById(R.id.name);
        TextView priceTextView =  view.findViewById(R.id.price);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        int quantityColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_QUANTITY);
        // Read the pet attributes from the Cursor for the current product
        String name = cursor.getString(nameColumnIndex);
        Double price = cursor.getDouble(priceColumnIndex);
        int quantity = cursor.getInt(quantityColumnIndex);

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(name);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(String.valueOf(quantity));
    }
}