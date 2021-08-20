package com.myapp.pizzaorderingapp.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.myapp.pizzaorderingapp.OrderHistory;
import com.myapp.pizzaorderingapp.R;
import com.myapp.pizzaorderingapp.database.OrderContract;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    // Holds on to the cursor to display the waitlist
    private Cursor mCursor;
    private Context mContext;
    OrderHistory obj;
    /**
     * Constructor using the context and the db cursor
     * @param context the calling context/activity
     * @param cursor the db cursor with waitlist data to display
     */
    public OrderAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
        obj= (OrderHistory) context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get the RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.ordersinglero, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        int total = mCursor.getInt(mCursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_TOTAL));
        String datetime = mCursor.getString(mCursor.getColumnIndex(OrderContract.OrderEntry.COLUMN_DATE_TIME));
        // COMPLETED (6) Retrieve the id from the cursor and
        final long id = mCursor.getLong(mCursor.getColumnIndex(OrderContract.OrderEntry._ID));

        // Display the guest name
        holder.total_tv.setText("Total: $"+total);
        // Display the party count
        holder.datetime_tv.setText(""+datetime);
        // COMPLETED (7) Set the tag of the itemview in the holder to the id

        holder.orderid_tv.setText("ORDER ID: "+id);

        holder.itemView.setTag(id);

        holder.delimv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj.delete(id);
            }
        });

        holder.toppings.setText(obj.getToppings(id));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    /**
     * Swaps the Cursor currently held in the adapter with a new one
     * and triggers a UI refresh
     *
     * @param newCursor the new cursor that will replace the existing one
     */
    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();

        }
    }


    public void setmCursor(Cursor mCursor) {
        this.mCursor = mCursor;
    }

    /**
     * Inner class to hold the views needed to display a single item in the recycler-view
     */
    class OrderViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView total_tv;
        // Will display the party size number
        TextView datetime_tv;

        TextView orderid_tv,toppings;

        ImageView delimv;


        public OrderViewHolder(View itemView) {
            super(itemView);
            total_tv = (TextView) itemView.findViewById(R.id.total_tv);
            datetime_tv = (TextView) itemView.findViewById(R.id.datetime_tv);
            orderid_tv = (TextView) itemView.findViewById(R.id.orderid_tv);
            toppings = (TextView) itemView.findViewById(R.id.toppings);
            delimv =itemView.findViewById(R.id.delimv);
        }

    }
}