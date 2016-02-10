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
    private Button loginButton;
    private Button loginCancelButton;

    private String loginUserNameString = "";
    private String loginPasswordString = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loginUserNameEditText = (EditText) findViewById(R.id.userNameEditText);
        loginPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginCancelButton = (Button) findViewById(R.id.loginCancelButton);

        loginUserNameEditText.setOnEditorActionListener(this);
        loginPasswordEditText.setOnEditorActionListener(this);
        loginButton.setOnClickListener(this);
        loginCancelButton.setOnClickListener(this);

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
                case R.id.inputItemNameEditText:
                    loginUserNameString = loginUserNameEditText.getText().toString();
                    break;
                case R.id.inputSizeWeightEditText:
                    loginPasswordString = loginPasswordEditText.getText().toString();
                    break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                //check user name and password
                break;
            case R.id.cancelButton:
                //go to login
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
    }
}
