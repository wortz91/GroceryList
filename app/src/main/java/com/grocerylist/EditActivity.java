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
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class EditActivity extends AppCompatActivity {

    public static final String SERVER_ADDRESS = "http://w16groc.franklinpracticum.com/";

    //JSON parser
    JSONParser jsonParser = new JSONParser();


    //private Button bSubmit;
    private Spinner sCategory, sUnitType;
    private EditText etName, etAmount, etDescription, etPrice, etId;
    private int intNum;
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
        intNum = intent.getIntExtra("ItemID", -99);
        Log.d("(49)intNum=", intNum + "");
        userID = intent.getIntExtra("UserID", -98);
        Log.d("(51)userId=", userID + "");

/*
        etId.setText(intNum + "");
*/

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
            ProgressDialog progressDialog;

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

                String responseMessage = "";
                InputStream inputStream = null;

                List<NameValuePair> nameValuePairs = new ArrayList<>();

                nameValuePairs.add(new BasicNameValuePair("ItemID", intNum + ""));
                nameValuePairs.add(new BasicNameValuePair("ItemName", name));
                nameValuePairs.add(new BasicNameValuePair("ItemUnitType", unitType));
                nameValuePairs.add(new BasicNameValuePair("ItemDescription", description));
                nameValuePairs.add(new BasicNameValuePair("ItemPrice", price + ""));
                nameValuePairs.add(new BasicNameValuePair("ItemCount", amount + ""));
                nameValuePairs.add(new BasicNameValuePair("ItemCategory", category));

                JSONObject jsonObject = jsonParser.makeHttpRequest(
                        SERVER_ADDRESS + "edit_script.php", "GET", nameValuePairs);

                /*if (jsonObject == null) {
                    System.out.println("Its null!!!!!!"); //This is happening 3/23 10:17pm
                }

                try {
                    String success = jsonObject.getString("success");

                    if (success.equalsIgnoreCase("1")) {
                        Intent intent = getIntent();
                        setResult(100, intent);
                        finish();
                    }
                    else {
                        //not sure what goes here
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
*/
                return null;

               /* try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(SERVER_ADDRESS + "edit_script.php");
                    post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();
                    //inputStream = entity.getContent();

                }
                catch (ClientProtocolException e) {
                    System.out.println("ClientProtocolException!");
                    e.printStackTrace();
                }
                catch (IOException e) {
                    System.out.println("IOException!");
                    e.printStackTrace();
                }
                return "success";*/

                /*try {
                    BufferedReader bufferedReader = new BufferedReader(
                            new InputStreamReader(inputStream, "iso-8859-1"), 8);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line = null;
                    while ((line = bufferedReader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                    inputStream.close();
                    responseMessage = stringBuilder.toString();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d("(223)Response Message", responseMessage);
                System.out.println("Test");
                return responseMessage;*/

                /*try {
                    JSONArray jsonArray = new JSONArray(responseMessage);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                    }


                    int num = ()
                }
                catch (Exception e) {
                    e.printStackTrace();
                }*/
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