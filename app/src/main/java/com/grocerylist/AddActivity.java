package com.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
//import widget classes
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Spinner;
//import listener
import android.widget.TextView.OnEditorActionListener;
import android.view.View.OnClickListener;

public class AddActivity extends AppCompatActivity implements OnEditorActionListener,
OnClickListener {

    //Instance variables for widgets
    private TextView selectCategoryView;
    private Spinner selectCategorySpinner;
    private EditText inputItemNameEditText;
    private EditText inputSizeWeightEditText;
    private EditText inputBrandEditText;
    private Button addButton;
    private Button cancelButton;

    private String inputItemNameString = "";
    private String inputSizeWeightString = "";
    private String inputBrandString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get references to widgets
        selectCategoryView = (TextView) findViewById(R.id.selectCategoryTextView);
        selectCategorySpinner = (Spinner) findViewById(R.id.selectCategorySpinner);
        inputItemNameEditText = (EditText) findViewById(R.id.inputItemNameEditText);
        inputSizeWeightEditText = (EditText) findViewById(R.id.inputSizeWeightEditText);
        inputBrandEditText = (EditText) findViewById(R.id.inputBrandEditText);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        //set array adapter for spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.category_array, android.R.layout.simple_spinner_item);

        //set layout for spinner
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        //set adapter for spinner
        selectCategorySpinner.setAdapter(adapter);

        //Set listeners to instance variables
        inputItemNameEditText.setOnEditorActionListener(this);
        inputSizeWeightEditText.setOnEditorActionListener(this);
        inputBrandEditText.setOnEditorActionListener(this);
        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_NEXT ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            switch (v.getId()) {
                case R.id.inputItemNameEditText:
                    inputItemNameString = inputItemNameEditText.getText().toString();
                    break;
                case R.id.inputSizeWeightEditText:
                    inputSizeWeightString = inputSizeWeightEditText.getText().toString();
                    break;
                case R.id.inputBrandEditText:
                    inputBrandString = inputBrandEditText.getText().toString();
                    break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                //insert in database
                break;
            case R.id.cancelButton:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
    }
}
