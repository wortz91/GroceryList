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
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener{


    //instance variables for widgets
    private EditText loginUserNameEditText;
    private EditText loginPasswordEditText;
    private EditText reenterPasswordEditText;
    private Button loginButton;
    private Button loginRegisterButton;
    private Button loginRegisterButton2;
    private TextView loginFailedTextView;
    private TextView registrationFailedTextView;
    private TextView registrationSuccessfulTextView;

    private String loginUserNameString = "";
    private String loginPasswordString = "";
    private String loginPasswordString2 = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        loginUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
        loginPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        reenterPasswordEditText = (EditText) findViewById(R.id.reenterPasswordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginRegisterButton = (Button) findViewById(R.id.loginRegisterButton);
        loginRegisterButton2 = (Button) findViewById(R.id.loginRegisterButton2);
        loginFailedTextView = (TextView) findViewById(R.id.loginFailedTextView);
        registrationFailedTextView = (TextView) findViewById(R.id.registrationFailedTextView);
        registrationSuccessfulTextView = (TextView) findViewById(R.id.registrationSuccessfulTextView);

        reenterPasswordEditText.setVisibility(View.GONE);
        loginFailedTextView.setVisibility(View.GONE);
        registrationFailedTextView.setVisibility(View.GONE);
        registrationSuccessfulTextView.setVisibility(View.GONE);
        loginRegisterButton2.setVisibility(View.GONE);

        //loginUserNameEditText.setOnEditorActionListener(this);
        //loginPasswordEditText.setOnEditorActionListener(this);
        //reenterPasswordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
        loginRegisterButton.setOnClickListener(this);
        loginRegisterButton2.setOnClickListener(this);


    }

/**    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            switch (v.getId()) {
                case R.id.userNameEditText:
                    loginUserNameString = loginUserNameEditText.getText().toString();
                    break;
                case R.id.passwordEditText:
                    loginPasswordString = loginPasswordEditText.getText().toString();
                    break;
                case R.id.reenterPasswordEditText:
                    loginPasswordString2 = reenterPasswordEditText.getText().toString();
            }
        }
        return false;
    }**/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                //check user name and password
                registrationSuccessfulTextView.setVisibility(View.GONE);

                //send to database
                String TAG = "GroceryList";
                StringBuilder sb = new StringBuilder();
                JSONObject jo = new JSONObject();

                try {

                    jo.put("UserName", loginUserNameEditText.getText());
                    jo.put("Password", loginPasswordEditText.getText());
                }
                catch(JSONException e) {
                    e.printStackTrace();
                    System.out.println("json exception");
                }


                try {
                    URL url = new URL("http://w16groc.franklinpracticum.com/login_script.php?" +
                     "UserName=%27" + loginUserNameEditText.getText() +
                     "%27&Password=%27" + loginPasswordEditText.getText()  + "%27");
                     HttpURLConnection con = (HttpURLConnection) url.openConnection();
                     BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                   /** URL url = new URL("http://w16groc.franklinpracticum.com/login_script.php?");
                    con = (HttpURLConnection) url.openConnection();
                    System.out.println("connection open");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    System.out.println("bufferReader created");

                    con.setDoOutput(true);
                    System.out.println("DoOutput set");
                    con.setRequestProperty("Accept", "application/json");
                    System.out.println("request p 1");

                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    System.out.println("request p 2");
                    con.setRequestMethod("POST");
                    System.out.println("post");

                    OutputStream os = con.getOutputStream();
                    System.out.println("outputstream created");
                    os.write(jo.toString().getBytes("UTF-8"));
                    System.out.println("os written to");
                    os.close();**/


                    String s;
                    while ((s = bufferedReader.readLine()) != null) {
                        sb.append(s + "\n");
                    }
                    System.out.println("bufferReader sb appended");
                }
                catch(Exception e){
                    e.printStackTrace();
                    System.out.println("connection exception");
                }
                /**finally
                {
                    con.disconnect();
                }**/




                int userId = 0;
                try {
                    String IDString = sb.toString();
                    String[] split = IDString.split(":");
                    String userIDBlock = split[2];

                    System.out.println(userIDBlock);
                    String[] split2 = userIDBlock.split("\"");
                    String s = split2[1];
                    System.out.println(s);
                    userId = Integer.parseInt(s);
                    Log.d("UserID:", userId+"");
                } catch (Exception e) {
                    loginFailedTextView.setVisibility(View.VISIBLE);

                }


                if (!sb.toString().startsWith("Login"))
                {
                    //login success
                    loginFailedTextView.setVisibility(View.GONE);
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("UserID", userId);
                    startActivity(intent);
                }
                else
                {
                    loginFailedTextView.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.loginRegisterButton:
                //change view for registering new user
                loginFailedTextView.setVisibility(View.GONE);
                registrationSuccessfulTextView.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
                loginRegisterButton.setVisibility(View.GONE);
                reenterPasswordEditText.setVisibility(View.VISIBLE);
                loginRegisterButton2.setVisibility(View.VISIBLE);
                break;

            case R.id.loginRegisterButton2:
                JSONObject joReg = new JSONObject();
                Boolean valid = false;
                try {
                    joReg.put("ItemName", loginUserNameEditText.getText());
                    joReg.put("ItemUnitType", loginPasswordEditText.getText());

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                System.out.println(joReg.toString());
                HttpClient httpClient = HttpClientBuilder.create().build();

                if (loginUserNameString.equalsIgnoreCase("a") &&
                        loginPasswordString.equalsIgnoreCase("1") &&
                        loginPasswordString2.equals(loginPasswordString))
                {
                    registrationFailedTextView.setVisibility(View.GONE);
                    registrationSuccessfulTextView.setVisibility(View.VISIBLE);
                    loginButton.setVisibility(View.VISIBLE);
                    loginRegisterButton.setVisibility(View.VISIBLE);
                    loginRegisterButton2.setVisibility(View.GONE);
                    reenterPasswordEditText.setVisibility(View.GONE);
                }
                else
                {
                    registrationFailedTextView.setVisibility(View.VISIBLE);
                }

        }
    }
}
