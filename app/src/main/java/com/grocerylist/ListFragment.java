package com.grocerylist;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    public ListFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 8;

    private ListView listView;

    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected RecyclerView mRecyclerView;
    protected MyAdapter mAdapter;
    protected GroceryListTouchHelper groceryListTouchHelper;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected ArrayList<ItemData> mDataset;
    protected ItemData itemData;
    protected Context mContext;

    private int userID;
    private int itemID = 0;

    private ArrayList<ItemData> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list_fragment_view, container, false);

        if(savedInstanceState == null) {
            Bundle extras = getActivity().getIntent().getExtras();

            if(extras == null) {
                userID = 2;
            } else {
                userID = extras.getInt("UserID");
            }
        }
        Log.d("UserID after Pass", userID + "");
        listView = (ListView) rootView.findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("ItemID", itemID);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                Intent intent = new Intent(getContext(), DeleteActivity.class);
                intent.putExtra("ItemID", itemID);
                startActivity(intent);
                // Return true to consume the click event. In this case the
                // onListItemClick listener is not called anymore.
                return true;
            }
        });
/*        rootView.setTag(TAG);

        // BEGIN_INCLUDE(initializeRecyclerView)
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        // LinearLayoutManager is used here, this will layout the elements in a similar fashion
        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
        // elements are laid out.
        mLayoutManager = new LinearLayoutManager(getActivity());

        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }
        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        mContext = getContext();

        mAdapter = new MyAdapter(mContext, mDataset);
        // Set MainActivityAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);
        // END_INCLUDE(initializeRecyclerView)

        ItemTouchHelper.Callback callback = new GroceryListTouchHelper(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecyclerView);*/

        updateListView();

        return rootView;
    }

    public void updateListView() {
        JSONArray ja = this.getItems();

        List<String> itemsArray = new ArrayList<String>();

        for (int i = 0; i < ja.length(); i++) {
            try {
                JSONObject jo = ja.getJSONObject(i);

                ItemData item = new ItemData();
                item.setItemID(jo.optInt("ItemID"));
                item.setItemName(jo.optString("ItemName"));
                item.setItemUnitType(jo.optString("ItemUnitType"));
                item.setItemDescription(jo.optString("ItemDescription"));
                item.setItemPrice(jo.optDouble("ItemPrice"));
                item.setItemCount(jo.optInt("ItemCount"));
                item.setItemCategory(jo.optString("ItemCategory"));

                Log.d("JSONObject:", jo.toString());
                itemsArray.add(item.toString());
                itemID = item.getItemID();
                Log.d("itemID:", itemID+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                itemsArray);
        Log.d("Array Items:", itemsArray.get(0).toString());
        Log.d("Activity", getActivity().toString());

        listView.setAdapter(arrayAdapter);
    }

    public JSONArray getItems() {
        //http://stackoverflow.com/questions/22395417/error-strictmodeandroidblockguardpolicy-onnetwork
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        String urlAddress = "http://groceryapp-russjw.rhcloud.com/GroceryAppFranklin/rest/items/";
        String urlAddress = "http://w16groc.franklinpracticum.com/select_script.php?UserID=" + userID;

        InputStream inputStream = null;

        JSONObject jo = null;
        JSONArray ja = null;

        try {
            URL url = new URL(urlAddress);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // receive response as inputStream
            inputStream = new BufferedInputStream(urlConnection.getInputStream());

            Log.v(TAG, "rest url:" + url);

            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;

            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            Log.v(TAG, "rest data" + total.toString());

            ja = new JSONArray(total.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ja;
    }

    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }

        switch (layoutManagerType) {
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getActivity());
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
        }

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


}
