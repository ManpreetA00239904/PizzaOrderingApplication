package com.myapp.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.myapp.pizzaorderingapp.Adapters.ToppingAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView toppinglv;
    ArrayList<String> toppinglist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toppinglist=new ArrayList<>();
        toppinglist.add("Extra Cheese");
        toppinglist.add("Peppers");
        toppinglist.add("Onions");



        toppinglv = findViewById(R.id.toppinglist);
        ToppingAdapter adapter = new ToppingAdapter(toppinglist, getApplicationContext());
        toppinglv.setAdapter(adapter);

    }
}
