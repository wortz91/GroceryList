package com.grocerylist;

import android.view.View;

/**
 * Created by Nicholas on 2/4/2016.
 */
public interface ItemClickListener {
    void onClick(View view, int position, boolean isLongClick);
}
