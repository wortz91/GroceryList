package com.grocerylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class EditActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://w16groc.franklinpracticum.com/";
    private ProgressDialog progressDialog;
    private JSONParser jsonParser = new JSONParser();

    private Spinner sCategory, sUnitType;
    private EditText etName, etAmount, etDescription, etPrice;

    private int itemId;
    private int userID;

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
        itemId = intent.getIntExtra("ItemID", -99);
        Log.d("(60)itemId=", itemId + "");
        userID = intent.getIntExtra("UserID", -98);
        Log.d("(62)userId=", userID + "");

        getItem();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getItem() {
        class GetItem extends AsyncTask<String, String, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(EditActivity.this);
                progressDialog.setMessage("Retrieving Item Data...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Log.d("EditActivity itemId=", itemId + "");
                String s = sendGetRequestParam(SERVER_ADDRESS +
                        "selectItem_script_v2.php?ItemID=", itemId + "");
                Log.d("EditActivity: String s", s);
                return s;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                showItem(s);
            }


        }
        GetItem getItem = new GetItem();
        getItem.execute();
    }

    public String sendGetRequestParam (String requestURL, String id) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            URL url = new URL(requestURL + id);
            Log.d("EditActivity Strng url=", requestURL + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));

            String string;
            while ((string = bufferedReader.readLine()) != null) {
                stringBuilder.append(string + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
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
        final String name = etName.getText().toString().trim();
        System.out.println("name = " + name);
        final int amount = Integer.parseInt(etAmount.getText().toString().trim());
        System.out.println("amount = " + amount);
        final String description = etDescription.getText().toString().trim();
        System.out.println("description = " + description);
        final double price = Double.parseDouble(etPrice.getText().toString().trim());
        System.out.println("price = " + price);
        final String category = sCategory.getSelectedItem().toString().trim();
        System.out.println("category = " + category);
        final String unitType = sUnitType.getSelectedItem().toString().trim();
        System.out.println("unitType = " + unitType);

        class UpdateItem extends AsyncTask<String, String, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(EditActivity.this);
                progressDialog.setMessage("Updating...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {

                List<NameValuePair> nameValuePairs = new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("ItemID", itemId + ""));
                nameValuePairs.add(new BasicNameValuePair("ItemName", name));
                nameValuePairs.add(new BasicNameValuePair("ItemUnitType", unitType));
                nameValuePairs.add(new BasicNameValuePair("ItemDescription", description));
                nameValuePairs.add(new BasicNameValuePair("ItemPrice", price + ""));
                nameValuePairs.add(new BasicNameValuePair("ItemCount", amount + ""));
                nameValuePairs.add(new BasicNameValuePair("ItemCategory", category));

                jsonParser.makeHttpRequest(
                        SERVER_ADDRESS + "edit_script.php", "GET", nameValuePairs);

                return null;

            }
            @Override
            protected void onPostExecute(String result) {
                progressDialog.dismiss();
            }
        }

        UpdateItem ui = new UpdateItem();
        ui.execute();
    }

    public void onClickUpdateItem(View view) {
        updateItem();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserID", userID);
        startActivity(intent);
    }
}