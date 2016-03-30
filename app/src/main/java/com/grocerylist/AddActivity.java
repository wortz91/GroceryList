
package com.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
//import widget classes
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
//import listener
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.*;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class AddActivity extends AppCompatActivity implements
        OnClickListener, OnItemSelectedListener {

    private int userID;

    //Instance variables for widgets
    private TextView selectCategoryView;
    private TextView addSuccessView;
    private TextView addFailView;
    private Spinner selectCategorySpinner;
    private EditText inputNameEditText;
    private EditText inputUnitTypeEditText;
    private EditText inputDescriptionEditText;
    private EditText inputQuantityEditText;
    private EditText inputPriceEditText;
    private Button addButton;
    private Button cancelButton;
    private Button addAnotherItemButton;

    private String inputItemNameString;
    private String inputSizeWeightString;
    private String inputBrandString;
    private int spinnerPosition = -1;
    private String categoryString;

    String url = "http://w16groc.franklinpracticum.com/add_script.php";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle b = getIntent().getExtras();
        userID = b.getInt("UserID");

        //strict mode
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // Get references to widgets
        selectCategoryView = (TextView) findViewById(R.id.selectCategoryTextView);
        addSuccessView = (TextView) findViewById(R.id.addSuccessTextView);
        addFailView = (TextView) findViewById(R.id.addFailTextView);
        selectCategorySpinner = (Spinner) findViewById(R.id.selectCategorySpinner);
        inputNameEditText = (EditText) findViewById(R.id.inputNameEditText);
        inputUnitTypeEditText = (EditText) findViewById(R.id.inputUnitTypeEditText);
        inputDescriptionEditText = (EditText) findViewById(R.id.inputDescriptionEditText);
        inputQuantityEditText = (EditText) findViewById(R.id.inputQuantityEditText);
        inputPriceEditText = (EditText) findViewById(R.id.inputPriceEditText);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        addAnotherItemButton = (Button) findViewById(R.id.addAnotherItemButton);

        //volley
        requestQueue = Volley.newRequestQueue(getApplicationContext());

        //set array adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);

        //set layout for spinner
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        //set adapter for spinner
        selectCategorySpinner.setAdapter(adapter);


        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        addAnotherItemButton.setOnClickListener(this);

        //get rid of notifications
        addSuccessView.setVisibility(View.GONE);
        addFailView.setVisibility(View.GONE);
        addAnotherItemButton.setVisibility(View.GONE);

    }





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:

                String nameString = inputNameEditText.getText().toString();
                //inputSizeWeightString = inputSizeWeightEditText.getText().toString();
                //inputBrandString = inputBrandEditText.getText().toString();
                StringBuilder sb =new StringBuilder();

                String TAG = "GroceryList";
                JSONObject jo = new JSONObject();

                if (nameString != null && !nameString.equals("")) {
                    try {
                        jo.put("ItemName", inputNameEditText.getText());
                        jo.put("ItemUnitType", inputUnitTypeEditText.getText());
                        jo.put("ItemDescription", inputDescriptionEditText.getText());
                        jo.put("ItemPrice", inputPriceEditText.getText());
                        jo.put("ItemCount", inputQuantityEditText.getText());
                        jo.put("ItemCategory", selectCategorySpinner.getSelectedItem());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.v(TAG, "JSON Exception");
                    }
                    System.out.println(jo.toString());
                    HttpClient httpClient = HttpClientBuilder.create().build();
                    try {
                        HttpPost post = new HttpPost(url);
                        //url is defined earlier as "http://w16groc.franklinpracticum.com/add_script.php"
                        StringEntity postingString = new StringEntity(jo.toString());
                        post.setEntity(postingString);
                        post.setHeader("Content-type", "application/json");
                        HttpResponse response = httpClient.execute(post);
                        if (response != null)
                        {
                            InputStream in = response.getEntity().getContent();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                            String s;
                            while ((s = bufferedReader.readLine()) != null) {
                                sb.append(s + "\n");
                                System.out.println("***response =" + sb.toString());
                            }
                        }

                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    finally {
                        httpClient.getConnectionManager().shutdown();
                    }


                    /**try {
                        URL url = new URL("http://w16groc.franklinpracticum.com/add_script.php?");

                        HttpURLConnection con = (HttpURLConnection) url.openConnection();

                        Log.v(TAG, "connection created");

                        con.setDoOutput(true);
                        con.setRequestProperty("Accept", "application/json");
                        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                        con.setRequestMethod("POST");

                        OutputStream os = con.getOutputStream();
                        os.write(jo.toString().getBytes("UTF-8"));
                        os.close();

                        int responseCode = con.getResponseCode();
                        Log.v(TAG, "response code:" + responseCode);

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        String s;
                        while ((s = bufferedReader.readLine()) != null) {
                            sb.append(s + "\n");
                            System.out.println(sb.toString());
                        }
                        if (responseCode == 201) {
                            Log.v(TAG, "Successful add");
                            Intent i = new Intent(this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Log.v(TAG, "Failed add");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.v(TAG, "exception");
                    }**/
                    addSuccessView.setVisibility(View.VISIBLE);
                    addFailView.setVisibility(View.GONE);
                    addButton.setVisibility(View.GONE);
                    addAnotherItemButton.setVisibility(View.VISIBLE);
                }
                else
                {
                    addFailView.setVisibility(View.VISIBLE);
                    addButton.setVisibility(View.GONE);
                    addAnotherItemButton.setVisibility(View.VISIBLE);
                }



        break;
        case R.id.cancelButton:
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            main.putExtra("UserID", userID);
            startActivity(main);

        break;
        case R.id.addAnotherItemButton:
        startActivity(new Intent(getApplicationContext(), AddActivity.class));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerPosition = position;
        categoryString = (String) selectCategorySpinner.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


















