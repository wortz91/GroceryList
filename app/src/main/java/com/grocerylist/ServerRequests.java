package com.grocerylist;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.internal.http.HttpConnection;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.conn.ClientConnectionManager;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;
import cz.msebera.android.httpclient.params.BasicHttpParams;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Michael on 3/9/2016.
 */
public class ServerRequests {

    ProgressDialog progressDialog;
    public static final int CONNECTION_TIMEOUT = 1000 * 15;
    public static final String SERVER_ADDRESS = "http://w16groc.franklinpracticum.com/";

    public ServerRequests(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Processing");
        progressDialog.setMessage("Please wait...");
    }

    public void storeItemDataInBackground(ItemData item, GetItemCallback callBack) {
        progressDialog.show();
        new StoreItemDataAsyncTask(item, callBack).execute();
    }

    public void fetchItemDataInBackground(ItemData item, GetItemCallback callBack) {
        progressDialog.show();
        new fetchItemDataAsyncTask(item, callBack).execute();
    }

    public class StoreItemDataAsyncTask extends AsyncTask<Void, Void, Void> {

        ItemData item;
        GetItemCallback itemCallBack;

        public StoreItemDataAsyncTask(ItemData item, GetItemCallback callBack) {
            this.item = item;
            this.itemCallBack = callBack;
        }

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", item.getItemName()));
            dataToSend.add(new BasicNameValuePair("unitType", item.getItemUnitType()));
            dataToSend.add(new BasicNameValuePair("description", item.getItemDescription()));
            dataToSend.add(new BasicNameValuePair("price", item.getItemPrice() + ""));
            dataToSend.add(new BasicNameValuePair("amount", item.getItemCount() + ""));
            dataToSend.add(new BasicNameValuePair("category", item.getItemCategory()));
            dataToSend.add(new BasicNameValuePair("itemId", item.getItemID() + ""));

            HttpParams httpRequestParams = (HttpParams) new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient((ClientConnectionManager) httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "edit_script.php");

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                client.execute(post);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
            itemCallBack.done(null);

            super.onPostExecute(aVoid);
        }
    }

    public class fetchItemDataAsyncTask extends AsyncTask<Void, Void, ItemData> {

        ItemData item;
        GetItemCallback itemCallBack;

        public fetchItemDataAsyncTask(ItemData item, GetItemCallback callBack) {
            this.item = item;
            this.itemCallBack = callBack;
        }

        @Override
        protected ItemData doInBackground(Void... params) {
            ArrayList<NameValuePair> dataToSend = new ArrayList<>();
            dataToSend.add(new BasicNameValuePair("name", item.getItemName()));
            dataToSend.add(new BasicNameValuePair("unitType", item.getItemUnitType()));
            dataToSend.add(new BasicNameValuePair("description", item.getItemDescription()));
            dataToSend.add(new BasicNameValuePair("price", item.getItemPrice() + ""));
            dataToSend.add(new BasicNameValuePair("amount", item.getItemCount() + ""));
            dataToSend.add(new BasicNameValuePair("category", item.getItemCategory()));
            dataToSend.add(new BasicNameValuePair("itemId", item.getItemID() + ""));

            HttpParams httpRequestParams = (HttpParams) new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpRequestParams, CONNECTION_TIMEOUT);
            HttpConnectionParams.setSoTimeout(httpRequestParams, CONNECTION_TIMEOUT);

            HttpClient client = new DefaultHttpClient((ClientConnectionManager) httpRequestParams);
            HttpPost post = new HttpPost(SERVER_ADDRESS + "select_script.php");

            ItemData returnedItem = null;

            try {
                post.setEntity(new UrlEncodedFormEntity(dataToSend));
                HttpResponse httpResponse = client.execute(post);

                HttpEntity entity = httpResponse.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject jObject = new JSONObject(result);

                if (jObject.length() == 0) {
                    returnedItem = null;
                } else {
                    String itemName = jObject.getString("ItemName");
                    String itemUnitType = jObject.getString("ItemUnitType");
                    String itemDescription = jObject.getString("ItemDescription");
                    double itemPrice = jObject.getDouble("ItemPrice");
                    int itemCount = jObject.getInt("ItemCount");
                    String itemCategory = jObject.getString("ItemCategory");

                    returnedItem = new ItemData(item.getItemID(), itemName, itemUnitType,
                            itemDescription, itemPrice, itemCount, itemCategory);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return returnedItem;
        }

        @Override
        protected void onPostExecute(ItemData returnedItem) {
            progressDialog.dismiss();
            itemCallBack.done(returnedItem);
            super.onPostExecute(returnedItem);
        }
    }


}
