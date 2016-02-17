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

public class AddActivity extends AppCompatActivity implements
OnClickListener, OnItemSelectedListener {

    //Instance variables for widgets
    private TextView selectCategoryView;
    private TextView addSuccessView;
    private TextView addFailView;
    private Spinner selectCategorySpinner;
    private EditText inputItemNameEditText;
    private EditText inputSizeWeightEditText;
    private EditText inputBrandEditText;
    private Button addButton;
    private Button cancelButton;
    private Button addAnotherItemButton;

    private String inputItemNameString;
    private String inputSizeWeightString;
    private String inputBrandString;
    private int spinnerPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get references to widgets
        selectCategoryView = (TextView) findViewById(R.id.selectCategoryTextView);
        addSuccessView = (TextView) findViewById(R.id.addSuccessTextView);
        addFailView = (TextView) findViewById(R.id.addFailTextView);
        selectCategorySpinner = (Spinner) findViewById(R.id.selectCategorySpinner);
        inputItemNameEditText = (EditText) findViewById(R.id.inputItemNameEditText);
        inputSizeWeightEditText = (EditText) findViewById(R.id.inputSizeWeightEditText);
        inputBrandEditText = (EditText) findViewById(R.id.inputBrandEditText);
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        addAnotherItemButton = (Button) findViewById(R.id.addAnotherItemButton);

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
                inputItemNameString = inputItemNameEditText.getText().toString();
                inputSizeWeightString = inputSizeWeightEditText.getText().toString();
                inputBrandString = inputBrandEditText.getText().toString();
                //insert in database
                if (inputItemNameString != null) // &&
                {  //spinnerPosition > -1)
                    if (!inputItemNameString.equals(""))
                        {
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
                    }

                break;
            case R.id.cancelButton:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                break;
            case R.id.addAnotherItemButton:
                startActivity(new Intent(getApplicationContext(), AddActivity.class));


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerPosition = position;

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
