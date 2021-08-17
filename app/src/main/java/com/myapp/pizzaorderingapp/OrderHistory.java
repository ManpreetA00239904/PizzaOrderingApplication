package com.myapp.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.myapp.pizzaorderingapp.Adapters.OrderAdapter;
import com.myapp.pizzaorderingapp.database.OrderContract;
import com.myapp.pizzaorderingapp.database.OrderDbHelper;
import com.myapp.pizzaorderingapp.database.OrderDetailContract;

public class OrderHistory extends AppCompatActivity {

    private OrderAdapter mAdapter;
    private SQLiteDatabase mDb;

    TextView headingtv;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        headingtv = findViewById(R.id.headingtv);
        RecyclerView waitlistRecyclerView;


        waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_orders_list_view);
        waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        OrderDbHelper dbHelper = new OrderDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        cursor = getAllOrders();
        headingtv.setText("Your Orders (" + cursor.getCount() + ")");
        mAdapter = new OrderAdapter(this, cursor);
        waitlistRecyclerView.setAdapter(mAdapter);


    }


    public void showorders() {
        cursor = getAllOrders();
        mAdapter.setmCursor(cursor);
        mAdapter.notifyDataSetChanged();
        headingtv.setText("Your Orders (" + cursor.getCount() + ")");
    }

    public void delete(final long orderid) {
        final AlertDialog.Builder builder1 = new AlertDialog.Builder(OrderHistory.this);
        builder1.setTitle("Confirmation");
        builder1.setMessage("Are you sure you want to delete this order?");

        builder1.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean option = deleteOrder(orderid);


                showorders();

                dialog.dismiss();
            }
        });

        builder1.setNegativeButton(android.R.string.no, null);

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private Cursor getAllOrders() {
        return mDb.query(
                OrderContract.OrderEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public String getToppings(long orderid) {
        Cursor cursor = mDb.query(
                OrderDetailContract.OrderDetailEntry.TABLE_NAME,
                null,
                OrderDetailContract.OrderDetailEntry.COLUMN_ORDER_ID + "=" + orderid,
                null,
                null,
                null,
                null
        );

        String top = "";
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
            // Update the view holder with the information needed to display
            int price = cursor.getInt(cursor.getColumnIndex(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_PRICE));
            String name = cursor.getString(cursor.getColumnIndex(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_NAME));
            top += name + "($" + price + "), ";
            }
        }
        cursor.close();

        return top;
    }


    private boolean deleteOrder(long id) {
        return mDb.delete(OrderContract.OrderEntry.TABLE_NAME, OrderContract.OrderEntry._ID + "=" + id, null) > 0;
    }

}