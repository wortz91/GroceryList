package com.grocerylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DeleteActivity extends AppCompatActivity {

    private int intNum = 10; //Temporary hardcode!!!!!!!!!!!
    public static final String SERVER_ADDRESS = "http://w16groc.franklinpracticum.com/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        Intent intent = getIntent();
        //intNum = intent.getIntExtra("itemId", -88);
    }

    public void onClickDelete(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        deleteItem(intNum);
        startActivity(intent);
    }

    private void deleteItem(final int idOfDeletedItem) {

        class DeleteItem extends AsyncTask<Void, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(DeleteActivity.this,
                        "Updating...", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(DeleteActivity.this, s, Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler requestHandler = new RequestHandler();
                String s = requestHandler.sendGetRequestParam(SERVER_ADDRESS +
                        "delete_script.php?ItemID=", idOfDeletedItem + "");
                return s;
            }
        }
        DeleteItem deleteItem = new DeleteItem();
        deleteItem.execute();
    }

    public void onClickCancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
