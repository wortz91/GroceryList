package com.grocerylist;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class MainActivity extends AppCompatActivity {

    //List Variables
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // RecyclerView Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 3. create an adapter
        MyAdapter mAdapter = new MyAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.Callback callback = new GroceryListTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        //FloatingActionButton Setup
        FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab);


        FloatingActionButton fab1 = new FloatingActionButton(this);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action 1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        FloatingActionButton fab2 = new FloatingActionButton(this);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action 2", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        fabMenu.addButton(fab1);
        fabMenu.addButton(fab2);

//        //Expandable List Setup
//        // ListView Group click listener
//        expListView.setOnGroupClickListener(new OnGroupClickListener() {
//
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v,
//                                        int groupPosition, long id) {
//                // Toast.makeText(getApplicationContext(),
//                // "Group Clicked " + listDataHeader.get(groupPosition),
//                // Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
//
//        // ListView Group expanded listener
//        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Expanded",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // ListView Group collapsed listener
//        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        listDataHeader.get(groupPosition) + " Collapsed",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        // Listview on child click listener
//        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                // TODO Auto-generated method stub
//                Toast.makeText(
//                        getApplicationContext(),
//                        listDataHeader.get(groupPosition)
//                                + " : "
//                                + listDataChild.get(
//                                listDataHeader.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT)
//                        .show();
//                return false;
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    /*
     * Preparing the list data
     *
     * This will connect to the database and grab the JSON code for the Grocery Categories
     * and the Grocery Items
     */
        private void prepareListData() {
            listDataHeader = new ArrayList<String>();
            listDataChild = new HashMap<String, List<String>>();

            // Adding child data
            // this is the header titles for database values in the categories table
            listDataHeader.add("Produce");
            listDataHeader.add("Meat");
            listDataHeader.add("Dry Goods");
            listDataHeader.add("Dairy");
            listDataHeader.add("Frozen");

            // Adding child data
            // this is the individual items for the categories
            // the data is pre-populated right now will be dynamic soon
            List<String> produce = new ArrayList<String>();
            produce.add("The Shawshank Redemption");
            produce.add("The Godfather");
            produce.add("The Godfather: Part II");
            produce.add("Pulp Fiction");
            produce.add("The Good, the Bad and the Ugly");
            produce.add("The Dark Knight");
            produce.add("12 Angry Men");

            List<String> meat = new ArrayList<String>();
            meat.add("The Conjuring");
            meat.add("Despicable Me 2");
            meat.add("Turbo");
            meat.add("Grown Ups 2");
            meat.add("Red 2");
            meat.add("The Wolverine");

            List<String> dry_goods = new ArrayList<String>();
            dry_goods.add("2 Guns");
            dry_goods.add("The Smurfs 2");
            dry_goods.add("The Spectacular Now");
            dry_goods.add("The Canyons");
            dry_goods.add("Europa Report");

            List<String> dairy = new ArrayList<String>();
            dairy.add("2 Guns");
            dairy.add("The Smurfs 2");
            dairy.add("The Spectacular Now");
            dairy.add("The Canyons");
            dairy.add("Europa Report");

            List<String> frozen = new ArrayList<String>();
            frozen.add("2 Guns");
            frozen.add("The Smurfs 2");
            frozen.add("The Spectacular Now");
            frozen.add("The Canyons");
            frozen.add("Europa Report");

            listDataChild.put(listDataHeader.get(0), produce); // Header, Child data, add with the put command
            listDataChild.put(listDataHeader.get(1), meat);
            listDataChild.put(listDataHeader.get(2), dry_goods);
            listDataChild.put(listDataHeader.get(3), dairy);
            listDataChild.put(listDataHeader.get(4), frozen);
        }
}
