package com.grocerylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;;
import org.json.JSONObject;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class AddList extends AppCompatActivity {

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();

    EditText inputUserID;
    EditText inputItemID;

    private static String url_create_user_item = "http://w16groc.franklinpracticum.com/InsertUserItems.php";
    private static final String TAG_SUCCESS = "success";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        inputUserID = (EditText) findViewById(R.id.editUserID);
        inputItemID = (EditText) findViewById(R.id.editItemID);

        Button btnCreateList = (Button) findViewById(R.id.btnCreateProduct);

        btnCreateList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // creating new product in background thread
                String itemID = inputItemID.getText().toString();
                String userID = inputUserID.getText().toString();
                new CreateNewList().execute(itemID, userID);
            }
        });

    }

    class CreateNewList extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddList.this);
            pDialog.setMessage("Creating Product...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String item = args[0];
            String user = args[1];

            int itemInt = Integer.parseInt(item);
            int userInt = Integer.parseInt(user);

            Log.d("userID", user);
            Log.d("itemID", item);

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("UserID", user));
            params.add(new BasicNameValuePair("ItemID", item));

            JSONObject json = jsonParser.makeHttpRequest(url_create_user_item, "GET", params);

            Log.d("TAG", TAG_SUCCESS);

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            i.putExtra("UserID", Integer.parseInt(user));
            startActivity(i);

            finish();

            return null;
        }

        @Override
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
        }
    }
}
