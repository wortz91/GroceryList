package com.grocerylist;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.grocerylist.GroceryListTouchHelper;
import com.grocerylist.ItemData;
import com.grocerylist.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListFragment extends Fragment {

    //List Variables
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // RecyclerView Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentActivity fragmentActivity = (FragmentActivity) super.getActivity();

        View drawer = inflater.inflate(R.layout.fragment_list, container, false);

        RecyclerView recyclerView = (RecyclerView) drawer.findViewById(R.id.recycler_view);

        // this is data from recycler view
        // JSON Data Eventually
        ArrayList<ItemData> itemsData = new ArrayList();
        itemsData.add(new ItemData("Produce"));
        itemsData.add(new ItemData("Meat"));
        itemsData.add(new ItemData("Dairy"));
        itemsData.add(new ItemData("Dry Stock"));
        itemsData.add(new ItemData("Frozen"));
        itemsData.add(new ItemData("Alcohol"));
        itemsData.add(new ItemData("Misc"));

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(super.getActivity()));
        // 3. create an adapter
        MyAdapter mAdapter = new MyAdapter(super.getActivity().getApplicationContext(), itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.Callback callback = new GroceryListTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        recyclerView.findViewById(R.id.recycler_view);

        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    /*********************************************************************************************/
    /*                                  Bonus Methods                                            */
    /*********************************************************************************************/
    /*
     *  Create the populated ArrayList of data objects
     */
    private ArrayList<ItemData> getDataSet() {
        ArrayList results = new ArrayList<ItemData>();
        for (int index = 0; index < 20; index++) {
            ItemData obj = new ItemData("Some Primary Text " + index);
            results.add(index, obj);
        }
        return results;
    }
}
