package com.myapp.pizzaorderingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.myapp.pizzaorderingapp.Adapters.ToppingAdapter;
import com.myapp.pizzaorderingapp.database.OrderContract;
import com.myapp.pizzaorderingapp.database.OrderDbHelper;
import com.myapp.pizzaorderingapp.database.OrderDetailContract;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView toppinglv;
    ArrayList<Topping> toppinglist;
    ArrayList<Boolean> selectedToppings;
    private SQLiteDatabase mDb;
    public int baseprice = 15;
    TextView total,choosetoppingtv,basepricetv;
    ToppingAdapter adapter;
    int theme;
    AlertDialog.Builder builder1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences preferences = getSharedPreferences("PIZZAAPP", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferences.getInt("theme", 0) != 0) {
            if (preferences.getInt("theme", 0) == R.style.AppTheme1) {

                setTheme(R.style.AppTheme1);
                builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Material_Dialog));

            } else {
                setTheme(R.style.AppTheme);
                builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Material_Light_Dialog));

            }
        } else {
            setTheme(R.style.AppTheme);
            builder1 = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Material_Light_Dialog));

        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toppinglist = new ArrayList<>();
        selectedToppings = new ArrayList<>();
        choosetoppingtv = findViewById(R.id.choosetoppingtv);
        basepricetv=findViewById(R.id.basepricetv);
        total = findViewById(R.id.total);
        total.setText("TOTAL: $" + baseprice);
        toppinglist.add(new Topping("Extra Cheese", 5));
        toppinglist.add(new Topping("Peppers", 4));
        toppinglist.add(new Topping("Onions", 3));
        toppinglist.add(new Topping("broccoli", 8));
        toppinglist.add(new Topping("Black olives", 4));
        toppinglist.add(new Topping("Gorgonzola", 9));
        toppinglist.add(new Topping("capsicum", 3));
        toppinglist.add(new Topping("Pepperoni", 6));
        toppinglist.add(new Topping("Extra cheese", 5));

        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);
        selectedToppings.add(false);


        toppinglv = findViewById(R.id.toppinglist);
        adapter = new ToppingAdapter(toppinglist, selectedToppings, this);
        toppinglv.setAdapter(adapter);

        OrderDbHelper dbHelper = new OrderDbHelper(this);

        // Keep a reference to the mDb until paused or killed. Get a writable database
        // because you will be adding restaurant customers
        mDb = dbHelper.getWritableDatabase();


    }

    @Override
    protected void onResume() {
        super.onResume();
        RunAnimation();
    }

    private void RunAnimation()
    {
        Animation a = AnimationUtils.loadAnimation(this, R.anim.scale);
        a.reset();
        choosetoppingtv.clearAnimation();
        choosetoppingtv.startAnimation(a);
        Animation a1 = AnimationUtils.loadAnimation(this, R.anim.move);
        a1.reset();
        basepricetv.clearAnimation();
        basepricetv.startAnimation(a1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_view_order) {
            startActivity(new Intent(getApplicationContext(), OrderHistory.class));
        } else if (id == R.id.change_theme) {
            finish();
            startActivity(new Intent(getApplicationContext(), ChangeTheme.class));
        }

        return super.onOptionsItemSelected(item);

    }

    private void PlaceOrder(int total) {
        ContentValues cv = new ContentValues();
        cv.put(OrderContract.OrderEntry.COLUMN_TOTAL, total);
        long orderid = mDb.insert(OrderContract.OrderEntry.TABLE_NAME, null, cv);

        for (int i = 0; i < selectedToppings.size(); i++) {
            ContentValues cv1 = new ContentValues();
            cv.put(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_NAME, toppinglist.get(i).getName());
            cv.put(OrderDetailContract.OrderDetailEntry.COLUMN_TOPPING_PRICE, toppinglist.get(i).getPrice());
            cv.put(OrderDetailContract.OrderDetailEntry.COLUMN_ORDER_ID, orderid);
            long orderdetailid = mDb.insert(OrderDetailContract.OrderDetailEntry.TABLE_NAME, null, cv);
        }


        builder1.setTitle("Confirmation");
        builder1.setMessage("Order Placed successfully?");

        builder1.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                refresh();
                dialog.dismiss();
            }
        });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public void updateBasePrice() {
        total.setText("TOTAL: $" + baseprice);
    }

    public void placeOrderLogic(View view) {
        PlaceOrder(baseprice);
    }

    public void refresh() {
        adapter.notifyDataSetChanged();
        baseprice = 15;
        total.setText("TOTAL: $" + baseprice);
    }
}
