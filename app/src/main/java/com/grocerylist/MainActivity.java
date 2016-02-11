package com.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;


public class MainActivity extends AppCompatActivity {

    // List Variables
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    // RecyclerView Variables
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TabLayout Variables
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TabView
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        // FloatingActionButton Setup
        FloatingActionsMenu fabMenu = (FloatingActionsMenu) findViewById(R.id.fab);

        FloatingActionButton fab1 = new FloatingActionButton(this);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action 1", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent addToCart = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(addToCart);
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
        if (id==R.id.overflowItem1) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*********************************************************************************************/
    /*                                  ViewPager                                                */
    /*********************************************************************************************/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ListFragment(), "List");
        adapter.addFragment(new CartFragment(), "Cart");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
