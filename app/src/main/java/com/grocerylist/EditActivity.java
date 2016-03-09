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

    Button bSubmit;
    Spinner sCategory, etUnitType;
    EditText etName, etAmount, etDescription, etPrice, etCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = (EditText) findViewById(R.id.name_content);
        etAmount = (EditText) findViewById(R.id.amount_content);
        etDescription = (EditText) findViewById(R.id.description_content);



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
    public void onClickUpdateItem(View view) {
        //make a new intent, specifying the next activity to launch on button click.
        Intent intent = new Intent(this, MainActivity.class);
        //get the name EditText data and convert to string, then add to intent.




        String nameText = etName.getText().toString();



        intent.putExtra("name", nameText);
        //get the category spinner data and convert to string...
        startActivity(intent);



        ItemData item;


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
