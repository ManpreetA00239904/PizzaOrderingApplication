package com.myapp.pizzaorderingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.myapp.pizzaorderingapp.Adapters.ToppingAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ListView toppinglv;
    ArrayList<Topping> toppinglist;
    ArrayList<Boolean> selectedToppings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toppinglist=new ArrayList<>();
        selectedToppings=new ArrayList<>();
        toppinglist.add(new Topping("Extra Cheese",5));
        toppinglist.add(new Topping("Peppers",4));
        toppinglist.add(new Topping("Onions",3));



        toppinglv = findViewById(R.id.toppinglist);
        ToppingAdapter adapter = new ToppingAdapter(toppinglist, selectedToppings,getApplicationContext());
        toppinglv.setAdapter(adapter);

    }
}
