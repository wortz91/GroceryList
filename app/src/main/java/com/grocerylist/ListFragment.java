package com.grocerylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {
    public ListFragment() {
        // Required empty public constructor
    }

    // trwar

    private static final String TAG = "RecyclerViewFragment";
    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    private static final int SPAN_COUNT = 2;
    private static final int DATASET_COUNT = 8;

    private ListView listView;
    ArrayAdapter<String> arrayAdapter;
    SwipeDetector swipeDetector = new SwipeDetector();
    public List<String> itemsArray;
    public List<String> copiedList = new ArrayList<>();


    private enum LayoutManagerType {
        LINEAR_LAYOUT_MANAGER
    }

    protected LayoutManagerType mCurrentLayoutManagerType;

    protected Context mContext;

    private int userID;
    private int itemID = 0;

    boolean swiped = false;

//    Bundle bundle;
//    Fragment fragment;


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
        listView.setOnTouchListener(swipeDetector);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(swipeDetector.swipeDetected()){
                    Log.d("Detected a swipe: ", "onItemClick");
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Log.d("OnClickItem was Swiped", "Oh YEAH!");

                        List<String> passedList = swipeDetectedAction(itemsArray, position);
                        Log.d("passedList:", passedList.get(position));
                        Bundle args = new Bundle();
                        args.putString("PassedList", passedList.get(position));
                        args.putBoolean("Swiped", swiped);

                        Fragment frag = new PayFragment();
                        frag.setArguments(args);
                    }
                } else {
                    Intent intent = new Intent(getContext(), EditActivity.class);
                    intent.putExtra("ItemID", itemID);
                    intent.putExtra("UserID", userID);
                    startActivity(intent);
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                if(swipeDetector.swipeDetected()){
                    Log.d("Detected a swipe: ", "onItemLongClick");
                    if(swipeDetector.getAction() == SwipeDetector.Action.LR) {
                        Log.d("LongClickItem Swiped", "Oh YEAH!");
                    }
                } else {
                    Intent intent = new Intent(getContext(), DeleteActivity.class);
                    intent.putExtra("ItemID", itemID);
                    intent.putExtra("UserID", userID);
                    startActivity(intent);
                    // Return true to consume the click event. In this case the
                    // onListItemClick listener is not called anymore.
                    return true;
                }
                return false;
            }
        });

        updateListView();

        return rootView;
    }

    public void updateListView() {
        JSONArray ja = this.getItems();

        itemsArray = new ArrayList<String>();

        for (int i = 0; i < ja.length(); i++) {
            try {
                JSONObject jo = ja.getJSONObject(i);

//                // setup passing the ItemName to the PayFragment
//                bundle = new Bundle();
//                fragment = new PayFragment();

                ItemData item = new ItemData();
                item.setItemID(jo.optInt("ItemID"));
                item.setItemName(jo.optString("ItemName"));
                item.setItemUnitType(jo.optString("ItemUnitType"));
                item.setItemDescription(jo.optString("ItemDescription"));
                item.setItemPrice(jo.optDouble("ItemPrice"));
                item.setItemCount(jo.optInt("ItemCount"));
                item.setItemCategory(jo.optString("ItemCategory"));

                Log.d("JSONObject:", jo.toString());
                itemsArray.add(item.toItemName());
                itemID = item.getItemID();
//
//                // passing ItemName to the PayFragment
//                String itemNameStr = item.toString();
//                bundle.putString("ItemName", itemNameStr);
//
//                fragment.setArguments(bundle);

                Log.d("itemID:", itemID+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        arrayAdapter = new ArrayAdapter<String>(
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

    public List<String> swipeDetectedAction(List<String> itemsArray, int position) {
        Log.d("Position(262):", position + "");
        Log.d("itemsArray value:", itemsArray.get(position));

        String itemsArrayAdd = itemsArray.get(position);
        Log.d("itemsArrayAdd", itemsArrayAdd);

        copiedList.add(itemsArray.get(position));
        Log.d("copiedList:", copiedList.get(0));

        itemsArray.remove(position);

        return copiedList;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save currently selected layout manager.
        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
        super.onSaveInstanceState(savedInstanceState);
    }


}
