package com.grocerylist;

/**
 * Created by Nicholas on 2/1/2016.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.grocerylist.ItemClickListener;
import com.grocerylist.ListFragment;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<ItemData> itemsData;
    private Context mContext;

    public MyAdapter(Context context, ArrayList<ItemData> itemsData) {
        this.itemsData = itemsData;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView, mContext);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData
        final ViewHolder holder = viewHolder;

        viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
        viewHolder.imgViewIcon.setImageResource(itemsData.get(position).getImageUrl());

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(mContext, "#" + position + " - " + itemsData.get(position) + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "#" + position + " - " + itemsData.get(position), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public void remove(int position) {
        itemsData.remove(position);
        notifyItemRemoved(position);
    }

    /*********************************************************************************************/
    /*                               INNER CLASS                                                 */
    /*********************************************************************************************/
    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public TextView txtViewTitle;
        public ImageView imgViewIcon;
        private ItemClickListener clickListener;
        Context mContext;

        public ViewHolder(View itemLayoutView, Context context) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            imgViewIcon = (ImageView) itemLayoutView.findViewById(R.id.item_icon);
            itemLayoutView.setTag(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            itemLayoutView.setOnLongClickListener(this);


            mContext=context;
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
            Intent editActivity = new Intent(mContext, EditActivity.class);
            editActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(editActivity);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            Intent deleteActivity = new Intent(mContext, DeleteActivity.class);
            deleteActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(deleteActivity);
            return true;
        }
    }
}
