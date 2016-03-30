package com.grocerylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class DeleteActivity extends AppCompatActivity {

    private int itemId;
    private int userID;
    public static final String SERVER_ADDRESS =
            "http://w16groc.franklinpracticum.com/delete_script.php";
    private JSONParser jsonParser = new JSONParser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        itemId = intent.getIntExtra("ItemID", -88);
        userID = intent.getIntExtra("UserID", -98);

    }

    public void onClickDelete(View view) {
        deleteItem(itemId);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserID", userID);
        Log.d("DelAct", "Returning to MainActivity");
        startActivity(intent);
    }

    private void deleteItem(final int idOfDeletedItem) {

        class DeleteItem extends AsyncTask<String, String, String> {
            ProgressDialog progressDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(DeleteActivity.this);
                progressDialog.setMessage("Deleting...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... args) {
                List<NameValuePair> params = new ArrayList<>();
                Log.d("DelActivity itemId", itemId + "");
                params.add(new BasicNameValuePair("ItemID", itemId + ""));
                params.add(new BasicNameValuePair("UserID", userID + ""));

                jsonParser.makeHttpRequest(SERVER_ADDRESS, "GET", params);

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                progressDialog.dismiss();
            }


        }
        DeleteItem deleteItem = new DeleteItem();
        deleteItem.execute();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("UserID", userID);
        Log.d("DelAct", "Returning to MainActivity");
        startActivity(intent);
    }
}
