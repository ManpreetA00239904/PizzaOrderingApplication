package com.myapp.pizzaorderingapp.Adapters;


import android.content.Context;
import android.view.View;
import com.myapp.pizzaorderingapp.R;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ToppingAdapter extends BaseAdapter {

    ArrayList<String> toppinglist;


    Context context;

    public ToppingAdapter(ArrayList<String> toppinglist, Context context) {

        this.toppinglist=toppinglist;
        this.context=context;
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


    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View row;
        convertView = inflater.inflate(R.layout.toppingview, parent, false);
        TextView toppingname;

        toppingname = (TextView) convertView.findViewById(R.id.toppingname);

        toppingname.setText(toppinglist.get(position));
        return (convertView);
    }
}