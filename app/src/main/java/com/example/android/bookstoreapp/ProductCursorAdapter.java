package com.example.android.bookstoreapp;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookstoreapp.data.ProductContract.ProductEntry;


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
        TextView quantityTextView = view.findViewById(R.id.quantity);

        // Find individual views that we want to modify in the list item layout
        TextView nameTextView = view.findViewById(R.id.name);
        TextView priceTextView = view.findViewById(R.id.price);

        int nameColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_NAME);
        int priceColumnIndex = cursor.getColumnIndex(ProductEntry.COLUMN_PRODUCT_PRICE);
        final int quantityColumnIndex = cursor.getInt(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY));
        // Read the attributes from the Cursor for the current product
        String name = cursor.getString(nameColumnIndex);
        Double price = cursor.getDouble(priceColumnIndex);

        // Update the TextViews with the attributes for the current product
        nameTextView.setText(name);
        priceTextView.setText(String.valueOf(price));
        quantityTextView.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry.COLUMN_PRODUCT_QUANTITY)));

        String currentId = cursor.getString(cursor.getColumnIndexOrThrow(ProductEntry._ID));
        final Uri currentProductUri = ContentUris.withAppendedId(ProductEntry.CONTENT_URI, Long.parseLong(currentId));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (quantityColumnIndex > 0) {

                    ContentValues values = new ContentValues();
                    values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, String.valueOf(quantityColumnIndex - 1));

                    int newUpdate = context.getContentResolver().update(currentProductUri, values, null, null);

                } else {

                    Toast.makeText(context.getApplicationContext(), context.getString(R.string.editor_negative_quantity),
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}