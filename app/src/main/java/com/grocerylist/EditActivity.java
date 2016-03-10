package com.grocerylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://w16groc.franklinpracticum.com/";


    //private Button bSubmit;
    private Spinner sCategory, sUnitType;
    private EditText etName, etAmount, etDescription, etPrice, etId;
    private int intNum = 2; //Temporary hardcode!!!!!!!!!!!!!

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = (EditText) findViewById(R.id.name_content);
        etAmount = (EditText) findViewById(R.id.amount_content);
        etDescription = (EditText) findViewById(R.id.description_content);
        etPrice = (EditText) findViewById(R.id.price_content);
        sCategory = (Spinner) findViewById(R.id.category_spinner);
        sUnitType = (Spinner) findViewById(R.id.unit_spinner);


        Intent intent = getIntent();
        //intNum = intent.getIntExtra("itemId", -99); //Turn back on!!!!

        //etId.setText(intNum + "");

        getItem();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getItem() {
        class GetItem extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditActivity.this, "Fetching...", "Wait...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                showItem(s);
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequestParam(SERVER_ADDRESS +
                                "selectItem_script_v2.php?ItemID=", intNum + "");
                return s;
            }
        }
        GetItem getItem = new GetItem();
        getItem.execute();
    }

    private void showItem(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray result = jsonObject.getJSONArray("item");
            JSONObject c = result.getJSONObject(0);
            String itemName = c.getString("ItemName");
            String itemUnitType = c.getString("ItemUnitType");
            String description = c.getString("ItemDescription");
            double itemPrice = c.getDouble("ItemPrice");
            int itemCount = c.getInt("ItemCount");
            String itemCategory = c.getString("ItemCategory");

            etName.setText(itemName);

            //Hard coding for test...
            //etName.setText("Test Value!!!");

            //get index of spinner value
            int index = getIndex(sUnitType, itemUnitType);

            sUnitType.setSelection(index);
            etDescription.setText(description);
            etPrice.setText(itemPrice + "");
            etAmount.setText(itemCount + "");

            //get index of spinner value
            index = getIndex(sCategory, itemCategory);

            sCategory.setSelection(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = 1;
                break;
            }
        }
        return index;
    }

    private void updateItem() {
        final String name = etName.getText().toString();
        final int amount = Integer.parseInt(etAmount.getText().toString());
        final String description = etDescription.getText().toString();
        final double price = Double.parseDouble(etPrice.getText().toString());
        final String category = sCategory.getSelectedItem().toString();
        final String unitType = sUnitType.getSelectedItem().toString();

        class UpdateItem extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(EditActivity.this, "Updating...", "Wait...",
                        false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(EditActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("ItemID", intNum + "");
                hashMap.put("ItemName", name);
                hashMap.put("ItemCount", amount + "");
                hashMap.put("ItemDescription", description);
                hashMap.put("ItemPrice", price + "");
                hashMap.put("ItemCategory", category);
                hashMap.put("ItemUnitType", unitType);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(SERVER_ADDRESS + "edit_script.php", hashMap);

                return s;
            }
        }
        UpdateItem ui = new UpdateItem();
        ui.execute();
    }



    /*@Override
    protected void onStart() {
        super.onStart();
        displayItemDetails();
    }*/

    public void onClickUpdateItem(View view) {
        updateItem();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /*public void onClickUpdateItem(View view) {
        //make a new intent, specifying the next activity to launch on button click.
        Intent intent = new Intent(this, MainActivity.class);
        //get the name EditText data and convert to string, then add to intent.

        String name = etName.getText().toString();
        int amount = Integer.parseInt(etAmount.getText().toString());
        String description = etDescription.getText().toString();
        double price = Double.parseDouble(etPrice.getText().toString());
        String category = sCategory.getSelectedItem().toString();
        String unitType = sUnitType.getSelectedItem().toString();


//Temporary hardcode ItemID, "intNum" -> 2

        ItemData item = new ItemData(2, name, unitType, description, price, amount, category);

        submitItemChanges(item);

        //intent.putExtra("name", name);
        //get the category spinner data and convert to string...
        //startActivity(intent);
    }*/

    /*private void submitItemChanges(ItemData item) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeItemDataInBackground(item, new GetItemCallback() {
            @Override
            public void done(ItemData returnedItem) {
                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });
    }*/

    private void displayItemDetails() {
        //set the values in the EditText onStart....


    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

//            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
//                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }*/


}
