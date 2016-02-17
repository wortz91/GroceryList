package com.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements TextView.OnEditorActionListener,
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

        loginUserNameEditText.setOnEditorActionListener(this);
        loginPasswordEditText.setOnEditorActionListener(this);
        reenterPasswordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
        loginRegisterButton.setOnClickListener(this);
        loginRegisterButton2.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                //check user name and password
                registrationSuccessfulTextView.setVisibility(View.GONE);
                if (loginUserNameString.equalsIgnoreCase("a") &&
                        loginPasswordString.equalsIgnoreCase("1"))
                {
                    //login success
                    loginFailedTextView.setVisibility(View.GONE);
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
