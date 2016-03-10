package com.grocerylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity {

    private Button bSubmit;
    private Spinner sCategory, sUnitType;
    private EditText etName, etAmount, etDescription, etPrice, etId;
    private int intNum;

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
        etId = (EditText) findViewById(R.id.name_content);

        Intent intent = getIntent();
        intNum = intent.getIntExtra("itemId", -99);



//        ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayItemDetails();
    }

    public void onClickUpdateItem(View view) {
        //make a new intent, specifying the next activity to launch on button click.
        Intent intent = new Intent(this, MainActivity.class);
        //get the name EditText data and convert to string, then add to intent.




        String name = etName.getText().toString();
        int amount = Integer.parseInt(etAmount.getText().toString());
        String description = etDescription.getText().toString();
        double price = Double.parseDouble(etPrice.getText().toString());
        String category = sCategory.getSelectedItem().toString();
        String unitType = sUnitType.getSelectedItem().toString();

        ItemData item = new ItemData(intNum, name, unitType, description, price, amount, category);

        submitItemChanges(item);

        //intent.putExtra("name", name);
        //get the category spinner data and convert to string...
        //startActivity(intent);
    }

    private void submitItemChanges(ItemData item) {
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeItemDataInBackground(item, new GetItemCallback() {
            @Override
            public void done(ItemData returnedItem) {
                startActivity(new Intent(EditActivity.this, MainActivity.class));
            }
        });
    }

    private void displayItemDetails() {
        //set the values in the EditText onStart....
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

//            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
//                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
