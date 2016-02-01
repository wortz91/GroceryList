package com.grocerylist;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Nicholas on 2/1/2016.
 */
public class GroceryListTouchHelper extends ItemTouchHelper.SimpleCallback {
    private MyAdapter mGroceryListAdapter;

    public GroceryListTouchHelper(MyAdapter groceryAdapter) {
        super(ItemTouchHelper.UP| ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.mGroceryListAdapter = groceryAdapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        //TODO: Not implemented here
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Remove item
        mGroceryListAdapter.remove(viewHolder.getAdapterPosition());
    }

}
