package com.grocerylist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DeleteActivity extends AppCompatActivity {

    private int intNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        intNum = intent.getIntExtra("itemId", -88);
    }

    public void onClickDelete(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        ServerRequests serverRequests = new ServerRequests(this);



    }

    private void deleteItem(int idOfDeletedItem) {

    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
