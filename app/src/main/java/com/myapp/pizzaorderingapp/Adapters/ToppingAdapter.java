package com.myapp.pizzaorderingapp.Adapters;


import android.content.Context;
import android.view.View;

import com.myapp.pizzaorderingapp.MainActivity;
import com.myapp.pizzaorderingapp.R;
import com.myapp.pizzaorderingapp.Topping;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import java.util.ArrayList;

public class ToppingAdapter extends BaseAdapter {

    ArrayList<Topping> toppinglist;
    ArrayList<Boolean> selectedToppings;


    Context context;
    MainActivity obj;

    public ToppingAdapter(ArrayList<Topping> toppinglist,ArrayList<Boolean> selectedToppings, Context context) {

        this.toppinglist=toppinglist;
        this.context=context;
        this.selectedToppings=selectedToppings;
        obj= (MainActivity) context;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return toppinglist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        convertView = inflater.inflate(R.layout.toppingview, parent, false);
        TextView toppingname,toppingprice;
        CheckBox cbtoppings;

        toppingname = (TextView) convertView.findViewById(R.id.toppingname);
        toppingprice = (TextView) convertView.findViewById(R.id.toppingprice);

        cbtoppings = (CheckBox) convertView.findViewById(R.id.cbtoppings);


        cbtoppings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    selectedToppings.set(position,true);
                    obj.baseprice+=toppinglist.get(position).getPrice();

                }
                else
                {
                    selectedToppings.set(position,false);
                    obj.baseprice-=toppinglist.get(position).getPrice();

                }
                obj.updateBasePrice();


            }
        });

        toppingname.setText(toppinglist.get(position).getName());
        toppingprice.setText("$"+toppinglist.get(position).getPrice());

        return (convertView);
    }
}